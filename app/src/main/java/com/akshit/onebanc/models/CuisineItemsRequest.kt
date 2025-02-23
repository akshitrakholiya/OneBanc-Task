package com.akshit.onebanc.models

import com.google.gson.annotations.SerializedName

data class CuisineItemsRequest(

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null
)
