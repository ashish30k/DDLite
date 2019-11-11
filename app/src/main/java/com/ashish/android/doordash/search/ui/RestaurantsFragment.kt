package com.ashish.android.doordash.search.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashish.android.doordash.R
import com.ashish.android.doordash.core.DoorDashApp
import com.ashish.android.doordash.databinding.FragmentRestaurantsBinding
import com.ashish.android.doordash.search.dagger.RestaurantsComponent
import com.ashish.android.doordash.search.domain.RestaurantsDataSourceFactory
import com.ashish.android.doordash.search.domain.State
import com.ashish.android.doordash.search.net.Restaurant
import com.ashish.android.doordash.search.net.RestaurantService
import javax.inject.Inject

class RestaurantsFragment : Fragment() {

    interface OnRestaurantSelectListener {
        fun onRestaurantSelected(restaurant: Restaurant)
    }

    private var callback: OnRestaurantSelectListener? = null

    fun setOnRestaurantSelectListener(callback: OnRestaurantSelectListener) {
        this.callback = callback
    }

    private lateinit var restauranstListAdapter: RestaurantsAdapter

    private lateinit var binding: FragmentRestaurantsBinding

    private lateinit var restaurantsComponent: RestaurantsComponent

    @Inject
    lateinit var restaurantService: RestaurantService

    private lateinit var restaurantsDataSourceFactory: RestaurantsDataSourceFactory

    private lateinit var restaurantsViewModel: RestaurantsViewModel

    override fun onAttach(context: Context) {
        restaurantsComponent = (context.applicationContext as DoorDashApp).appComponent.restaurantsComponent().create()
        restaurantsComponent.inject(this)

        super.onAttach(context)

        // TODO for the purpose of this exercise hardcoding lat and long. In real life either it will be user's live location or entered location
        restaurantsDataSourceFactory = RestaurantsDataSourceFactory(restaurantService, "37.422740", "-122.139956")

        restaurantsViewModel =
            ViewModelProviders.of(this, RestaurantsViewModelFactory(restaurantsDataSourceFactory))
                .get(RestaurantsViewModel::class.java)

        if (context is OnRestaurantSelectListener) {
            callback = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_restaurants,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.restaurants_activity_title)

        setupAdapter()
        observeLiveData()
    }

    private fun observeLiveData() {
        restaurantsViewModel.restaurantsList.observe(viewLifecycleOwner, Observer {
            restauranstListAdapter.submitList(it)

            val adapterPosition: Int? =
                (context?.applicationContext as DoorDashApp).appComponent.getSharedPrefsHelper()
                    .getInt(SAVED_SCROLL_POSITION_PREF_KEY)

            binding.restaurantsRv.scrollToPosition(adapterPosition!!)
        })

        restaurantsViewModel.getState().observe(viewLifecycleOwner, Observer { state ->
            if (restaurantsViewModel.isEmptyList() && state == State.LOADING) {
                binding.loadingProgressBar.visibility = View.VISIBLE
            } else {
                binding.loadingProgressBar.visibility = View.GONE
            }

            if (restaurantsViewModel.isEmptyList() && state == State.ERROR) {
                binding.errorTv.visibility = View.VISIBLE
            } else {
                binding.errorTv.visibility = View.GONE
            }

            if (!restaurantsViewModel.isEmptyList()) {
                restauranstListAdapter.setState(state ?: State.DONE)
            }
        })
    }


    private fun setupAdapter() {
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.restaurantsRv.addItemDecoration(decoration)

        val layoutManager = LinearLayoutManager(this.context)
        binding.restaurantsRv.layoutManager = layoutManager

        restauranstListAdapter = RestaurantsAdapter {
            saveScrollPosition()
            callback?.onRestaurantSelected(it)
        }

        binding.restaurantsRv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.restaurantsRv.adapter = restauranstListAdapter
    }

    override fun onStop() {
        super.onStop()
        saveScrollPosition()
    }

    private fun saveScrollPosition() {
        (context?.applicationContext as DoorDashApp).appComponent.getSharedPrefsHelper().putInt(
            SAVED_SCROLL_POSITION_PREF_KEY,
            (binding.restaurantsRv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        )
    }

}