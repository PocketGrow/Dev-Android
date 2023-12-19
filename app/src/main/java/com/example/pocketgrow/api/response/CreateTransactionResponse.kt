package com.example.pocketgrow.api.response

import com.google.gson.annotations.SerializedName

data class CreateTransactionResponse(

	@field:SerializedName("data")
	val data: CreateTransactionData,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Category(

	@field:SerializedName("name")
	val name: String
)

data class CreateTransactionData(

	@field:SerializedName("transaction")
	val transaction: Transaction
)

data class Transaction(

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
	val type: String,

	@field:SerializedName("category")
	val category: Category
)
