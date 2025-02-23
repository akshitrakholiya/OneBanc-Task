package com.akshit.onebanc.view.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshit.onebanc.R
import com.akshit.onebanc.databinding.FragmentDashboardBinding
import com.akshit.onebanc.infra.CoreApplication
import com.akshit.onebanc.infra.network.NetworkResult
import com.akshit.onebanc.infra.utils.ConnectivityManager
import com.akshit.onebanc.models.CuisineItemsRequest
import com.akshit.onebanc.models.CuisineItemsResponse
import com.akshit.onebanc.models.CuisinesItem
import com.akshit.onebanc.utilities.ARG_CUISINE_INFO
import com.akshit.onebanc.utilities.PaginationListener
import com.akshit.onebanc.utilities.ProgressDialog
import com.akshit.onebanc.utilities.ResponseCode
import com.akshit.onebanc.view.adapters.CuisineItemsAdapter
import com.akshit.onebanc.view.adapters.CuisineItemsAdapter.Companion.setupSmoothScroll
import com.akshit.onebanc.viewmodels.HomeViewModel
import com.google.android.material.snackbar.Snackbar
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

    private var isLoading = false
    private var maxPageSize = 1
    private var isLastItem = false

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
        addCuisineItemListScrollListener()
        resetPaginationData()
        callCuisinesWithItemsAPI()
    }

    private fun resetPaginationData() {
        currPage = 1
        isLoading = false
        maxPageSize = 1
        isLastItem = false
    }

    private fun setupCuisineRecyclerView(){
        cuisineItemsAdapter = CuisineItemsAdapter{ cuisineInfo->
            val bundle = Bundle()
            bundle.putParcelable(ARG_CUISINE_INFO,cuisineInfo)
            findNavController().navigate(R.id.action_dashboardFragment_to_cuisineInfoFragment,bundle)
        }
        binding.rvCuisine.apply {
            adapter = cuisineItemsAdapter
        }
        binding.rvCuisine.setupSmoothScroll(requireContext())
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

                        paginationRelatedValidation(response.data)

                        response.data?.cuisines?.let { addCuisineData(it) }
                    }
                }
                is NetworkResult.Error -> {
                    Snackbar.make(binding.root,response.message.toString(), Snackbar.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                is NetworkResult.Loading -> {
                    dialog.show()
                    isLoading = true
                }
            }
        }
    }


    private fun paginationRelatedValidation(_response: CuisineItemsResponse?) {
        maxPageSize = _response?.totalPages ?: -1
        if ((_response?.page ?: 0) >= maxPageSize) {
            isLastItem = true
        }

        _response?.cuisines?.let {
            addCuisineData(it)
        }
        isLoading = false
    }

    private fun addCuisineItemListScrollListener(){
        binding.rvCuisine.addOnScrollListener(object : PaginationListener(binding.rvCuisine.layoutManager as LinearLayoutManager){
            override fun loadMoreItems() {
                if(!isLastItem){
                    currPage++
                    callCuisinesWithItemsAPI()
                }
            }

            override fun isLastPage(): Boolean {
                return isLastItem
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })
    }

    companion object {
    }
}