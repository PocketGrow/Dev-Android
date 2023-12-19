package com.example.pocketgrow.ui.prediction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pocketgrow.R
import com.example.pocketgrow.databinding.FragmentPredictionBinding


class PredictionFragment : Fragment() {

    private lateinit var binding: FragmentPredictionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPredictionBinding.inflate(inflater, container, false)
        val view = binding.root

        setupAction()
        return inflater.inflate(R.layout.fragment_prediction, container, false)
    }
    private fun setupAction() {
        binding.predictionButton.setOnClickListener {

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

            if (nominal != 0) {
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }
}