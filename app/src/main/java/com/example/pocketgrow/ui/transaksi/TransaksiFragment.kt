package com.example.pocketgrow.ui.transaksi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketgrow.R
import com.example.pocketgrow.adapter.NewsAdapter
import com.example.pocketgrow.adapter.TransaksiAdapter
import com.example.pocketgrow.api.response.NewsItem
import com.example.pocketgrow.api.response.TransactionItem
import com.example.pocketgrow.databinding.FragmentTransaksiBinding
import com.example.pocketgrow.di.Injection
import com.example.pocketgrow.ui.newsdetail.NewsDetailActivity


class TransaksiFragment : Fragment() {

    private lateinit var binding: FragmentTransaksiBinding
    private lateinit var transaksiAdapter: TransaksiAdapter

    private val transaksiViewModel: TransaksiViewModel by viewModels {
        TransaksiViewModel.TransaksiViewModelFactory(Injection.provideRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransaksiBinding.inflate(inflater, container, false)
        val view = binding.root

        transaksiAdapter = TransaksiAdapter()
        binding.recyclerViewTransaction.adapter = transaksiAdapter
        binding.recyclerViewTransaction.layoutManager = LinearLayoutManager(requireContext())

        transaksiViewModel.user.observe(viewLifecycleOwner) {
            transaksiAdapter.submitData(lifecycle, it)
        }

        transaksiAdapter.setOnItemClickCallback(object : TransaksiAdapter.OnItemClickCallback {
            override fun onItemCLicked(data: TransactionItem) {
                startActivity(
                    Intent(requireContext(), TransaksiDetailActivity::class.java)
                        .putExtra("transaction_id", data.id.toString())
                )
            }
        })

        // floating button
        binding.tombolTambah.setOnClickListener {
            val intent = Intent(requireContext(), UploadTransaksiActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}