package com.ashish.android.doordash.core

import com.ashish.android.doordash.search.dagger.RestaurantsComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, SubComponentsModule::class])
interface AppComponent {
    fun restaurantsComponent(): RestaurantsComponent.Factory
}