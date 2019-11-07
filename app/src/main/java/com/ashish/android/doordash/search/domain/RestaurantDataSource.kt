package com.ashish.android.doordash.search.domain

import com.ashish.android.doordash.search.net.Restaurant
import io.reactivex.Observable

interface RestaurantDataSource {
    fun getRestaurants(lat: String, lng: String, offset: Int): Observable<List<Restaurant>>
}