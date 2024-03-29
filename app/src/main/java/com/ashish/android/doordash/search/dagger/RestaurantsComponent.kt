package com.ashish.android.doordash.search.dagger

import com.ashish.android.doordash.core.di.ActivityScope
import com.ashish.android.doordash.search.ui.RestaurantsActivity
import com.ashish.android.doordash.search.ui.RestaurantsFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface RestaurantsComponent {
    fun inject(restaurantsActivity: RestaurantsActivity)
    fun inject(restaurantsFragment: RestaurantsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): RestaurantsComponent
    }
}