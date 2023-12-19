package com.example.pocketgrow.api.request

data class UploadTransaksiRequest (
    val name: String,
    val nominal: Int,
    val date: String,
    val type: String,
    val transactionCategoryId: Int
)