package com.ashish.android.doordash.search.domain

import com.ashish.android.doordash.search.net.Restaurant
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantRepo @Inject constructor(val remoteDataSource: RestaurantRemoteDataSource) {
    fun getRestaurants(lat: String, lng: String, offset: Int): Observable<List<Restaurant>> {
        return remoteDataSource.getRestaurants(lat, lng, offset)
    }
}