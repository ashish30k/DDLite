package com.ashish.android.doordash.search.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.ashish.android.doordash.R
import com.ashish.android.doordash.R.layout
import com.ashish.android.doordash.core.DoorDashApp
import com.ashish.android.doordash.search.dagger.RestaurantsComponent
import javax.inject.Inject

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

        restaurantsViewModel =
            ViewModelProviders.of(this, restaurantsViewModelFactory).get(RestaurantsViewModel::class.java)

        supportFragmentManager.beginTransaction().replace(
            R.id.restaurants_fragment_container,
            RestaurantsFragment(),
            RestaurantsFragment::class.java.simpleName
        ).commit()
    }
}
