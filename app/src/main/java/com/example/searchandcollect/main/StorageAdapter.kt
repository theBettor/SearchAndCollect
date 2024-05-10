package com.example.searchandcollect.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchandcollect.data.database.model.SearchModel
import com.example.searchandcollect.databinding.ItemRvBinding

class StorageAdapter(
    private var dataList: MutableList<SearchModel>,
) : RecyclerView.Adapter<StorageAdapter.StorageViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StorageAdapter.StorageViewHolder {
        val binding = ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StorageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: StorageAdapter.StorageViewHolder, position: Int) {
        holder.bindItems(dataList[position])
        Log.d("position", position.toString())

    }

    inner class StorageViewHolder(private val binding: ItemRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: SearchModel) {
            Glide.with(itemView.context)// 이미지를 표시하기 위해 필요하다.
                .load(item.thumbnailUrl)
                .into(binding.ivProfile)
            binding.tvSource.text = item.displaySiteName
            binding.tvDatetime.text = item.dateTime.toString()

            if (item.isLiked) {
                binding.redNum1.visibility = View.VISIBLE
            } else {
                binding.redNum1.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                item.isLiked = !item.isLiked
                notifyDataSetChanged()
            }
        }
    }
}

