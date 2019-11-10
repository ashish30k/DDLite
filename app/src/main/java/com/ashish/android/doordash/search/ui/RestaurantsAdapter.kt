package com.ashish.android.doordash.search.ui

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ashish.android.doordash.search.domain.State
import com.ashish.android.doordash.search.net.Restaurant

class RestaurantsAdapter(val restaurantClickListener: ((Restaurant) -> Unit)) :
    PagedListAdapter<Restaurant, RecyclerView.ViewHolder>(RestaurantssDiffCallback) {

    private val RESTAURANT_VIEW_TYPE = 1
    private val PROGRESS_BAR_VIEW_TYPE = 2

    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == RESTAURANT_VIEW_TYPE) {
            return RestaurantViewHolder.create(parent)
        } else {
            return ProgressBarViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == RESTAURANT_VIEW_TYPE) {
            (holder as RestaurantViewHolder).bind(getItem(position), restaurantClickListener)
        } else {
            (holder as ProgressBarViewHolder).bind(state)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position < super.getItemCount()) {
            return RESTAURANT_VIEW_TYPE
        } else {
            return PROGRESS_BAR_VIEW_TYPE
        }
    }

    companion object {
        val RestaurantssDiffCallback = object : DiffUtil.ItemCallback<Restaurant>() {
            override fun areItemsTheSame(oldRestaurant: Restaurant, newRestaurant: Restaurant): Boolean {
                return oldRestaurant.id == newRestaurant.id
            }

            override fun areContentsTheSame(oldProduct: Restaurant, newProduct: Restaurant): Boolean {
                return oldProduct == newProduct
            }
        }
    }

    override fun getItemCount(): Int {
        if (hasFooter()) {
            return super.getItemCount() + 1
        } else {
            return super.getItemCount()
        }
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}