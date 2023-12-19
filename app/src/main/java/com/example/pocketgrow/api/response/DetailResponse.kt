package com.example.pocketgrow.api.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

	@field:SerializedName("data")
	val data: DetailData,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class News(

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("source")
	val source: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("headerImg")
	val headerImg: String,

	@field:SerializedName("createDate")
	val createDate: String
)

data class DetailData(

	@field:SerializedName("news")
	val news: News
)
