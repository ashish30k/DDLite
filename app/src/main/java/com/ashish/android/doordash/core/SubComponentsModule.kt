package com.ashish.android.doordash.core

import com.ashish.android.doordash.search.dagger.RestaurantsComponent
import dagger.Module

@Module(subcomponents = [RestaurantsComponent::class])
interface SubComponentsModule {
}