package com.example.pocketgrow.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS) // Timeout for connection
            .readTimeout(120, TimeUnit.SECONDS)    // Timeout for reading data
            .writeTimeout(120, TimeUnit.SECONDS)   // Timeout for writing data
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pocketgrow-be-oqi53a6rlq-uc.a.run.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()
        return retrofit.create(ApiService::class.java)
    }
}