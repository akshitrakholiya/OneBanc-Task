package com.akshit.onebanc.infra.network

sealed class ResponseHandler<out T> {
    data class Loading<T>(val data: T?) : ResponseHandler<T>()
    data class Success<out T>(val data: T) : ResponseHandler<T>()
    data class Error(val code: Int? = null, val error: String? = null) :
        ResponseHandler<Nothing>()

    object NetworkError : ResponseHandler<Nothing>()
}