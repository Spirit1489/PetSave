

package com.raywenderlich.android.petsave.animalsnearyou.presentation.animaldetails

import com.raywenderlich.android.petsave.animalsnearyou.presentation.animaldetails.model.UIAnimalDetailed

sealed class AnimalDetailsViewState {
  object Loading : AnimalDetailsViewState()

  data class AnimalDetails(
      val animal: UIAnimalDetailed
  ) : AnimalDetailsViewState()

  object Failure : AnimalDetailsViewState()
}