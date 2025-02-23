package com.akshit.onebanc.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import android.telecom.Call.Details
import com.google.gson.annotations.SerializedName

@Parcelize
data class PlaceOrderResponse(

	@field:SerializedName("response_code")
	val responseCode: Int? = null,

	@field:SerializedName("response_message")
	val responseMessage: String? = null,

	@field:SerializedName("error_details")
	val errorDetails: String? = null,

	@field:SerializedName("timetaken")
	val timetaken: String? = null,

	@field:SerializedName("txn_ref_no")
	val txnRefNo: String? = null,

	@field:SerializedName("requester_ip")
	val requesterIp: String? = null,

	@field:SerializedName("outcome_code")
	val outcomeCode: Int? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
) : Parcelable
