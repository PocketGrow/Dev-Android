package com.example.pocketgrow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketgrow.R
import com.example.pocketgrow.api.response.LatestTransaction
import com.example.pocketgrow.databinding.HomeTransaksiItemBinding
import com.example.pocketgrow.helper.dateFormat
import java.text.NumberFormat
import java.util.Locale

class HomeTransaksiAdapter : PagingDataAdapter<LatestTransaction, HomeTransaksiAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemCLicked(data: LatestTransaction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeTransaksiItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }

        holder.itemView.setOnClickListener {
            data?.let { it1 -> onItemClickCallback.onItemCLicked(it1) }
        }
    }
    class ViewHolder(private val binding: HomeTransaksiItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: LatestTransaction) {
            binding.judulTulisan.text = data.name

            val formattedNominal = NumberFormat.getNumberInstance(Locale("id", "ID")).format(data.nominal)
            binding.rupiahTulisan.text = "Rp. $formattedNominal"

            // Atur warna teks berdasarkan tipe transaksi
            if (data.type.equals("expense", ignoreCase = true)) {
                binding.rupiahTulisan.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
            } else if (data.type.equals("income", ignoreCase = true)) {
                binding.rupiahTulisan.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LatestTransaction>() {
            override fun areItemsTheSame(oldItem: LatestTransaction, newItem: LatestTransaction): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: LatestTransaction, newItem: LatestTransaction): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}