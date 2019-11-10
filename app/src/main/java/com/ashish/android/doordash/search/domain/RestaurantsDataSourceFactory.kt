package com.ashish.android.doordash.search.domain

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ashish.android.doordash.search.net.Restaurant
import com.ashish.android.doordash.search.net.RestaurantService
import javax.inject.Inject

class RestaurantsDataSourceFactory @Inject constructor(
    private val restaurantService: RestaurantService
) : DataSource.Factory<Int, Restaurant>() {

    private var lat: String = ""
    private var lng: String = ""

    val restaurantsDataSourceLiveData = MutableLiveData<RestaurantsDataSource>()

    override fun create(): DataSource<Int, Restaurant> {
        val restaurantsDataSource = RestaurantsDataSource(restaurantService, lat, lng)
        restaurantsDataSourceLiveData.postValue(restaurantsDataSource)
        return restaurantsDataSource
    }

    // TODO find some other good way to set lat and lang
    fun setLatLng(lat: String, lng: String) {
        this.lat = lat
        this.lng = lng
    }
}