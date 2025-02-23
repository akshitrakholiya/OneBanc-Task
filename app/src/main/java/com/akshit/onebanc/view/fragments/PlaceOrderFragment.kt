package com.akshit.onebanc.view.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.akshit.onebanc.R
import com.akshit.onebanc.databinding.FragmentPlaceOrderBinding
import com.akshit.onebanc.infra.CoreApplication
import com.akshit.onebanc.infra.network.NetworkResult
import com.akshit.onebanc.infra.utils.ConnectivityManager
import com.akshit.onebanc.models.DataItem
import com.akshit.onebanc.models.ItemsItem
import com.akshit.onebanc.models.PlaceOrderRequest
import com.akshit.onebanc.utilities.ARG_CART_ITEMS
import com.akshit.onebanc.utilities.ProgressDialog
import com.akshit.onebanc.utilities.ResponseCode
import com.akshit.onebanc.view.adapters.FinalSummaryAdapter
import com.akshit.onebanc.viewmodels.OrdersViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class PlaceOrderFragment : Fragment() {

    private lateinit var binding: FragmentPlaceOrderBinding
    private var finalCartItems:ArrayList<ItemsItem> = arrayListOf()
    private var placeOrderItems:MutableList<DataItem> = mutableListOf()
    private var totalAmount:Double = 0.0
    private var finalAmount:Double = 0.0
    private var totalQty:Int = 0

    //percentage
    private var sgst:Double = 2.5
    private var cgst:Double = 2.5

    private val orderViewModel: OrdersViewModel by viewModels()
    @Inject
    lateinit var connectivityManager: ConnectivityManager
    private lateinit var dialog: Dialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            finalCartItems = it.getParcelableArrayList<ItemsItem>(ARG_CART_ITEMS) as ArrayList<ItemsItem>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceOrderBinding.inflate(inflater, container, false)
        dialog = ProgressDialog.progressDialog(requireContext())
        setupViewClickListeners()
        return binding.root
    }

    private fun setupViewClickListeners() {
        binding.tvCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.acbPlaceOrder.setOnClickListener {
            callPlaceOrderAPI()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calculateTotal()
        setupFinalSummaryRecyclerView()
    }

    private fun calculateTotal() {
        totalAmount = 0.0
        totalQty = 0
        finalAmount = 0.0
        placeOrderItems.clear()

        CoroutineScope(Dispatchers.Default).launch {
            for (item in finalCartItems) {
                val price = item.price?.toDoubleOrNull()
                if (price != null) {
                    totalAmount += price * item.quantity
                    totalQty += item.quantity
                }
                placeOrderItems.add(DataItem(itemId = item.id?.toIntOrNull(), itemPrice = price?.toInt(), itemQuantity = item.quantity))
            }

            // Calculate SGST and CGST
            val sgstCharge = totalAmount * sgst / 100
            val cgstCharge = totalAmount * cgst / 100
            finalAmount = totalAmount + sgstCharge + cgstCharge

            withContext(Dispatchers.Main){
                binding.tvTotalQty.text = "$totalQty Qty"
                binding.tvTotalPrice.text = "Rs. $finalAmount"
            }
        }
    }

    private fun setupFinalSummaryRecyclerView() {
        binding.rvFinalSummary.apply {
            adapter = FinalSummaryAdapter(finalCartItems)
        }
    }

    private fun callPlaceOrderAPI() {
        if (connectivityManager.internetAvailable(CoreApplication.appContext)){
            orderViewModel.placeOrder(PlaceOrderRequest(data = placeOrderItems.toList(), totalAmount = finalAmount.toString(), totalItems = totalQty))
        }

        orderViewModel.placeOrderResponse.observe(viewLifecycleOwner) { response ->
            when(response){
                is NetworkResult.Success -> {
                    if (ResponseCode.valid(response.data?.responseCode,response.data?.responseMessage)){
                        Snackbar.make(binding.root,response.data?.responseMessage.toString(),Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack(R.id.dashboardFragment,true)
                    }else{
                        Snackbar.make(binding.root,response.data?.errorDetails.toString(),Snackbar.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                }
                is NetworkResult.Error -> {
                    dialog.dismiss()
                }
                is NetworkResult.Loading -> {
                    dialog.show()
                }
            }
        }
    }

}