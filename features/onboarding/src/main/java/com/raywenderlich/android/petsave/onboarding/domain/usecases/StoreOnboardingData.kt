

package com.raywenderlich.android.petsave.onboarding.domain.usecases

import com.raywenderlich.android.petsave.common.domain.repositories.AnimalRepository
import javax.inject.Inject

class StoreOnboardingData @Inject constructor(
    private val repository: AnimalRepository
) {

  suspend operator fun invoke(postcode: String, distance: String) {
    repository.storeOnboardingData(postcode, distance.toInt())
  }
}
