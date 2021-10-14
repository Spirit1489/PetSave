

package com.raywenderlich.android.petsave.common.data.di

import com.raywenderlich.android.petsave.common.data.preferences.PetSavePreferences
import com.raywenderlich.android.petsave.common.data.preferences.Preferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

  @Binds
  abstract fun providePreferences(preferences: PetSavePreferences): Preferences
}