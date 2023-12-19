package com.example.pocketgrow.api.response

import com.google.gson.annotations.SerializedName

data class RecNewsResponse(

	@field:SerializedName("data")
	val data: Datarec,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class NewsItemRec(

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

data class Datarec(

	@field:SerializedName("news")
	val news: List<NewsItemRec>
)
