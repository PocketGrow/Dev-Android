package com.example.pocketgrow.api.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("msg")
    val msg: String,

    @field:SerializedName("data")
    val data: LoginData
)

data class LoginData(

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("token")
    val token: String
)