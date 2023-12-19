package com.example.pocketgrow.api.response

import com.google.gson.annotations.SerializedName

data class LatestTransactionResponse(

	@field:SerializedName("data")
	val data: LatestData,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class LatestData(

	@field:SerializedName("transaction")
	val transaction: LatestTransaction
)

data class LatestTransaction(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("nominal")
	val nominal: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("usersId")
	val usersId: Int,

	@field:SerializedName("transactionCategoryId")
	val transactionCategoryId: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("type")
	val type: String
)
