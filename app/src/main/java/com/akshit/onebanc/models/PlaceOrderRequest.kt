package com.akshit.onebanc.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PlaceOrderRequest(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("total_amount")
	val totalAmount: String? = null,

	@field:SerializedName("total_items")
	val totalItems: Int? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("item_id")
	val itemId: Int? = null,

	@field:SerializedName("item_price")
	val itemPrice: Int? = null,

	@field:SerializedName("cuisine_id")
	val cuisineId: Int? = null,

	@field:SerializedName("item_quantity")
	val itemQuantity: Int? = null
) : Parcelable
