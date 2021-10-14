

package com.raywenderlich.android.petsave.common.domain.usecases

import com.raywenderlich.android.petsave.common.domain.model.animal.details.AnimalWithDetails
import com.raywenderlich.android.petsave.common.domain.repositories.AnimalRepository
import javax.inject.Inject

class GetAnimalDetails @Inject constructor(
    private val animalRepository: AnimalRepository
) {

  suspend operator fun invoke(animalId: Long): AnimalWithDetails {
    return animalRepository.getAnimal(animalId)
  }
}