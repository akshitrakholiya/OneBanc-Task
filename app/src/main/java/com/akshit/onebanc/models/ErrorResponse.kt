package com.akshit.onebanc.models

import com.google.gson.annotations.SerializedName

open class ErrorResponse<T> {

    @SerializedName("error")
    var error: String? = null

    @SerializedName("message")
    var message: T? = null

    @SerializedName("statusCode")
    var statusCode: Int? = 0

}