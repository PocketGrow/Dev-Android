package com.example.pocketgrow.ui.prediction

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.pocketgrow.R
import com.example.pocketgrow.adapter.HomeNewsAdapter
import com.example.pocketgrow.api.ApiConfig
import com.example.pocketgrow.api.ApiService
import com.example.pocketgrow.api.response.PredictionResponse
import com.example.pocketgrow.databinding.FragmentPredictionBinding
import com.example.pocketgrow.di.Injection
import com.example.pocketgrow.helper.AuthPreference
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header


class PredictionFragment : Fragment() {

    private lateinit var binding: FragmentPredictionBinding
    private lateinit var apiConfig: ApiConfig
    private lateinit var apiService: ApiService

    private val predictionViewModel: PredictionViewModel by viewModels {
        PredictionViewModel.PredictionViewModelFactory(Injection.provideRepository(requireContext()))
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPredictionBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Memanggil setupAction() setelah tata letak ditampilkan
        setupAction()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ApiConfig and ApiService
        apiConfig = ApiConfig()
        apiService = apiConfig.getApiService()
    }
    private fun setupAction() {
        binding.predictionButton.setOnClickListener {
            System.out.println("clicked")

            //nominal
            val nominalString = binding.nominalTextInvestmentValue.text.toString()
            val nominal = nominalString.toIntOrNull() ?: 0

            //category
            val categoryRadioGroup = binding.radioGroupBulan
            val type = when (categoryRadioGroup.checkedRadioButtonId) {
                R.id.radioButton1Tahun -> {
                    "1"
                }
                R.id.radioButton3Tahun -> {
                    "3"
                }
                R.id.radioButton5Tahun -> {
                    "5"
                }
                R.id.radioButton10Tahun -> {
                    "10"
                }
                else -> ""
            }

            if (nominal != 9999999) {

                val context = requireContext()
                val authPreference = AuthPreference(context)
                val token = authPreference.getValue("key")

                // Ambil nilai dari nominalTextInvestmentValue
                val moneyValue = binding.nominalTextInvestmentValue.text.toString()

                val call = apiService.getPrediction("Bearer $token", moneyValue)
                binding.progressBar.visibility = View.VISIBLE


                //viewmodel2an
                call.enqueue(object : Callback<PredictionResponse> {
                    override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                        if (response.isSuccessful) {
//                            callback.onSuccess(response.body() ?: "")
                            println("Prediction successful")
                            println(response.body().toString())

                            // Find the TextView by its ID
                            binding.textNominal.setText(
                                response.body()?.data?.interest?.calculated?.get(0).toString())
                        } else {
//                            callback.onFailure(Exception("Request failed"))
                            println("request failed")
                            println(response.toString())
                        }
                        binding.progressBar.visibility = View.INVISIBLE
                    }

                    override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
//                        callback.onFailure(t)
                        println("error")
                        println(t.message)
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                })

            }
        }
    }
}
//    interface ApiService {
//        @GET("prediction?money=5000")
//        fun getData(@Header("Authorization") authToken: String?): Call<Any>
//    }
//
//    class ApiClient {
//        companion object {
//            private const val BASE_URL = "https://pocketgrow-be-oqi53a6rlq-uc.a.run.app/api/"
//
//            fun create(): ApiService {
//                val retrofit = Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//
//                return retrofit.create(ApiService::class.java)
//            }
//        }
//    }