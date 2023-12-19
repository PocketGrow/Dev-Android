package com.example.pocketgrow

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pocketgrow.login.LoginViewModel
import com.example.pocketgrow.register.RegisterViewModel
import com.example.pocketgrow.ui.newsdetail.NewsDetailViewModel
import com.example.pocketgrow.ui.transaksi.TransaksiDetailViewModel
import com.example.pocketgrow.ui.transaksi.UploadTransaksiViewModel

class ViewModelFactory private constructor(
    private val repository: Application
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
            return NewsDetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(UploadTransaksiViewModel::class.java)) {
            return UploadTransaksiViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(TransaksiDetailViewModel::class.java)) {
            return TransaksiDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("View model unknown: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(context)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}