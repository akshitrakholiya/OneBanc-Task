package com.akshit.onebanc.infra.network

import com.akshit.onebanc.models.UserInfoRequest
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: WebApiInterface) {
    suspend fun getUserInfo(userInfoRequest: UserInfoRequest) =
        api.getUserInfo(userInfoRequest,WebApiInterface.BASE_URL+"endpoints/")
}