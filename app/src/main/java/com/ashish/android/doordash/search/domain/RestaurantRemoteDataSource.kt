package com.ashish.android.doordash.search.domain

import com.ashish.android.doordash.search.net.Restaurant
import com.ashish.android.doordash.search.net.RestaurantService
import io.reactivex.Observable
import javax.inject.Inject

class RestaurantRemoteDataSource @Inject constructor(private val restaurantService: RestaurantService) :
    RestaurantDataSource {
    override fun getRestaurants(lat: String, lng: String, offset: Int): Observable<List<Restaurant>> {
        return restaurantService.getRestaurants(lat, lng, offset)
    }
}