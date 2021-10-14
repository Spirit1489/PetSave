

package com.raywenderlich.android.petsave.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raywenderlich.android.petsave.common.data.cache.daos.AnimalsDao
import com.raywenderlich.android.petsave.common.data.cache.daos.OrganizationsDao
import com.raywenderlich.android.petsave.common.data.cache.model.cachedanimal.*
import com.raywenderlich.android.petsave.common.data.cache.model.cachedorganization.CachedOrganization

@Database(
    entities = [
      CachedPhoto::class,
      CachedVideo::class,
      CachedTag::class,
      CachedAnimalWithDetails::class,
      CachedOrganization::class,
      CachedAnimalTagCrossRef::class
    ],
    version = 1
)
abstract class PetSaveDatabase : RoomDatabase() {
  abstract fun organizationsDao(): OrganizationsDao
  abstract fun animalsDao(): AnimalsDao
}