package com.akshit.onebanc.infra.repo

import com.akshit.onebanc.infra.network.BaseApiResponse
import com.akshit.onebanc.infra.network.NetworkResult
import com.akshit.onebanc.infra.network.RemoteDataSource
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

}