package com.example.pocketgrow.ui.news

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pocketgrow.api.response.NewsItem
import com.example.pocketgrow.data.UserRepository
import com.example.pocketgrow.di.Injection

class NewsViewModel (userRepository: UserRepository) : ViewModel() {

    val user: LiveData<PagingData<NewsItem>> =
        userRepository.getnews().cachedIn(viewModelScope)

    class NewsViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}