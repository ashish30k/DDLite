package com.ashish.android.doordash.search.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ashish.android.doordash.core.BaseViewModel
import com.ashish.android.doordash.search.domain.RestaurantRepo
import com.ashish.android.doordash.search.net.Restaurant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RestaurantsViewModel @Inject constructor(val restaurantRepo: RestaurantRepo) : BaseViewModel() {
    private var restaurantsMutableLiveData = MutableLiveData<List<Restaurant>>()
    val restaurantsLiveData: LiveData<List<Restaurant>> = restaurantsMutableLiveData

    private var noRestaurantMutableLiveData = MutableLiveData<Boolean>()
    val noRestaurantLiveData: LiveData<Boolean> = noRestaurantMutableLiveData

    private var restaurantsList:List<Restaurant>? = null

    fun fetchRestaurants(lat: String, lng: String, offset: Int) {
        restaurantRepo.getRestaurants(lat, lng, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
            }.doAfterTerminate {
            }.subscribe({
                restaurantsList = it
                if (it.isEmpty()) {
                    noRestaurantMutableLiveData.postValue(true)
                } else {
                    restaurantsMutableLiveData.postValue(it)
                }
            }, {
                Log.e(RestaurantsViewModel::class.java.simpleName, it.message)
                errorMutableLiveData.postValue(it.message)
            })
    }

    fun getRestaurantsList() = restaurantsList
}