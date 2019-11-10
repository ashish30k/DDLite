package com.ashish.android.doordash.search.domain

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.ashish.android.doordash.search.net.Restaurant
import com.ashish.android.doordash.search.net.RestaurantService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsDataSource @Inject constructor(
    private val restaurantService: RestaurantService,
    private val lat: String,
    private val lng: String
) : PageKeyedDataSource<Int, Restaurant>() {

    var state: MutableLiveData<State> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Restaurant>) {
        val uiScope = CoroutineScope(Dispatchers.Main)

        uiScope.launch {
            state.postValue(State.LOADING)
            try {
                withContext(Dispatchers.IO) {
                    val restaurantsList =
                        restaurantService.getRestaurants(lat, lng, 0, params.requestedLoadSize)
                    callback.onResult(restaurantsList, null, restaurantsList.size)
                }
            } catch (e: Exception) {
                state.postValue(State.ERROR)
            }
            state.postValue(State.DONE)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Restaurant>) {
        val uiScope = CoroutineScope(Dispatchers.Main)

        uiScope.launch {
            state.postValue(State.LOADING)
            withContext(Dispatchers.IO) {
                val restaurantsList =
                    restaurantService.getRestaurants("37.422740", "-122.139956", params.key, params.requestedLoadSize)
                callback.onResult(restaurantsList, params.key + 1)
            }
            state.postValue(State.DONE)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Restaurant>) {
    }

}