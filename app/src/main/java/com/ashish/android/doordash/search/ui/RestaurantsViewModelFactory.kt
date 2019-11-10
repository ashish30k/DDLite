package com.ashish.android.doordash.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ashish.android.doordash.core.di.ActivityScope
import com.ashish.android.doordash.search.domain.RestaurantsDataSourceFactory
import javax.inject.Inject

@ActivityScope
class RestaurantsViewModelFactory @Inject constructor(val restaurantsDataSourceFactory: RestaurantsDataSourceFactory) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantsViewModel::class.java)) {
            return RestaurantsViewModel(restaurantsDataSourceFactory) as T
        }
        throw Throwable("Unknown ViewModel class")
    }
}