package com.ashish.android.doordash.search.net

import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantService {
    @GET("restaurant")
    suspend fun getRestaurants(
        @Query("lat") lat: String,
        @Query("lng") lng: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Restaurant>
}