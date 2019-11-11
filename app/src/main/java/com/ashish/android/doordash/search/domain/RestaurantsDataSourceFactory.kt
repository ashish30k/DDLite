package com.ashish.android.doordash.search.domain

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ashish.android.doordash.search.net.Restaurant
import com.ashish.android.doordash.search.net.RestaurantService

class RestaurantsDataSourceFactory constructor(
    private val restaurantService: RestaurantService,
    private val latitude: String,
    private val longitude: String
) : DataSource.Factory<Int, Restaurant>() {

    val restaurantsDataSourceLiveData = MutableLiveData<RestaurantsDataSource>()

    override fun create(): DataSource<Int, Restaurant> {
        val restaurantsDataSource = RestaurantsDataSource(restaurantService, latitude, longitude)
        restaurantsDataSourceLiveData.postValue(restaurantsDataSource)
        return restaurantsDataSource
    }
}