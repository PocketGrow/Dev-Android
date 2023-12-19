package com.example.pocketgrow.data

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pocketgrow.api.ApiService
import com.example.pocketgrow.api.response.NewsItem
import com.example.pocketgrow.helper.AuthPreference

class NewsPaging(private val apiService: ApiService, private val context: Context) : PagingSource<Int, NewsItem>() {

    override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
        return try {
            val authPreference = AuthPreference(context)
            val token = authPreference.getValue("key")

            // Hanya muat data jika position == 1 (halaman pertama)
            val position = params.key ?: INITIAL_PAGE_INDEX
            if (position == INITIAL_PAGE_INDEX) {
                val responseData = apiService.getNews("Bearer $token")
                LoadResult.Page(
                    data = responseData.data.news,
                    prevKey = null,  // Tidak ada halaman sebelumnya
                    nextKey = null   // Tidak ada halaman berikutnya
                )
            } else {
                // Tidak muat data jika position bukan halaman pertama
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}