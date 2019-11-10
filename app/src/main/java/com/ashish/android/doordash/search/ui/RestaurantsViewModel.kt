package com.ashish.android.doordash.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ashish.android.doordash.search.domain.RestaurantsDataSource
import com.ashish.android.doordash.search.domain.RestaurantsDataSourceFactory
import com.ashish.android.doordash.search.domain.State
import com.ashish.android.doordash.search.net.Restaurant
import javax.inject.Inject

class RestaurantsViewModel @Inject constructor(private val restaurantsDataSourceFactory: RestaurantsDataSourceFactory) :
    ViewModel() {

    private val pageSize = 10

    var restaurantsList: LiveData<PagedList<Restaurant>>

    init {
        // TODO for the purpose of this exercise hardcoding lat and long. In real life either it will be user's live location or entered location
        restaurantsDataSourceFactory.setLatLng("37.422740", "-122.139956")
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        restaurantsList = LivePagedListBuilder(this.restaurantsDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> {
        return Transformations.switchMap<RestaurantsDataSource,
                State>(restaurantsDataSourceFactory.restaurantsDataSourceLiveData, RestaurantsDataSource::state)
    }

    fun isEmptyList(): Boolean {
        return restaurantsList.value?.isEmpty() ?: true
    }
}