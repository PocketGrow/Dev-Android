package com.example.pocketgrow.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.liveData
import com.example.pocketgrow.api.ApiService
import com.example.pocketgrow.api.response.LatestTransaction
import com.example.pocketgrow.api.response.NewsItem
import com.example.pocketgrow.api.response.NewsItemRec
import com.example.pocketgrow.api.response.TransactionItem

class UserRepository (private val apiService: ApiService, private val context: Context) { //private val storyDatabase: QuoteDatabase
    fun getnews(): LiveData<PagingData<NewsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                NewsPaging(apiService, context)
            }
        ).liveData
    }
    fun gethomenews(): LiveData<PagingData<NewsItemRec>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                HomeNewsPaging(apiService, context)
            }
        ).liveData
    }
    fun gettransaksi(): LiveData<PagingData<TransactionItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                TransaksiPaging(apiService, context)
            }
        ).liveData
    }
    fun gethometransaksi(): LiveData<PagingData<LatestTransaction>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                HomeTransaksiPaging(apiService, context)
            }
        ).liveData
    }
}