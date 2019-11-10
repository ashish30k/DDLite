package com.ashish.android.doordash.search.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashish.android.doordash.R
import com.ashish.android.doordash.core.DoorDashApp
import com.ashish.android.doordash.databinding.FragmentRestaurantsBinding
import com.ashish.android.doordash.search.domain.State
import com.ashish.android.doordash.search.net.Restaurant

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
        getRestaurantsViewModel().restaurantsList.observe(viewLifecycleOwner, Observer {
            restauranstListAdapter.submitList(it)

            val adapterPosition: Int? =
                (context?.applicationContext as DoorDashApp).appComponent.getSharedPrefsHelper()
                    .getInt(SAVED_SCROLL_POSITION_PREF_KEY)

            binding.restaurantsRv.scrollToPosition(adapterPosition!!)
        })

        getRestaurantsViewModel().getState().observe(viewLifecycleOwner, Observer { state ->
            if (getRestaurantsViewModel().isEmptyList() && state == State.LOADING) {
                binding.loadingProgressBar.visibility = View.VISIBLE
            } else {
                binding.loadingProgressBar.visibility = View.GONE
            }

            if (getRestaurantsViewModel().isEmptyList() && state == State.ERROR) {
                binding.errorTv.visibility = View.VISIBLE
            } else {
                binding.errorTv.visibility = View.GONE
            }

            if (!getRestaurantsViewModel().isEmptyList()) {
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveScrollPosition()
    }

    private fun saveScrollPosition() {
        (context?.applicationContext as DoorDashApp).appComponent.getSharedPrefsHelper().putInt(
            SAVED_SCROLL_POSITION_PREF_KEY,
            (binding.restaurantsRv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        )
    }

    private fun getRestaurantsViewModel(): RestaurantsViewModel {
        return (this.activity as RestaurantsActivity).restaurantsViewModel
    }
}