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
import com.akshit.onebanc.models.PlaceOrderRequest
import com.akshit.onebanc.models.PlaceOrderResponse
import com.akshit.onebanc.models.UserInfoRequest
import com.akshit.onebanc.models.UserInfoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _placeOrderResponse: MutableLiveData<NetworkResult<PlaceOrderResponse>> = MutableLiveData()
    val placeOrderResponse: LiveData<NetworkResult<PlaceOrderResponse>> = _placeOrderResponse

    fun placeOrder(placeOrderRequest: PlaceOrderRequest) = viewModelScope.launch {
        _placeOrderResponse.value = NetworkResult.Loading()
        homeRepository.placeOrder(placeOrderRequest).collect { values ->
            _placeOrderResponse.value = values
        }
    }
}