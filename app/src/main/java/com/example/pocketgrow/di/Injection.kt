package com.example.pocketgrow.di

import android.content.Context
import com.example.pocketgrow.api.ApiConfig
import com.example.pocketgrow.data.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiConfig = ApiConfig()
        val apiService = apiConfig.getApiService()
        return UserRepository(apiService, context)
    }

//    fun provideTransaksiRepository(context: Context): TransaksiRepository {
//        val apiConfig = ApiConfig()
//        val apiService = apiConfig.getApiService()
//        return TransaksiRepository(apiService, context)
//    }
}