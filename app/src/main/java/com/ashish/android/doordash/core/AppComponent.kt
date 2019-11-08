package com.ashish.android.doordash.core

import android.app.Application
import android.content.Context
import com.ashish.android.doordash.search.dagger.RestaurantsComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class, SubComponentsModule::class])
interface AppComponent {
    fun inject(doorDashApp: DoorDashApp)
    fun getAppContext(): Context
    fun getApplication(): Application
    fun getSharedPrefsHelper(): SharedPrefsHelper
    fun restaurantsComponent(): RestaurantsComponent.Factory
}