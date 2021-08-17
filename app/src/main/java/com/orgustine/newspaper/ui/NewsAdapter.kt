package com.orgustine.newspaper.ui;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orgustine.newspaper.databinding.NewsItemLayoutBinding
import com.orgustine.newspaper.data.model.Result

class NewsAdapter(private val listener: OnItemClickListener)
    : ListAdapter<Result, NewsAdapter.NewsViewHolder>(DiffCallback()) {

    inner class NewsViewHolder(private val binding : NewsItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(newsResult: Result) {
            binding.apply {
                title.text = newsResult.title
                body.text = newsResult.abstract
                source.text = newsResult.source
                publishedDate.text = newsResult.published_date
                Glide.with(binding.root.context)
                    .load(newsResult.media[0].mediaMetadata[2].url)
                    .into(image)
                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        listener.onItemClick(adapterPosition)
                    }
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            NewsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

}
interface OnItemClickListener {
    fun onItemClick(id: Int)
}

class DiffCallback : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Result, newItem: Result) =
        oldItem == newItem
}
