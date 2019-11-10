package com.ashish.android.doordash.search.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ashish.android.doordash.core.di.ActivityScope
import com.ashish.android.doordash.search.domain.RestaurantRepo
import javax.inject.Inject

@ActivityScope
class RestaurantsViewModelFactory @Inject constructor(val restaurantRepo: RestaurantRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantsViewModel::class.java)) {
            return RestaurantsViewModel(restaurantRepo) as T
        }
        throw Throwable("Unknown ViewModel class")
    }
}