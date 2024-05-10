package com.example.searchandcollect.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.searchandcollect.R
import com.example.searchandcollect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), DataListener {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.frame)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 뷰페이저는 사용하기 싫었다. 탭메인을 사용하기 위해 변수로 만들어주고,
        // 사용할 이미지들을 리스트 변수로 만들고 for문을 통해 index로 구현했다.
        val tabLayout = binding.tapMain
        val tabIcons = listOf(R.drawable.search, R.drawable.folder)


        val fragmentList = listOf(
            SearchFragment(), // 인덱스 0에 해당하는 프래그먼트
            MyStorageFragment() // 인덱스 1에 해당하는 다른 프래그먼트
            // 필요한 만큼 추가
        )
        setFragment(SearchFragment())


        for (i in tabIcons.indices) {
            val tab = tabLayout.getTabAt(i)
            tab?.setIcon(tabIcons[i])
        }

        // 각 탭에 대한 클릭 리스너 설정
        for (i in fragmentList.indices) {
            val tab = tabLayout.getTabAt(i)
            if (tab != null) {
                tab.view.setOnClickListener { // view 넣어주니까 해결됨
                    // 클릭한 탭에 해당하는 프래그먼트로 교체
                    setFragment(fragmentList[i])
                }
            }
        }
    }

    // 프래그먼트 교체 함수 정의
    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun saveDataToSharedPreferences(data: String) {
        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("search_text", data)
        editor.apply()
    }

    override fun onDataSaved(data: String) {
        // 이 메서드가 호출되면서 Fragment로부터 데이터를 전달받습니다.
        // 여기서 데이터를 저장하거나 다른 작업을 수행할 수 있습니다.
        saveDataToSharedPreferences(data)
        Log.d("SharedPreferences", "Saved Data: $data")
    }
}