package com.example.pocketgrow.ui.newsdetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pocketgrow.api.ApiConfig
import com.example.pocketgrow.api.response.DetailResponse
import com.example.pocketgrow.api.response.News
import com.example.pocketgrow.helper.AuthPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDetailViewModel(application: Application): AndroidViewModel(application) {

    companion object{
        private const val NDVM = "NewsDetailViewModel"
    }

    private val _jikaBerhasil = MutableLiveData<News>()
    val jikaBerhasil: LiveData<News> = _jikaBerhasil

    fun getDetail(data: String) {
        val authPreference = AuthPreference(getApplication())
        val token = authPreference.getValue("key")
        val apiService = ApiConfig().getApiService()
        val detailRequest = apiService.getDetailNews("Bearer $token", data)
        detailRequest.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    var statusCode = response.code()
                    val responseBody = response.body()
                    if (responseBody != null && statusCode == 200) {
                        _jikaBerhasil.value = responseBody.data.news
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e(NDVM, "$errorBody")
                    }
                    Log.e(NDVM, "gagal ${response.message()} coba lagi")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.e(NDVM, "gagal ${t.message} coba lagi")
            }
        })
    }
}