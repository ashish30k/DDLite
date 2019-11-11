package com.ashish.android.doordash.search.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ashish.android.doordash.R
import com.ashish.android.doordash.R.layout
import com.ashish.android.doordash.core.DoorDashApp
import com.ashish.android.doordash.core.extenstions.replaceFragmentToBackStack

const val SAVED_SCROLL_POSITION_PREF_KEY = "saved_scroll_position_pref_key"

class RestaurantsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_restaurants)
        if (savedInstanceState == null) {
            (this.applicationContext as DoorDashApp).appComponent.getSharedPrefsHelper()
                .putInt(SAVED_SCROLL_POSITION_PREF_KEY, 0)
            replaceFragmentToBackStack(R.id.restaurants_fragment_container, RestaurantsFragment())
        }
    }
    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        when {
            count == 1 -> finish()
            count > 1 -> supportFragmentManager.popBackStack()
            else -> super.onBackPressed()
        }
    }
}
