package com.akshit.onebanc.infra.repo

import com.akshit.onebanc.infra.network.BaseApiResponse
import com.akshit.onebanc.infra.network.NetworkResult
import com.akshit.onebanc.infra.network.RemoteDataSource
import com.akshit.onebanc.models.CuisineItemsRequest
import com.akshit.onebanc.models.CuisineItemsResponse
import com.akshit.onebanc.models.PlaceOrderRequest
import com.akshit.onebanc.models.PlaceOrderResponse
import com.akshit.onebanc.models.UserInfoRequest
import com.akshit.onebanc.models.UserInfoResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class HomeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun getUserInfo(userInfoRequest: UserInfoRequest): Flow<NetworkResult<UserInfoResponse>> {
        return flow<NetworkResult<UserInfoResponse>> {
            emit(safeApiCall { remoteDataSource.getUserInfo(userInfoRequest) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCuisinesWithItems(cuisineItemsRequest: CuisineItemsRequest): Flow<NetworkResult<CuisineItemsResponse>> {
        return flow<NetworkResult<CuisineItemsResponse>> {
            emit(safeApiCall { remoteDataSource.getCuisinesWithItems(cuisineItemsRequest) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun placeOrder(placeOrderRequest: PlaceOrderRequest): Flow<NetworkResult<PlaceOrderResponse>> {
        return flow<NetworkResult<PlaceOrderResponse>> {
            emit(safeApiCall { remoteDataSource.placeOrder(placeOrderRequest) })
        }.flowOn(Dispatchers.IO)
    }

}