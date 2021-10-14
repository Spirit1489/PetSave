

package com.raywenderlich.android.petsave.animalsnearyou.presentation.animaldetails

sealed class AnimalDetailsEvent {
  data class LoadAnimalDetails(val animalId: Long) : AnimalDetailsEvent()
}