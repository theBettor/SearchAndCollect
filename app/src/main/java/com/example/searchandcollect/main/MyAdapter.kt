package com.example.searchandcollect.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchandcollect.data.remote.ImageDocuments
import com.example.searchandcollect.databinding.ItemRvBinding

class MyAdapter(private var dataList: MutableList<ImageDocuments>) :
    RecyclerView.Adapter<MyAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataList(newDataList: MutableList<ImageDocuments>) {
        dataList = newDataList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindItems(dataList[position])

    }

    class MainViewHolder(private val binding: ItemRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: ImageDocuments) {
            Glide.with(itemView.context)// 이미지를 표시하기 위해 필요하다.
                .load(item.thumbnailUrl)
                .into(binding.ivProfile)
            binding.tvSource.text = item.displaySiteName
            binding.tvDatetime.text = item.dateTime.toString()


//            sourceTextView
        }
    }
    // nested class : 외부 참조 불가, 메모리 누수 발생 확률이 적다
    // inner : 외부(엄마, Adapter) 클래스를 참조 할 수 있음
}