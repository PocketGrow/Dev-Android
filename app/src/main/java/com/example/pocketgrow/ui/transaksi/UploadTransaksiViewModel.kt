package com.example.pocketgrow.ui.transaksi

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pocketgrow.api.ApiConfig
import com.example.pocketgrow.api.request.UploadTransaksiRequest
import com.example.pocketgrow.api.response.CreateTransactionResponse
import com.example.pocketgrow.helper.AuthPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadTransaksiViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val UTV = "UploadTransaksiViewModel"
    }

    private val _jikaBerhasil = MutableLiveData<Boolean>()
    val jikaBerhasil: LiveData<Boolean> = _jikaBerhasil

    fun uploadtransaksi(uploadTransaksiRequest: UploadTransaksiRequest) {
        val apiService = ApiConfig().getApiService()
        val authPreference = AuthPreference(getApplication())
        val key = authPreference.getValue("key")

        val upload = apiService.uploadTransaction("Bearer $key", uploadTransaksiRequest)
        upload.enqueue(object : Callback<CreateTransactionResponse> {
            override fun onResponse(
                call: Call<CreateTransactionResponse>,
                response: Response<CreateTransactionResponse>
            ) {
                if (response.isSuccessful) {
                    _jikaBerhasil.value = true
                    val responseBody = response.body()
                    Log.i(UTV, "accepted : ${responseBody?.message}")
                } else {
                    _jikaBerhasil.value = false
                    Log.e(UTV, "fail : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CreateTransactionResponse>, t: Throwable) {
                _jikaBerhasil.value = false
                Log.e(UTV, "fail : ${t.message}")
            }
        })
    }
}