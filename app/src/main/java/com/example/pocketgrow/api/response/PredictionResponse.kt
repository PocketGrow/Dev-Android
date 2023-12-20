package com.example.pocketgrow.api.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("gold")
	val gold: List<Any>,

	@field:SerializedName("interest")
	val interest: Interest,

	@field:SerializedName("stock")
	val stock: List<Any>,

	@field:SerializedName("house")
	val house: List<Any>
)

data class Interest(

	@field:SerializedName("rates")
	val rates: List<Any>,

	@field:SerializedName("calculated")
	val calculated: List<Any>
)
