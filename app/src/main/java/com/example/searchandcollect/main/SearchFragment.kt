package com.example.searchandcollect.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.searchandcollect.data.DustDocuments
import com.example.searchandcollect.databinding.FragmentSearchBinding
import com.example.searchandcollect.model.SearchModel
import com.example.searchandcollect.retrofit.NetWorkClient
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var myAdapter: MyAdapter
    //    private lateinit var documents : DustDocuments
    private var documents = mutableListOf<DustDocuments>()
    // fragment에서 by lazy를 onCreateView 때문에 잘 안한다.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
//        communicateNetWork(setUpDustParameter())
        
    }

    private fun setUpAdapter() {

        val items = listOf(
            SearchModel(
                thumbnailUrl = "123123",
                displaySiteName = "기모띠",
                dateTime = "알빠노",
                isLiked = true
            ), SearchModel(
                thumbnailUrl = "123123",
                displaySiteName = "기모띠",
                dateTime = "알빠노",
                isLiked = true
            ), SearchModel(
                thumbnailUrl = "123123",
                displaySiteName = "기모띠",
                dateTime = "알빠노",
                isLiked = true
            ), SearchModel(
                thumbnailUrl = "123123",
                displaySiteName = "기모띠",
                dateTime = "알빠노",
                isLiked = true
            ), SearchModel(
                thumbnailUrl = "123123",
                displaySiteName = "기모띠",
                dateTime = "알빠노",
                isLiked = true
            )
        )

        myAdapter = MyAdapter(items) // 임시로
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 2) // fragment에서는 context, 액티비티는 this, 어댑터는 context
    }
//    private fun communicateNetWork(param: HashMap<String, String>) = lifecycleScope.launch() {
//        val responseData = NetWorkClient.dustNetWork.getDust(param)
//        Log.d("Parsing Dust ::", responseData.toString())
//
////        documents = responseData.response.dustDocuments
////        items = responseData.response.dustBody.dustItem!!
//    }
//    private fun setUpDustParameter(): HashMap<String, String> {
//        val authKey =
//            "7b62d05be255bbbb6ceb8f69ad8bc0ab"
//
//        return hashMapOf(
//            "Authorization" to "KakaoAK 7b62d05be255bbbb6ceb8f69ad8bc0ab"
//        )
//    }
}
