package com.example.pocketgrow.ui.transaksi

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pocketgrow.api.ApiConfig
import com.example.pocketgrow.api.response.DetailTransactionResponse
import com.example.pocketgrow.api.response.TransactionDetail
import com.example.pocketgrow.helper.AuthPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransaksiDetailViewModel (application: Application): AndroidViewModel(application) {

    companion object{
        private const val TDVM = "TransaksiDetailViewModel"
    }

    private val _jikaBerhasil = MutableLiveData<TransactionDetail>()
    val jikaBerhasil: LiveData<TransactionDetail> = _jikaBerhasil

    fun getDetailTr(data: String) {
        val authPreference = AuthPreference(getApplication())
        val token = authPreference.getValue("key")
        val apiService = ApiConfig().getApiService()
        val detailRequest = apiService.getDetailTransaction("Bearer $token", data)
        detailRequest.enqueue(object : Callback<DetailTransactionResponse> {
            override fun onResponse(
                call: Call<DetailTransactionResponse>,
                response: Response<DetailTransactionResponse>
            ) {
                if (response.isSuccessful) {
                    var statusCode = response.code()
                    val responseBody = response.body()
                    if (responseBody != null && statusCode == 200) {
                        _jikaBerhasil.value = responseBody.data.transaction
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e(TDVM , "$errorBody")
                    }
                    Log.e(TDVM , "gagal ${response.message()} coba lagi")
                }
            }

            override fun onFailure(call: Call<DetailTransactionResponse>, t: Throwable) {
                Log.e(TDVM , "gagal ${t.message} coba lagi")
            }
        })
    }
}