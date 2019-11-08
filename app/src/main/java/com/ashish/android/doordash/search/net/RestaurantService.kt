package com.ashish.android.doordash.search.net

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantService {
    @GET("restaurant")
    fun getRestaurants(@Query("lat") lat: String, @Query("lng") lng: String, @Query("offset") offset: Int): Observable<List<Restaurant>>
}