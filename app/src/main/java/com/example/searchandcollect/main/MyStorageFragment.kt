package com.example.searchandcollect.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.searchandcollect.data.database.model.SearchModel
import com.example.searchandcollect.data.remote.retrofit.NetWorkClient
import com.example.searchandcollect.databinding.FragmentMyStorageBinding
import com.google.gson.Gson

class MyStorageFragment : Fragment() {

    private lateinit var binding: FragmentMyStorageBinding
    private lateinit var storageAdapter: StorageAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyStorageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPreferences = requireContext()
            .getSharedPreferences("local", Context.MODE_PRIVATE)
        val thumbNailUrl = sharedPreferences.getStringSet("thumbNailUrlSet", mutableSetOf())
        Log.d("ㅎㅇ", thumbNailUrl.toString())

        val gson = Gson()

        val searchModel = thumbNailUrl?.map {
            gson.fromJson(it, SearchModel::class.java)
        } ?: emptyList<SearchModel>()
        Log.d("Gd", searchModel.toString())
//        setUpAdapter()
        storageAdapter = StorageAdapter(searchModel.toMutableList(),)
        binding.recyclerView.adapter = storageAdapter
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 2) // fragment에서는 context, 액티비티는 this, 어댑터는 context

    }
    private fun setUpAdapter() {

    }

    override fun onResume() {
        super.onResume()
        storageAdapter.notifyDataSetChanged()
    }

}
