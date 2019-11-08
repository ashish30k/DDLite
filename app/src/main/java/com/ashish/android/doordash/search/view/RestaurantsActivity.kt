package com.ashish.android.doordash.search.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.ashish.android.doordash.R
import com.ashish.android.doordash.R.layout
import com.ashish.android.doordash.core.DoorDashApp
import com.ashish.android.doordash.search.dagger.RestaurantsComponent
import javax.inject.Inject

const val SAVED_SCROLL_POSITION_PREF_KEY = "saved_scroll_position_pref_key"

class RestaurantsActivity : AppCompatActivity() {

    lateinit var restaurantsComponent: RestaurantsComponent
    @Inject
    lateinit var restaurantsViewModelFactory: RestaurantsViewModelFactory
    lateinit var restaurantsViewModel: RestaurantsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        restaurantsComponent = (applicationContext as DoorDashApp).appComponent.restaurantsComponent().create()
        restaurantsComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_restaurants)

        if (savedInstanceState == null) {
            (this.applicationContext as DoorDashApp).appComponent.getSharedPrefsHelper()
                .putInt(SAVED_SCROLL_POSITION_PREF_KEY, 0)
        }

        restaurantsViewModel =
            ViewModelProviders.of(this, restaurantsViewModelFactory).get(RestaurantsViewModel::class.java)

        supportFragmentManager.beginTransaction().replace(
            R.id.restaurants_fragment_container,
            RestaurantsFragment(),
            RestaurantsFragment::class.java.simpleName
        ).commit()
    }
}
