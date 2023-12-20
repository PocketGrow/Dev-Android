package com.example.pocketgrow.ui.prediction

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
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
    private var predictionData: PredictionResponse? = null
    private var prevMoney: Double? = null
    private lateinit var prevInterestButton: RadioButton
    private lateinit var prevGoldButton : RadioButton
    private lateinit var prevHouseButton : RadioButton
    private var interestIndex: Int = 0
    private var goldIndex: Int = 0
    private var houseIndex: Int = 0

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
        prevInterestButton = binding.radioButton1Tahun
        prevGoldButton = binding.radioButton1TahunEmas
        prevHouseButton = binding.radioButton1TahunHouse

        // init radiogroup NOMINAL state
        prevInterestButton.isChecked = true
        // init radiogroup EMAS state
        prevGoldButton.isChecked = true
        // init radiogroup HOUSE state
        prevHouseButton.isChecked = true


        //interest radio button NOMINAL
        binding.radioGroupBulan.setOnCheckedChangeListener { group, checkedId ->
            prevInterestButton.isChecked = false
            // checkedId is the ID of the RadioButton that is checked
            when (checkedId) {
                R.id.radioButton1Tahun -> {
                    prevInterestButton = binding.radioButton1Tahun
                    interestIndex = 0
                }
                R.id.radioButton3Tahun -> {
                    prevInterestButton = binding.radioButton3Tahun
                    interestIndex = 1
                }
                R.id.radioButton5Tahun -> {
                    prevInterestButton = binding.radioButton5Tahun
                    interestIndex = 2
                }
                R.id.radioButton10Tahun -> {
                    prevInterestButton = binding.radioButton10Tahun
                    interestIndex = 3
                }
            }
            prevInterestButton.isChecked = true
            if (predictionData != null) {
                setData()
            }
        }

        //radio button EMAS
        binding.radioGroupEmas.setOnCheckedChangeListener { group, checkedId ->
            prevGoldButton.isChecked = false
            // checkedId is the ID of the RadioButton that is checked
            when (checkedId) {
                R.id.radioButton1TahunEmas -> {
                    prevGoldButton = binding.radioButton1TahunEmas
                    goldIndex = 0
                }
                R.id.radioButton3TahunEmas -> {
                    prevGoldButton = binding.radioButton3TahunEmas
                    goldIndex = 1
                }
                R.id.radioButton5TahunEmas -> {
                    prevGoldButton = binding.radioButton5TahunEmas
                    goldIndex = 2
                }
                R.id.radioButton10TahunEmas -> {
                    prevGoldButton = binding.radioButton10TahunEmas
                    goldIndex = 3
                }
            }
            prevGoldButton.isChecked = true
            if (predictionData != null) {
                setData()
            }
        }
        //radio button HOUSE
        binding.radioGroupHouse.setOnCheckedChangeListener { group, checkedId ->
            prevHouseButton.isChecked = false
            // checkedId is the ID of the RadioButton that is checked
            when (checkedId) {
                R.id.radioButton1TahunHouse -> {
                    prevHouseButton = binding.radioButton1TahunHouse
                    houseIndex = 0
                }
                R.id.radioButton3TahunHouse -> {
                    prevHouseButton = binding.radioButton3TahunHouse
                    houseIndex = 1
                }
                R.id.radioButton5TahunHouse -> {
                    prevHouseButton = binding.radioButton5TahunHouse
                    houseIndex = 2
                }
                R.id.radioButton10TahunHouse -> {
                    prevHouseButton = binding.radioButton10TahunHouse
                    houseIndex = 3
                }
            }
            prevHouseButton.isChecked = true
            if (predictionData != null) {
                setData()
            }
        }

        binding.predictionButton.setOnClickListener {
            System.out.println("clicked")

            //nominal
            val nominalString = binding.nominalTextInvestmentValue.text.toString()
            val nominal = nominalString.toDoubleOrNull() ?: 0

            // get cache
            val sPrevMoney: String? = getLocalCachePrediction()

            if (sPrevMoney == null || !sPrevMoney.equals(nominal.toString())) {

                val context = requireContext()
                val authPreference = AuthPreference(context)
                val token = authPreference.getValue("key")

                // Ambil nilai dari nominalTextInvestmentValue
                val moneyValue = binding.nominalTextInvestmentValue.text.toString()

                val call = apiService.getPrediction("Bearer $token", moneyValue)

                // Loading setup for other components
                binding.progressBar.visibility = View.VISIBLE
                binding.predictionButton.isEnabled = false

                //viewmodel2an
                call.enqueue(object : Callback<PredictionResponse> {
                    override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                        if (response.isSuccessful) {
//                            callback.onSuccess(response.body() ?: "")
                            println("Prediction successful")
                            println(response.body().toString())

                            if (response.body() != null) {
                                predictionData = response.body()!!
                            }

                            setData()
                        } else {
//                            callback.onFailure(Exception("Request failed"))
                            println("request failed")
                            println(response.toString())
                        }

                        // Loading setup for other components
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.predictionButton.isEnabled = true
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

    private fun setData() {
        // set all
        // Find the TextView by its ID
        binding.textNominal.setText(
            predictionData?.data?.interest?.calculated!!.get(interestIndex).toString())
        binding.textNominalEmas.setText(
            predictionData?.data?.gold!!.get(goldIndex).toString())
        binding.textNominalHouse.setText(
            predictionData?.data?.house!!.get(houseIndex).toString())
    }

//    private fun cachePrediction() {
//        var context = requireContext()
//        var sharePreferences = context.getSharedPreferences(AuthPreference.PENGGUNA_PREP, 0)
//
//        // cache to local storage
//        val mengedit = sharePreferences
//            .edit()
//        mengedit.putString("prediction", prevMoney.toString())
//        mengedit.apply()
//    }

    private fun getLocalCachePrediction(): String? {
        var context = requireContext()
        var sharePreferences = context.getSharedPreferences(AuthPreference.PENGGUNA_PREP, 0)

        return sharePreferences.getString("prediction", null)
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