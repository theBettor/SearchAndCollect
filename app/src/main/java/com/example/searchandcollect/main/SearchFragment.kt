package com.example.searchandcollect.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.searchandcollect.data.database.model.SearchModel
import com.example.searchandcollect.data.remote.ImageDocuments
import com.example.searchandcollect.databinding.FragmentSearchBinding
import com.example.searchandcollect.data.remote.retrofit.NetWorkClient
import com.google.android.material.internal.ViewUtils.hideKeyboard
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var myAdapter: MyAdapter

    private var isGrid = false

    //    private lateinit var documents : DustDocuments
    private var documents = mutableListOf<ImageDocuments>()
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



        binding.btnSearch.setOnClickListener {

            lifecycleScope.launch {
                val responseData = NetWorkClient.imageNetWork.getImage(
                    binding.etSearch.text.toString()
                )
                Log.d("Parsing Dust ::", responseData.toString())

                // 가져온 데이터를 documents에 할당
                documents = responseData.documents.toMutableList()

                // RecyclerView에 데이터를 설정하여 갱신
                myAdapter.setDataList(documents)

                hideKeyboard()
                saveData()
            }

        }
        loadData()
        setUpAdapter()

//        communicateNetWork(setUpImageParameter())




    }

    private fun setUpAdapter() {

//        val items = listOf(
//            SearchModel(
//                thumbnailUrl = "123123",
//                displaySiteName = "기모띠",
//                dateTime = "알빠노",
//                isLiked = true
//            ), SearchModel(
//                thumbnailUrl = "123123",
//                displaySiteName = "기모띠",
//                dateTime = "알빠노",
//                isLiked = true
//            ), SearchModel(
//                thumbnailUrl = "123123",
//                displaySiteName = "기모띠",
//                dateTime = "알빠노",
//                isLiked = true
//            ), SearchModel(
//                thumbnailUrl = "123123",
//                displaySiteName = "기모띠",
//                dateTime = "알빠노",
//                isLiked = true
//            ), SearchModel(
//                thumbnailUrl = "123123",
//                displaySiteName = "기모띠",
//                dateTime = "알빠노",
//                isLiked = true
//            )
//        )

        myAdapter = MyAdapter(documents) // 임시로
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 2) // fragment에서는 context, 액티비티는 this, 어댑터는 context
    }
    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken,0)
    }

    private fun saveData() {
        val pref = requireContext().getSharedPreferences("pref", 0)
        // 1번째 인자는 키, 2번째 인자는 실제 담아둘 값
        val edit = pref.edit() // 수정 모드
        edit.putString("name", binding.etSearch.text.toString())
        edit.apply() // 저장완료
    }

    private fun loadData() {
        val pref = requireContext().getSharedPreferences("pref", 0)
        // 1번째 인자는 키, 2번째 인자는 데이터가 존재하지 않을경우의 값
        binding.etSearch.setText(pref.getString("name", ""))

        // 이런 코드도 있다. 똑같은 것임.
//        val sharedPref = activity?.getSharedPreferences(
//        getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    }

//    private fun communicateNetWork(param: HashMap<String, String>) = lifecycleScope.launch() {
//        val responseData = NetWorkClient.imageNetWork.getImage(param)
//        Log.d("Parsing Dust ::", responseData.toString())
//
////        documents = responseData.documents.toMutableList()
////        items = responseData.response.dustBody.dustItem!!
//        try {
//            val responseData = NetWorkClient.imageNetWork.getImage(param)
//            Log.d("Parsing Dust ::", responseData.toString())
//
//            // 가져온 데이터를 documents에 할당
//            documents = responseData.documents.toMutableList()
//
//            // RecyclerView에 데이터를 설정하여 갱신
//            myAdapter.setDataList(documents)
//        } catch (e: Exception) {
//            // 네트워크 요청 중 오류가 발생한 경우 처리
//            Log.e("Network Error", "Error: ${e.message}")
//        }
//    }

    private fun setUpImageParameter(): HashMap<String, String> {
//        val authKey =
//            "7b62d05be255bbbb6ceb8f69ad8bc0ab"

        return hashMapOf(
            "Authorization" to "KakaoAK 7b62d05be255bbbb6ceb8f69ad8bc0ab"
        )
    }
}



