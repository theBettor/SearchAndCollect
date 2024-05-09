package com.example.searchandcollect.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.searchandcollect.databinding.ItemRvBinding
import com.example.searchandcollect.model.SearchModel

class MyAdapter(private val dataList: List<SearchModel>) : RecyclerView.Adapter<MyAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

    }
    class MainViewHolder(private val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: SearchModel) {
            binding.sourceTextView.text = item.displaySiteName
//            sourceTextView
        }
    }
    // nested class : 외부 참조 불가, 메모리 누수 발생 확률이 적다
    // inner : 외부(엄마, Adapter) 클래스를 참조 할 수 있음
}