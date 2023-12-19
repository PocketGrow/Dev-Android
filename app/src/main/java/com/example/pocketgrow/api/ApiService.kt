package com.example.pocketgrow.api

import com.example.pocketgrow.api.request.LoginRequest
import com.example.pocketgrow.api.request.RegisterRequest
import com.example.pocketgrow.api.request.UploadTransaksiRequest
import com.example.pocketgrow.api.response.AllNewsResponse
import com.example.pocketgrow.api.response.AllTransactionResponse
import com.example.pocketgrow.api.response.CreateTransactionResponse
import com.example.pocketgrow.api.response.DeleteTransactionResponse
import com.example.pocketgrow.api.response.DetailResponse
import com.example.pocketgrow.api.response.DetailTransactionResponse
import com.example.pocketgrow.api.response.LatestTransactionResponse
import com.example.pocketgrow.api.response.LoginResponse
import com.example.pocketgrow.api.response.PredictionResponse
import com.example.pocketgrow.api.response.RecNewsResponse
import com.example.pocketgrow.api.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/register")
    fun register(
        @Body signupRequest: RegisterRequest
    ): Call<RegisterResponse>

    @POST("auth/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @GET("news")
    suspend fun getNews(
        @Header("Authorization") authorization: String
    ): AllNewsResponse

    @GET("news/recommendations")
    suspend fun getRecNews(
        @Header("Authorization") authorization: String
    ): RecNewsResponse

    @GET("news/{newsId}")
    fun getDetailNews(
        @Header("Authorization") authorization: String,
        @Path("newsId") newsId: String
    ): Call<DetailResponse>

    @GET("transaction")
    suspend fun getTransaction(
        @Header("Authorization") authorization: String
    ): AllTransactionResponse

    @GET("transaction/latest")
    suspend fun getHomeTransaction(
        @Header("Authorization") authorization: String
    ): LatestTransactionResponse

    @POST("transaction/")
    fun uploadTransaction(
        @Header("Authorization") authorization: String,
        @Body uploadTransaksiRequest: UploadTransaksiRequest
    ): Call<CreateTransactionResponse>

    @GET("transaction/{transactionId}")
    fun getDetailTransaction(
        @Header("Authorization") authorization: String,
        @Path("transactionId") transactionId: String
    ): Call<DetailTransactionResponse>

    @DELETE("transaction/{transactionId}")
    fun deleteTransaction(
        @Header("Authorization") authorization: String,
        @Path("transactionId") transactionId: String
    ): Call<DeleteTransactionResponse>

    @GET("prediction?money={money}")
    suspend fun getPrediction(
        @Header("Authorization") authorization: String,
        @Query("money") money : String
    ): PredictionResponse
}