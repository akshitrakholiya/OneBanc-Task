package com.akshit.onebanc.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akshit.onebanc.infra.network.NetworkResult
import com.akshit.onebanc.infra.repo.HomeRepository
import com.akshit.onebanc.models.CuisineItemsRequest
import com.akshit.onebanc.models.CuisineItemsResponse
import com.akshit.onebanc.models.UserInfoRequest
import com.akshit.onebanc.models.UserInfoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _userInfoResponse: MutableLiveData<NetworkResult<UserInfoResponse>> = MutableLiveData()
    val userInfoResponse: LiveData<NetworkResult<UserInfoResponse>> = _userInfoResponse

    private val _cuisineItemsResponse: MutableLiveData<NetworkResult<CuisineItemsResponse>> = MutableLiveData()
    val cuisineItemsResponse: LiveData<NetworkResult<CuisineItemsResponse>> = _cuisineItemsResponse



    fun getUserInfo(userInfoRequest: UserInfoRequest) = viewModelScope.launch {
        _userInfoResponse.value = NetworkResult.Loading()
        homeRepository.getUserInfo(userInfoRequest).collect { values ->
            _userInfoResponse.value = values
        }
    }

    fun getCuisinesWithItems(cuisineItemsRequest: CuisineItemsRequest) = viewModelScope.launch {
        _cuisineItemsResponse.value = NetworkResult.Loading()
        homeRepository.getCuisinesWithItems(cuisineItemsRequest).collect { values ->
            _cuisineItemsResponse.value = values
        }
    }
}