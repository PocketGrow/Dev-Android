package com.example.pocketgrow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.view.View
import com.example.pocketgrow.helper.AuthPreference
import com.example.pocketgrow.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 3000 // Waktu tampilan splash dalam milidetik (di sini 3000 ms atau 3 detik)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        // Menggunakan Handler untuk menunda navigasi ke MainActivity
//        Handler().postDelayed({
//            // Membuat Intent untuk berpindah ke MainActivity
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)

            val authPreference = AuthPreference(this)
            val email = authPreference.getValue("key")
            val isUserLoggedIn = authPreference.getValue("key") != null

            Handler().postDelayed({

                if (isUserLoggedIn) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
            // Menutup SplashActivity agar tidak bisa kembali ke halaman splash dengan tombol back
            finish()
        }, splashTimeOut)
    }
}