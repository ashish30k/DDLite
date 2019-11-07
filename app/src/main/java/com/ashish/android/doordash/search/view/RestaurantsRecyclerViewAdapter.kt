package com.ashish.android.doordash.search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ashish.android.doordash.R
import com.ashish.android.doordash.search.net.Restaurant
import com.squareup.picasso.Picasso

class RestaurantsRecyclerViewAdapter(private var restaurantList: MutableList<Restaurant>) :
    RecyclerView.Adapter<RestaurantsRecyclerViewAdapter.RestaurantItemViewHolder>() {
    private var adapterPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_row, parent, false)
        return RestaurantItemViewHolder(view)
    }

    override fun getItemCount() = restaurantList.size

    override fun onBindViewHolder(holder: RestaurantItemViewHolder, position: Int) {
        adapterPosition = holder.adapterPosition
        holder.bind(restaurantList[position])
    }

    /**
     * @param restaurantList new list of restaurants to be displayed on screen
     * call this API if you want to replace all list elements with yours one
     */
    fun setRestaurants(restaurantList: List<Restaurant>) {
        this.restaurantList.clear()
        this.restaurantList.addAll(restaurantList)
        notifyDataSetChanged()
    }

    /**
     * @param restaurantList list of restaurants to be added into existing list being displayed on the screen
     */

    fun addRestaurants(restaurantList: List<Restaurant>) {
        this.restaurantList.addAll(restaurantList)
        notifyDataSetChanged()
    }

    fun getAdapterPosition() = adapterPosition

    class RestaurantItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val restaurantIv = itemView.findViewById<ImageView>(R.id.restaurant_iv)
        private val restaurantNameTv = itemView.findViewById<TextView>(R.id.restaurant_name_tv)
        private val restaurantTypeTv = itemView.findViewById<TextView>(R.id.restaurant_type_tv)
        private val restaurantStatusTv = itemView.findViewById<TextView>(R.id.restaurant_status_tv)

        fun bind(restaurant: Restaurant) {
            Picasso.get().load(restaurant.imagePath).into(restaurantIv)
            restaurantNameTv.text = restaurant.name
            restaurantTypeTv.text = restaurant.description
            restaurantStatusTv.text = getRestaurantStatus(restaurant)
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
}