package com.ashish.android.doordash.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashish.android.doordash.R
import com.ashish.android.doordash.databinding.RestaurantRowBinding
import com.ashish.android.doordash.search.net.Restaurant
import com.squareup.picasso.Picasso

class RestaurantViewHolder(val binding: RestaurantRowBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(restaurant: Restaurant?, listener: (Restaurant) -> Unit) {
        restaurant?.let {
            Picasso.get().load(restaurant.imagePath).into(binding.restaurantIv)
            binding.restaurantNameTv.text = restaurant.name
            binding.restaurantTypeTv.text = restaurant.description
            binding.restaurantStatusTv.text = getRestaurantStatus(restaurant)
            binding.root.setOnClickListener { listener(restaurant) }
        }
    }

    companion object {
        fun create(parent: ViewGroup): RestaurantViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RestaurantRowBinding.inflate(inflater)
            return RestaurantViewHolder(binding)
        }
    }

    // TODO move somewhere else not in ViewModel because on config changes like Locale change localized resources will not be updated
    private fun getRestaurantStatus(restaurant: Restaurant): String? {
        var status = ""
        when (restaurant.statusType) {
            "open" -> {
                val words = restaurant.status.split(" ").toMutableList()
                for (word in words) {
                    status += word.capitalize() + " "
                }
                status.trim()
            }
            else -> {
                status = itemView.context.getString(R.string.closed)
            }
        }
        return status
    }
}