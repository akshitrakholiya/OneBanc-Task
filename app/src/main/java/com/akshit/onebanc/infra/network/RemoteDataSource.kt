package com.akshit.onebanc.infra.network

import com.akshit.onebanc.models.CuisineItemsRequest
import com.akshit.onebanc.models.PlaceOrderRequest
import com.akshit.onebanc.models.UserInfoRequest
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: WebApiInterface) {
    suspend fun getUserInfo(userInfoRequest: UserInfoRequest) =
        api.getUserInfo(userInfoRequest,WebApiInterface.BASE_URL+"endpoints/")

    suspend fun getCuisinesWithItems(cuisineItemsRequest: CuisineItemsRequest) =
        api.getCuisinesWithItems(cuisineItemsRequest,"get_item_list",WebApiInterface.BASE_URL+WebApiInterface.MID_PREFIX+"get_item_list")

    suspend fun placeOrder(placeOrderRequest: PlaceOrderRequest) =
        api.placeOrder(placeOrderRequest,"make_payment",WebApiInterface.BASE_URL+WebApiInterface.MID_PREFIX+"make_payment")
}