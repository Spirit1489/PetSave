

package com.raywenderlich.android.petsave.animalsnearyou.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raywenderlich.android.logging.Logger
import com.raywenderlich.android.petsave.animalsnearyou.domain.usecases.GetAnimals
import com.raywenderlich.android.petsave.animalsnearyou.domain.usecases.RequestNextPageOfAnimals
import com.raywenderlich.android.petsave.common.domain.model.NetworkException
import com.raywenderlich.android.petsave.common.domain.model.NetworkUnavailableException
import com.raywenderlich.android.petsave.common.domain.model.NoMoreAnimalsException
import com.raywenderlich.android.petsave.common.domain.model.animal.Animal
import com.raywenderlich.android.petsave.common.domain.model.pagination.Pagination
import com.raywenderlich.android.petsave.common.presentation.Event
import com.raywenderlich.android.petsave.common.presentation.model.mappers.UiAnimalMapper
import com.raywenderlich.android.petsave.common.utils.DispatchersProvider
import com.raywenderlich.android.petsave.common.utils.createExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AnimalsNearYouFragmentViewModel @Inject constructor(
    private val getAnimals: GetAnimals,
    private val requestNextPageOfAnimals: RequestNextPageOfAnimals,
    private val uiAnimalMapper: UiAnimalMapper,
    private val dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {

  companion object {
    const val UI_PAGE_SIZE = Pagination.DEFAULT_PAGE_SIZE
  }

  val state: LiveData<AnimalsNearYouViewState> get() = _state
  var isLoadingMoreAnimals: Boolean = false
  var isLastPage = false

  private val _state = MutableLiveData<AnimalsNearYouViewState>()
  private var currentPage = 0

  init {
    _state.value = AnimalsNearYouViewState()
    subscribeToAnimalUpdates()
  }

  fun onEvent(event: AnimalsNearYouEvent) {
    when(event) {
      is AnimalsNearYouEvent.RequestMoreAnimals -> loadNextAnimalPage()
    }
  }

  private fun subscribeToAnimalUpdates() {
    getAnimals()
        .doOnNext { if (hasNoAnimalsStoredButCanLoadMore(it)) loadNextAnimalPage() }
        .filter { it.isNotEmpty() }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
          { onNewAnimalList(it) },
          { onFailure(it) }
        )
        .addTo(compositeDisposable)
  }

  private fun hasNoAnimalsStoredButCanLoadMore(animals: List<Animal>): Boolean {
    return animals.isEmpty() && !state.value!!.noMoreAnimalsNearby
  }

  private fun onNewAnimalList(animals: List<Animal>) {
    Logger.d("Got more animals!")

    val animalsNearYou = animals.map { uiAnimalMapper.mapToView(it) }

    // This ensures that new items are added below the already existing ones, thus avoiding
    // repositioning of items that are already visible, as it can provide for a confusing UX. A
    // nice alternative to this would be to add an "updatedAt" field to the Room entities, so
    // that we could actually order them by something that we completely control.
    val currentList = state.value!!.animals
    val newAnimals = animalsNearYou.subtract(currentList)
    val updatedList = currentList + newAnimals

    _state.value = state.value!!.copy( loading = false, animals = updatedList)
  }

  private fun loadNextAnimalPage() {
    isLoadingMoreAnimals = true

    val errorMessage = "Failed to fetch nearby animals"
    val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }

    viewModelScope.launch(exceptionHandler) {
      val pagination = withContext(dispatchersProvider.io()) {
        Logger.d("Requesting more animals.")

        requestNextPageOfAnimals(++currentPage)
      }

      onPaginationInfoObtained(pagination)
      isLoadingMoreAnimals = false
    }
  }

  private fun onPaginationInfoObtained(pagination: Pagination) {
    currentPage = pagination.currentPage
    isLastPage = !pagination.canLoadMore
  }

  private fun onFailure(failure: Throwable) {
    when (failure) {
      is NetworkException,
      is NetworkUnavailableException -> {
        _state.value = state.value!!.copy(
            loading = false,
            failure = Event(failure)
        )
      }
      is NoMoreAnimalsException -> {
        _state.value = state.value!!.copy(
            noMoreAnimalsNearby = true,
            failure = Event(failure)
        )
      }
    }
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }
}
