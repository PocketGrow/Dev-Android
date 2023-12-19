package com.example.pocketgrow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocketgrow.api.response.NewsItemRec
import com.example.pocketgrow.databinding.HomeNewsItemBinding

class HomeNewsAdapter : PagingDataAdapter<NewsItemRec, HomeNewsAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemCLicked(data: NewsItemRec)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
    class ViewHolder(private val binding: HomeNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NewsItemRec) {
            binding.homeNamaThumbnail.text = data.title
            Glide.with(itemView)
                .load(data.headerImg)
                .centerCrop()
                .into(binding.homeGambarThumbnail)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsItemRec>() {
            override fun areItemsTheSame(oldItem: NewsItemRec, newItem: NewsItemRec): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NewsItemRec, newItem: NewsItemRec): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
