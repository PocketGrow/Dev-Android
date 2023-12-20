package com.example.pocketgrow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketgrow.R
import com.example.pocketgrow.api.response.TransactionItem
import com.example.pocketgrow.databinding.AllTransactionitemBinding
import com.example.pocketgrow.helper.dateFormat
import java.text.NumberFormat
import java.util.Locale

class TransaksiAdapter : PagingDataAdapter<TransactionItem, TransaksiAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemCLicked(data: TransactionItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AllTransactionitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
    class ViewHolder(private val binding: AllTransactionitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TransactionItem) {
            // Pemotongan kata jika lebih dari 2 nama
            val truncatedName = truncateNamaThumbnail(data.name)
            binding.namaThumbnail.text = truncatedName

            //harga
            val formattedNominal = NumberFormat.getNumberInstance(Locale("id", "ID")).format(data.nominal)
            binding.hargaThumbnail.text = "Rp. $formattedNominal"

            binding.tanggalThumbnail.text = dateFormat(data.date)

            // Atur warna teks berdasarkan tipe transaksi
            if (data.type.equals("expense", ignoreCase = true)) {
                binding.hargaThumbnail.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
            } else if (data.type.equals("income", ignoreCase = true)) {
                binding.hargaThumbnail.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
            }
        }
        private fun truncateNamaThumbnail(namaThumbnail: String?): String {
            val maxLength: Int = 12
            if (namaThumbnail != null) {
                if (namaThumbnail.length > maxLength) {
                    return namaThumbnail?.substring(0, maxLength-3) + "..."
                }
            }
            return namaThumbnail!!
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TransactionItem>() {
            override fun areItemsTheSame(oldItem: TransactionItem, newItem: TransactionItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TransactionItem, newItem: TransactionItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}