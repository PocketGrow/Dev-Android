package com.example.pocketgrow.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.pocketgrow.ViewModelFactory
import com.example.pocketgrow.api.request.RegisterRequest
import com.example.pocketgrow.databinding.ActivityRegisterBinding
import com.example.pocketgrow.login.LoginActivity
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.datepickerButton.setOnClickListener {
            showDatePicker(it)
        }
        binding.registerTombol.setOnClickListener {

            val name = binding.namaTulisan.text.toString()
            val email = binding.emailTulisan.text.toString()
            val password = binding.passwordTulisan.text.toString()
            val rePassword = binding.rePasswordTulisan.text.toString() // Added line for re-entered password

            //tanggal
            val tanggalTulisan = binding.tanggalTulisan
            val dateOfBirth = tanggalTulisan.text.toString()

            if (password.length < 8) {
                binding.passwordTulisanLayout.error = "Should be more than 7 characters"
            } else {
                binding.passwordTulisanLayout.error = null
            }

            if (password != rePassword) {
                binding.rePasswordTulisanLayout.error = "Password doesn't match"
                return@setOnClickListener
            } else {
                binding.rePasswordTulisanLayout.error = null
            }

            if (name.isNotEmpty() && email.isNotEmpty() && password.length >= 8 && dateOfBirth.isNotEmpty()) {
                binding.progressBar.visibility = View.VISIBLE

                val registerRequest = RegisterRequest(name, email, password, dateOfBirth)
                registerViewModel.register(registerRequest)

                registerViewModel.jikaBerhasil.observe(this) {
                    binding.progressBar.visibility = View.GONE
                    if (it == true) {
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        // Jika gagal, Anda dapat menampilkan pesan kesalahan atau melakukan tindakan lainnya
                        // Misalnya, menampilkan pesan di TextView tertentu
                        Toast.makeText(this, "Failed to register, please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.loginBiruText.setOnClickListener {
            // Redirect ke halaman login
            startActivity(Intent(this, LoginActivity::class.java))
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
        val nameTextView =
            ObjectAnimator.ofFloat(binding.namaTulisanView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.namaTulisanLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTulisanView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailTulisanLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTulisanView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordTulisanLayout, View.ALPHA, 1f).setDuration(100)
        val tanggalTulisanView =
            ObjectAnimator.ofFloat(binding.tanggalLayout, View.ALPHA, 1f).setDuration(100)
        val tanggalLayout =
            ObjectAnimator.ofFloat(binding.tanggalTulisanView, View.ALPHA, 1f).setDuration(100)
        val daftar = ObjectAnimator.ofFloat(binding.registerTombol, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginBiruText, View.ALPHA, 1f).setDuration(100)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                tanggalTulisanView,
                tanggalLayout,
                daftar,
                login
            )
            startDelay = 50
        }.start()
    }
}