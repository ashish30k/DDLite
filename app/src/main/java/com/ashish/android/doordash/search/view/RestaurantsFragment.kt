package com.ashish.android.doordash.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashish.android.doordash.R
import com.ashish.android.doordash.R.layout
import com.ashish.android.doordash.core.displayToast

class RestaurantsFragment : Fragment() {

    private lateinit var restaurantsReyclerview: RecyclerView

    private lateinit var restaurantsRecyclerViewAdapter: RestaurantsRecyclerViewAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = (activity as RestaurantsActivity).restaurantsViewModel
        viewModel.fetchRestaurants("37.422740", "-122.139956", 0)
        viewModel.restaurantsLiveData.observe(this, Observer {
            restaurantsRecyclerViewAdapter.addRestaurants(it)
        })

        viewModel.noRestaurantLiveData.observe(viewLifecycleOwner, Observer {
            context?.displayToast(getString(R.string.no_restaurants_found), Toast.LENGTH_LONG)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(layout.fragment_restaurants_layout, container, false)
        restaurantsReyclerview = rootView.findViewById(R.id.search_results_rv)

        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        restaurantsReyclerview.addItemDecoration(decoration)

        val layoutManager = LinearLayoutManager(this.context)
        restaurantsReyclerview.layoutManager = layoutManager

        restaurantsRecyclerViewAdapter = RestaurantsRecyclerViewAdapter((mutableListOf()))
        restaurantsReyclerview.adapter = restaurantsRecyclerViewAdapter
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.restaurants_activity_title)
    }
}