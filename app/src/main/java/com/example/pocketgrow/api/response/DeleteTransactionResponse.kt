package com.example.pocketgrow.api.response

import com.google.gson.annotations.SerializedName

data class DeleteTransactionResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class TransactionDelete(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("nominal")
	val nominal: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("usersId")
	val usersId: Int? = null,

	@field:SerializedName("transactionCategoryId")
	val transactionCategoryId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class DeleteTransactionData(

	@field:SerializedName("transaction")
	val transaction: Transaction? = null
)
