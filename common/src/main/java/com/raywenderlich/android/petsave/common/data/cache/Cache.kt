

package com.raywenderlich.android.petsave.common.data.cache

import com.raywenderlich.android.petsave.common.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.raywenderlich.android.petsave.common.data.cache.model.cachedorganization.CachedOrganization
import com.raywenderlich.android.petsave.common.domain.model.animal.details.AnimalWithDetails
import io.reactivex.Flowable

interface Cache {
  suspend fun storeOrganizations(organizations: List<CachedOrganization>)
  suspend fun getOrganization(organizationId: String): CachedOrganization

  fun getNearbyAnimals(): Flowable<List<CachedAnimalAggregate>>
  suspend fun storeNearbyAnimals(animals: List<CachedAnimalAggregate>)
  suspend fun getAnimal(animalId: Long): CachedAnimalAggregate

  suspend fun getAllTypes(): List<String>
  fun searchAnimalsBy(
      name: String,
      age: String,
      type: String
  ): Flowable<List<CachedAnimalAggregate>>
}