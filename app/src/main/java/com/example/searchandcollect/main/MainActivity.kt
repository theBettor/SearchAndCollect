package com.example.searchandcollect.main

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.searchandcollect.R
import com.example.searchandcollect.data.DustDocuments
import com.example.searchandcollect.databinding.ActivityMainBinding
import com.example.searchandcollect.retrofit.NetWorkClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

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
        val tabIcons = listOf(R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground)


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
}