

package com.raywenderlich.android.petsave.common.data.api.interceptors

import com.raywenderlich.android.logging.Logger
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : HttpLoggingInterceptor.Logger {
  override fun log(message: String) {
    Logger.i(message)
  }
}