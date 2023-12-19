package com.example.pocketgrow.api.response

import com.google.gson.annotations.SerializedName

class RegisterResponse (

    @field:SerializedName("msg")
    val msg: String,

    @field:SerializedName("data")
    val data: RegisterData
)
data class RegisterData(

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("token")
    val token: String
)