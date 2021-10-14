

package com.raywenderlich.android.petsave

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.raywenderlich.android.logging.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PetSaveApplication: SplitCompatApplication() {

  // initiate analytics, crashlytics, etc

  override fun onCreate() {
    super.onCreate()

    initLogger()
  }

  private fun initLogger() {
    Logger.init()
  }
}