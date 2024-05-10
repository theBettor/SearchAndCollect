package com.example.searchandcollect.main

import android.content.Context
import android.content.SharedPreferences
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
import com.example.searchandcollect.data.remote.retrofit.NetWorkClient
import com.example.searchandcollect.databinding.FragmentSearchBinding
import com.google.gson.Gson
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var myAdapter: MyAdapter
    private lateinit var dataListener: DataListener

    private var documents = mutableListOf<ImageDocuments>()

    private lateinit var sharedPreferences: SharedPreferences
    private var saveUrlData = mutableSetOf<SearchModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = requireContext()
            .getSharedPreferences("local", Context.MODE_PRIVATE)
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

        // setDataListener를 통해 MainActivity와 통신 설정
        setDataListener(requireActivity() as DataListener)


        binding.btnSearch.setOnClickListener {

            lifecycleScope.launch {
                val responseData = NetWorkClient.imageNetWork.getImage(
                    binding.etSearch.text.toString()
                )

                val searchList = responseData.documents.map { docs ->
                    SearchModel(
                        thumbnailUrl = docs.thumbnailUrl,
                        displaySiteName = docs.displaySiteName,
                        dateTime = "2027-04-02",
                        isLiked = false,
                    )
                }.toMutableList()
                setUpAdapter(searchModel = searchList)

                Log.d("Parsing Dust ::", responseData.toString())

                // 가져온 데이터를 documents에 할당
                documents = responseData.documents.toMutableList()

                // RecyclerView에 데이터를 설정하여 갱신
                myAdapter.setDataList(searchList)

                hideKeyboard()
                // 데이터 저장 및 MainActivity로 전달
                saveAndSendData(binding.etSearch.text.toString())
//                saveData()
            }

        }
        loadData()


//        communicateNetWork(setUpImageParameter())


    }



    private fun setUpAdapter(searchModel: List<SearchModel>) {
        // 1. 이 메소드를 onViewCreated 안에 넣어줘도 되고
        // 2. 메소드에 파라미터로 넣어줘도 된다.

        myAdapter = MyAdapter(
            searchModel.toMutableList(),
            itemClickCallback =  { searchModel ->
                saveUrlData.add(searchModel)
            }
        )

//        myAdapter = MyAdapter(searchModel.toMutableList()) // 임시로
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 2) // fragment에서는 context, 액티비티는 this, 어댑터는 context
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    // Fragment에서 데이터를 저장하고 MainActivity로 전달하는 메서드
    private fun saveAndSendData(data: String) {
        // 데이터 저장
        saveData(data)

        // MainActivity로 데이터 전달
        dataListener.onDataSaved(data)
    }
    private fun saveData(data: String) {
        val pref = requireContext().getSharedPreferences("pref", 0)
        // 1번째 인자는 키, 2번째 인자는 실제 담아둘 값
        val editor = pref.edit() // 수정 모드
        editor.putString("search_text", binding.etSearch.text.toString())
        editor.apply() // 저장완료
    }

    private fun loadData() {
        val pref = requireContext().getSharedPreferences("pref", 0)
        // 1번째 인자는 키, 2번째 인자는 데이터가 존재하지 않을경우의 값
        binding.etSearch.setText(pref.getString("search_text", ""))

        // 이런 코드도 있다. 똑같은 것임.
//        val sharedPref = activity?.getSharedPreferences(
//        getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    }
    // MainActivity에서 호출할 때 사용하는 메서드
    fun setDataListener(listener: DataListener) {
        this.dataListener = listener
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

    override fun onPause() {
        super.onPause()

        if (saveUrlData.isNotEmpty()) {
            val gson = Gson()

            val save = saveUrlData.map {
                gson.toJson(it)
            }.toSet()
            sharedPreferences.edit()
                .putStringSet("thumbNailUrlSet", save)
                .commit()
        }
    }
}
