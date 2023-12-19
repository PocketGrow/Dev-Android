package com.example.pocketgrow.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.pocketgrow.MainActivity
import com.example.pocketgrow.R
import com.example.pocketgrow.ViewModelFactory
import com.example.pocketgrow.api.request.LoginRequest
import com.example.pocketgrow.databinding.ActivityLoginBinding
import com.example.pocketgrow.helper.AuthPreference
import com.example.pocketgrow.helper.ifEmailValid
import com.example.pocketgrow.register.RegisterActivity
import com.example.pocketgrow.ui.home.HomeFragment

class LoginActivity : AppCompatActivity() {
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var authPreference: AuthPreference
    private lateinit var binding: ActivityLoginBinding

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authPreference = AuthPreference(this)
        progressBar = findViewById(R.id.progressBar)

        if (authPreference.getValue("status").equals("1")) {
            finishAffinity()
            startActivity(Intent(this, MainActivity::class.java))
        }

        loginViewModel.jikaBerhasil.observe(this) {
            authCheck(it)
        }

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
    private fun authCheck(isSuccess: Boolean) {
        if (isSuccess) {
            finish()
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
    }

    private fun setupAction() {
        binding.loginTombol.setOnClickListener {
            val email = binding.emailTulisan.text.toString()
            val password = binding.passwordTulisan.text.toString()

            if (email.isNotEmpty() && email.ifEmailValid() && password.isNotEmpty() && password.length > 7) {
                showProgressBar()

                val loginRequest = LoginRequest(email, password)
                loginViewModel.login(loginRequest)
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Email atau Password Salah")
                    .setMessage("Silahkan coba lagi")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }

        binding.registerTombol.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

        private fun playAnimation() {

            val gambar = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(100)
            val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)

            val emailTextView =
                ObjectAnimator.ofFloat(binding.emailTulisanView, View.ALPHA, 1f).setDuration(100)
            val emailEditTextLayout =
                ObjectAnimator.ofFloat(binding.emailTulisanLayout, View.ALPHA, 1f).setDuration(100)
            val passwordTextView =
                ObjectAnimator.ofFloat(binding.passwordTulisanView, View.ALPHA, 1f).setDuration(100)
            val passwordEditTextLayout =
                ObjectAnimator.ofFloat(binding.passwordTulisanLayout, View.ALPHA, 1f).setDuration(100)
            val login = ObjectAnimator.ofFloat(binding.loginTombol, View.ALPHA, 1f).setDuration(300)
            val daftar = ObjectAnimator.ofFloat(binding.registerTombol, View.ALPHA, 1f).setDuration(100)

            AnimatorSet().apply {
                playSequentially(
                    gambar,
                    title,
                    emailTextView,
                    emailEditTextLayout,
                    passwordTextView,
                    passwordEditTextLayout,
                    login,
                    daftar
                )
                startDelay = 50
            }.start()
        }
    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}