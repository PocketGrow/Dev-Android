package com.example.pocketgrow.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.pocketgrow.R
import com.example.pocketgrow.adapter.HomeNewsAdapter
import com.example.pocketgrow.adapter.HomeTransaksiAdapter
import com.example.pocketgrow.adapter.NewsAdapter
import com.example.pocketgrow.api.response.LatestTransaction
import com.example.pocketgrow.api.response.NewsItem
import com.example.pocketgrow.api.response.NewsItemRec
import com.example.pocketgrow.api.response.TransactionItem
import com.example.pocketgrow.databinding.FragmentHomeBinding
import com.example.pocketgrow.databinding.FragmentNewsBinding
import com.example.pocketgrow.di.Injection
import com.example.pocketgrow.helper.AuthPreference
import com.example.pocketgrow.ui.news.NewsViewModel
import com.example.pocketgrow.ui.newsdetail.NewsDetailActivity


class HomeFragment : Fragment() {

    private lateinit var welcomeText: TextView

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeNewsAdapter: HomeNewsAdapter
    private lateinit var homeTransaksiAdapter: HomeTransaksiAdapter

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModel.NewsViewModelFactory(Injection.provideRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val context = requireContext()
        val authPreference = AuthPreference(context)
        val fullname = authPreference.getValue("fullname")

        welcomeText = view.findViewById(R.id.welcomeText)
        // Set the welcome text
        val welcomeMessage = getString(R.string.welcome_message, fullname)
        welcomeText.text = welcomeMessage
////////////// viewpager
        val recyclerViewHome = view.findViewById<RecyclerView>(R.id.recyclerViewHome)
        val icLeft = view.findViewById<ImageView>(R.id.ic_left)
        val icRight = view.findViewById<ImageView>(R.id.ic_right)

        icLeft.setOnClickListener {
            // Scroll RecyclerView to the left
            recyclerViewHome.smoothScrollBy(
                -resources.getDimensionPixelSize(R.dimen.scroll_amount),
                0
            )
        }
        icRight.setOnClickListener {
            // Scroll RecyclerView to the right
            recyclerViewHome.smoothScrollBy(
                resources.getDimensionPixelSize(R.dimen.scroll_amount), // Sesuaikan dengan seberapa jauh Anda ingin menggulir
                0
            )
        }

        homeNewsAdapter = HomeNewsAdapter()
        binding.recyclerViewHome.adapter = homeNewsAdapter
        binding.recyclerViewHome.layoutManager = LinearLayoutManager(requireContext()
            , LinearLayoutManager.HORIZONTAL, false)

        homeViewModel.user.observe(viewLifecycleOwner) {
            homeNewsAdapter.submitData(lifecycle, it)
        }

        homeNewsAdapter.setOnItemClickCallback(object : HomeNewsAdapter.OnItemClickCallback {
            override fun onItemCLicked(data: NewsItemRec) {
                startActivity(
                    Intent(requireContext(), NewsDetailActivity::class.java)
                        .putExtra("news_id", data.id.toString()))
            }
        })

////////////////
        // Set click listener for "see all news" TextView
        view.findViewById<TextView>(R.id.SeeAllNews).setOnClickListener {
            // Navigate to the NewsFragment
            findNavController().navigate(R.id.navigation_news)
        }

        ////////////// viewpager

        homeTransaksiAdapter = HomeTransaksiAdapter()
        binding.recyclerViewTransaksi.adapter = homeTransaksiAdapter
        binding.recyclerViewTransaksi.layoutManager = LinearLayoutManager(requireContext()
            , LinearLayoutManager.HORIZONTAL, false)

        homeViewModel.usertransaction.observe(viewLifecycleOwner) {
            homeTransaksiAdapter.submitData(lifecycle, it)
        }

        homeTransaksiAdapter.setOnItemClickCallback(object : HomeTransaksiAdapter.OnItemClickCallback {
            override fun onItemCLicked(data: LatestTransaction) {
                findNavController().navigate(R.id.navigation_transaksi)
            }
        })

////////////////


        view.findViewById<TextView>(R.id.textSeeAll).setOnClickListener {
            // Navigate to the NewsFragment
            findNavController().navigate(R.id.navigation_transaksi)
        }

        return view
    }



    companion object {
    }
}