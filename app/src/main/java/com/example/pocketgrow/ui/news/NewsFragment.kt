package com.example.pocketgrow.ui.news

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketgrow.R
import com.example.pocketgrow.ViewModelFactory
import com.example.pocketgrow.adapter.NewsAdapter
import com.example.pocketgrow.api.response.AllNewsResponse
import com.example.pocketgrow.api.response.NewsItem
import com.example.pocketgrow.databinding.FragmentNewsBinding
import com.example.pocketgrow.di.Injection
import com.example.pocketgrow.login.LoginViewModel
import com.example.pocketgrow.ui.newsdetail.NewsDetailActivity

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModel.NewsViewModelFactory(Injection.provideRepository(requireContext()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        val view = binding.root

        newsAdapter = NewsAdapter()
        binding.recyclerViewNews.adapter = newsAdapter
        binding.recyclerViewNews.layoutManager = LinearLayoutManager(requireContext())

        newsViewModel.user.observe(viewLifecycleOwner) {
            newsAdapter.submitData(lifecycle, it)
        }

        newsAdapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
            override fun onItemCLicked(data: NewsItem) {
                startActivity(
                    Intent(requireContext(), NewsDetailActivity::class.java)
                        .putExtra("news_id", data.id.toString()))
                val text = "halo"
                Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
            }
        })
        return view
    }
}