package com.akshit.onebanc.infra.network

import com.akshit.onebanc.models.CuisineItemsRequest
import com.akshit.onebanc.models.CuisineItemsResponse
import com.akshit.onebanc.models.UserInfoRequest
import com.akshit.onebanc.models.UserInfoResponse
import com.akshit.onebanc.utilities.X_FORWARD_PROXY_ACTION
import retrofit2.Response
import retrofit2.http.*

interface WebApiInterface {

    @POST()
    suspend fun getUserInfo(@Body userInfoRequest: UserInfoRequest, @Url url: String): Response<UserInfoResponse>

    @POST()
    suspend fun getCuisinesWithItems(@Body cuisineItemsRequest: CuisineItemsRequest, @Header(X_FORWARD_PROXY_ACTION) proxyAction: String,@Url url: String): Response<CuisineItemsResponse>

    companion object {

        //Mock API
        const val BASE_URL = "https://uat.onebanc.ai/"
        const val MID_PREFIX = "emulator/interview/"
    }
}