package com.akshit.onebanc.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.akshit.onebanc.databinding.FragmentDashboardBinding
import com.akshit.onebanc.infra.CoreApplication
import com.akshit.onebanc.infra.network.NetworkResult
import com.akshit.onebanc.infra.utils.ConnectivityManager
import com.akshit.onebanc.models.CuisineItemsRequest
import com.akshit.onebanc.utilities.ResponseCode
import com.akshit.onebanc.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    private val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()
    @Inject
    lateinit var connectivityManager: ConnectivityManager
    private var currPage:Int = 1
    private val cntPerPage:Int = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callCuisinesWithItemsAPI()
    }

    private fun callCuisinesWithItemsAPI(){
        if (connectivityManager.internetAvailable(CoreApplication.appContext)){
            homeViewModel.getCuisinesWithItems(CuisineItemsRequest(currPage,cntPerPage))
        }

        homeViewModel.cuisineItemsResponse.observe(viewLifecycleOwner) { response ->
            when(response){
                is NetworkResult.Success -> {

                    if (ResponseCode.valid(response.data?.responseCode,response.data?.responseMessage)){
                        Toast.makeText(requireContext(),response.data?.responseMessage,Toast.LENGTH_SHORT).show()
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    companion object {
    }
}