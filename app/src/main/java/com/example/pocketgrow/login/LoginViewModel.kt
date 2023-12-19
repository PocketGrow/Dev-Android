package com.example.pocketgrow.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pocketgrow.api.ApiConfig
import com.example.pocketgrow.api.request.LoginRequest
import com.example.pocketgrow.api.response.LoginResponse
import com.example.pocketgrow.helper.AuthPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val context: Context) : ViewModel() {
    companion object {
        private const val LGM = "LoginViewModel"
    }

    private val _jikaBerhasil = MutableLiveData<Boolean>()
    val jikaBerhasil: LiveData<Boolean> = _jikaBerhasil


    fun login(loginRequest: LoginRequest) {
        val apiService = ApiConfig().getApiService()
        val loginAccount = apiService.login(loginRequest)
        loginAccount.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    var statusCode = response.code()
                    val responseBody = response.body()
                    if (responseBody != null && statusCode == 200) {
                        _jikaBerhasil.value = true
                        val authPreference = AuthPreference(context)
                        authPreference.setValue("key", responseBody.data.token)
                        authPreference.setValue("fullname", responseBody.data.fullname)
                        authPreference.setValue("email", responseBody.data.email)
                    }
                } else {
                    _jikaBerhasil.value = false
                    Log.e(LGM, "fail: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _jikaBerhasil.value = false
                Log.e(LGM, "fail: ${t.message}")
            }
        })
    }

    fun logout() {
        val authPreference = AuthPreference(context)
        authPreference.clear()
    }
}