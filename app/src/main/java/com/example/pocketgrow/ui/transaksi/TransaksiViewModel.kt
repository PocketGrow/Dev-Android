package com.example.pocketgrow.ui.transaksi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pocketgrow.api.response.TransactionItem
import com.example.pocketgrow.data.UserRepository
import com.example.pocketgrow.ui.news.NewsViewModel

class TransaksiViewModel (userRepository: UserRepository) : ViewModel() {

    val user: LiveData<PagingData<TransactionItem>> =
        userRepository.gettransaksi().cachedIn(viewModelScope)

    class TransaksiViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TransaksiViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TransaksiViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}