package com.example.pocketgrow.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pocketgrow.api.response.LatestTransaction
import com.example.pocketgrow.api.response.NewsItem
import com.example.pocketgrow.api.response.NewsItemRec
import com.example.pocketgrow.api.response.TransactionItem
import com.example.pocketgrow.data.UserRepository
import com.example.pocketgrow.ui.news.NewsViewModel

class HomeViewModel (userRepository: UserRepository) : ViewModel() {

    val user: LiveData<PagingData<NewsItemRec>> =
        userRepository.gethomenews().cachedIn(viewModelScope)

    val usertransaction: LiveData<PagingData<LatestTransaction>> =
        userRepository.gethometransaksi().cachedIn(viewModelScope)

    class NewsViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}