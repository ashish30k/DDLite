package com.ashish.android.doordash.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class AppModule @Inject constructor(private val application: Application) {
    val APP_SHARED_PREF_FILE_NAME = "door_dash_lite"

    @Provides
    fun provideAppContext() = application.applicationContext

    @Provides
    fun provideApplication() = application

    @Provides
    fun provideSharedPreferences(): SharedPreferences {
        return application.getSharedPreferences(APP_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
    }
}