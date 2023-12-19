package com.example.pocketgrow.ui.newsdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.pocketgrow.R
import com.example.pocketgrow.ViewModelFactory
import com.example.pocketgrow.api.response.News
import com.example.pocketgrow.databinding.ActivityNewsDetailBinding
import com.example.pocketgrow.helper.dateFormat

class NewsDetailActivity : AppCompatActivity() {

    private val newsDetailViewModel by viewModels<NewsDetailViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sembunyikan Toolbar
        supportActionBar?.hide()

        val storyId = intent.getStringExtra("news_id")

        storyId?.let { newsDetailViewModel.getDetail(it) }

        newsDetailViewModel.jikaBerhasil.observe(this) {
            getNewsDetail(it)
        }
    }

    private fun getNewsDetail(snapgram: News) {
        Glide.with(this)
            .load(snapgram.headerImg)
            .centerCrop()
            .into(binding.newsdetailGambar)

        binding.newsdetailNama.text = snapgram.title
        binding.newsdetailAuthor.text = snapgram.author
        binding.newsdetailDeskripsi.text = snapgram.content
        binding.newsdetailTanggal.text = dateFormat(snapgram.createDate)
    }
}