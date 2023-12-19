package com.example.pocketgrow.ui.transaksi

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.pocketgrow.MainActivity
import com.example.pocketgrow.R
import com.example.pocketgrow.ViewModelFactory
import com.example.pocketgrow.api.request.UploadTransaksiRequest
import com.example.pocketgrow.databinding.ActivityUploadTransaksiBinding
import java.util.Calendar

class UploadTransaksiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadTransaksiBinding
    private val uploadTransaksiViewModel by viewModels<UploadTransaksiViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sembunyikan Toolbar
        supportActionBar?.hide()
        setupAction()
        playAnimation()
    }

    private var transactionCategoryId: Int = 0

    private fun setupAction() {
        binding.datepickerButton.setOnClickListener {
            showDatePicker(it)
        }
    binding.addTombol.setOnClickListener {
        val name = binding.namaTulisan.text.toString()

        //nominal
        val nominalString = binding.rupiahTulisan.text.toString()
        val nominal = nominalString.toIntOrNull() ?: 0

        //category
        val categoryRadioGroup = binding.categoryRadioGroup
        val type = when (categoryRadioGroup.checkedRadioButtonId) {
            R.id.expenseRadioButton -> {
                transactionCategoryId = 1 // Set transactionCategoryId to 1 for expense
                "EXPENSE"
            }
            R.id.incomeRadioButton -> {
                transactionCategoryId = 2 // Set transactionCategoryId to 2 for income
                "INCOME"
            }
            else -> ""
        }

        //tanggal
        val tanggalTulisan = binding.tanggalTulisan
        val date = tanggalTulisan.text.toString()

        if (name.isNotEmpty() && nominal != 0 ) {
            binding.progressBar.visibility = View.VISIBLE

            val uploadTransaksiRequest = UploadTransaksiRequest(name, nominal, date, type, transactionCategoryId)
            uploadTransaksiViewModel.uploadtransaksi(uploadTransaksiRequest)

            uploadTransaksiViewModel.jikaBerhasil.observe(this) {
                binding.progressBar.visibility = View.GONE
                if (it == true) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    // Jika gagal, Anda dapat menampilkan pesan kesalahan atau melakukan tindakan lainnya
                    // Misalnya, menampilkan pesan di TextView tertentu
                    Toast.makeText(this, "Registrasi gagal, coba lagi.", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    fun showDatePicker(view: View) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                // Handle the selected date
                val formattedMonth = if (selectedMonth + 1 < 10) "0${selectedMonth + 1}" else "${selectedMonth + 1}"
                val formattedDay = if (selectedDay < 10) "0$selectedDay" else "$selectedDay"
                val selectedDate = "$selectedYear-$formattedMonth-$formattedDay" + "T10:30:00Z"
                binding.tanggalTulisan.text = selectedDate
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun playAnimation() {

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val rupiahTextView =
            ObjectAnimator.ofFloat(binding.rupiahTulisanView, View.ALPHA, 1f).setDuration(100)
        val rupiahEditTextLayout =
            ObjectAnimator.ofFloat(binding.rupiahTulisanLayout, View.ALPHA, 1f).setDuration(100)
        val namaTextView =
            ObjectAnimator.ofFloat(binding.namaTulisanView, View.ALPHA, 1f).setDuration(100)
        val namaEditTextLayout =
            ObjectAnimator.ofFloat(binding.namaTulisanLayout, View.ALPHA, 1f).setDuration(100)
        val categoryTextView =
            ObjectAnimator.ofFloat(binding.categoryTulisanView, View.ALPHA, 1f).setDuration(100)
        val categoryEditTextLayout =
            ObjectAnimator.ofFloat(binding.categoryRadioGroup, View.ALPHA, 1f).setDuration(100)
        val tanggalTextView =
            ObjectAnimator.ofFloat(binding.tanggalTulisanView, View.ALPHA, 1f).setDuration(100)
        val tanggalEditTextLayout =
            ObjectAnimator.ofFloat(binding.tanggalLayout, View.ALPHA, 1f).setDuration(100)

        val daftar = ObjectAnimator.ofFloat(binding.addTombol, View.ALPHA, 1f).setDuration(100)


        AnimatorSet().apply {
            playSequentially(
                title,
                rupiahTextView,
                rupiahEditTextLayout,
                namaTextView,
                namaEditTextLayout,
                categoryTextView,
                categoryEditTextLayout,
                tanggalTextView,
                tanggalEditTextLayout,
                daftar
            )
            startDelay = 50
        }.start()
    }
}