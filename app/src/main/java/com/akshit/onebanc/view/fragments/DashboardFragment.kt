package com.akshit.onebanc.view.fragments

import android.app.Dialog
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
import com.akshit.onebanc.models.CuisinesItem
import com.akshit.onebanc.utilities.ProgressDialog
import com.akshit.onebanc.utilities.ResponseCode
import com.akshit.onebanc.view.adapters.CuisineItemsAdapter
import com.akshit.onebanc.viewmodels.HomeViewModel
import com.google.android.material.circularreveal.CircularRevealLinearLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var dialog: Dialog

    private val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()
    @Inject
    lateinit var connectivityManager: ConnectivityManager
    private var currPage:Int = 1
    private val cntPerPage:Int = 10

    private lateinit var cuisineItemsAdapter: CuisineItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        dialog = ProgressDialog.progressDialog(requireContext())
        setupCuisineRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callCuisinesWithItemsAPI()
    }

    private fun setupCuisineRecyclerView(){
        cuisineItemsAdapter = CuisineItemsAdapter{
            Toast.makeText(requireContext(),it?.cuisineName,Toast.LENGTH_SHORT).show()
        }
        binding.rvCuisine.apply {
            adapter = cuisineItemsAdapter
        }
    }

    private inline fun addCuisineData(newCuisineItemList: List<CuisinesItem?>){
        cuisineItemsAdapter.addCuisineItemList(newCuisineItemList)
    }

    private fun callCuisinesWithItemsAPI(){
        if (connectivityManager.internetAvailable(CoreApplication.appContext)){
            homeViewModel.getCuisinesWithItems(CuisineItemsRequest(cntPerPage,currPage))
        }

        homeViewModel.cuisineItemsResponse.observe(viewLifecycleOwner) { response ->
            when(response){
                is NetworkResult.Success -> {
                    dialog.dismiss()
                    if (ResponseCode.valid(response.data?.responseCode,response.data?.responseMessage)){
                        response.data?.cuisines?.let { addCuisineData(it) }
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                is NetworkResult.Loading -> {
                    dialog.show()
                }
            }
        }
    }

    companion object {
    }
}