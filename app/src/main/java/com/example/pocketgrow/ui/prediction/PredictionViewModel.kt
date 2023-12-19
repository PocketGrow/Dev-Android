package com.example.pocketgrow.ui.prediction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pocketgrow.api.response.LatestTransaction
import com.example.pocketgrow.api.response.NewsItemRec
import com.example.pocketgrow.data.UserRepository
import com.example.pocketgrow.ui.home.HomeViewModel

class PredictionViewModel (userRepository: UserRepository) : ViewModel() {

    val user: LiveData<PagingData<NewsItemRec>> =
        userRepository.gethomenews().cachedIn(viewModelScope)
    class PredictionViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PredictionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PredictionViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}