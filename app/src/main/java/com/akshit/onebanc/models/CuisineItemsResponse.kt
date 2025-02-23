package com.akshit.onebanc.models

import com.google.gson.annotations.SerializedName

data class CuisineItemsResponse(

	@field:SerializedName("response_code")
	val responseCode: Int? = null,

	@field:SerializedName("response_message")
	val responseMessage: String? = null,

	@field:SerializedName("timetaken")
	val timetaken: String? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("requester_ip")
	val requesterIp: String? = null,

	@field:SerializedName("outcome_code")
	val outcomeCode: Int? = null,

	@field:SerializedName("total_items")
	val totalItems: Int? = null,

	@field:SerializedName("cuisines")
	val cuisines: List<CuisinesItem?>? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)

data class CuisinesItem(

	@field:SerializedName("cuisine_name")
	val cuisineName: String? = null,

	@field:SerializedName("cuisine_image_url")
	val cuisineImageUrl: String? = null,

	@field:SerializedName("cuisine_id")
	val cuisineId: String? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)

data class ItemsItem(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
