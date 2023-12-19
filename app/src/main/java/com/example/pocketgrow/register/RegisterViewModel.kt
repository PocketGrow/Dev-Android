package com.example.pocketgrow.register

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pocketgrow.api.ApiConfig
import com.example.pocketgrow.api.request.RegisterRequest
import com.example.pocketgrow.api.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel (private val context: Context): ViewModel() {
    companion object {
        private const val RVM = "RegisterViewModel"
    }
    private val _jikaBerhasil = MutableLiveData<Boolean>()
    val jikaBerhasil: LiveData<Boolean> = _jikaBerhasil

    fun register(registerRequest: RegisterRequest){
        val apiService = ApiConfig().getApiService()
        val register = apiService.register(registerRequest)
        register.enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if(response.isSuccessful){
                    _jikaBerhasil.value=true
                    val responseBody = response.body()
                    Log.i(RVM,"accepted : ${responseBody?.msg}")
                }
                else {
                    _jikaBerhasil.value=false
                    Log.e(RVM,"fail : ${response?.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _jikaBerhasil.value=false
                Log.e(RVM,"fail : ${t?.message}")
            }

        })
    }
}