package com.ashish.android.doordash.search.net

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("cover_img_url") val imagePath: String,
    @SerializedName("status") val status: String,
    @SerializedName("status_type") val statusType: String,
    @SerializedName("delivery_fee") val deliverFee: String
) : Parcelable


