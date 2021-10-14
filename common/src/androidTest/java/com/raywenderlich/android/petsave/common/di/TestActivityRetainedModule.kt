

package com.raywenderlich.android.petsave.common.di

import com.raywenderlich.android.petsave.common.data.FakeRepository
import com.raywenderlich.android.petsave.common.domain.repositories.AnimalRepository
import com.raywenderlich.android.petsave.common.utils.CoroutineDispatchersProvider
import com.raywenderlich.android.petsave.common.utils.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class TestActivityRetainedModule {

  @Binds
  @ActivityRetainedScoped
  abstract fun bindAnimalRepository(repository: FakeRepository): AnimalRepository

  @Binds
  abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider): DispatchersProvider

  companion object {
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()
  }
}