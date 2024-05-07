package com.example.searchandcollect

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.searchandcollect.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

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
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(binding.frame.id, SearchFragment())
        fragmentTransaction.commit()
        // 뷰페이저는 사용하기 싫었다. 탭메인을 사용하기 위해 변수로 만들어주고,
        // 사용할 이미지들을 리스트 변수로 만들고 for문을 통해 index로 구현했다.
        val tabLayout = binding.tapMain
        val tabIcons = listOf(R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground)

        for (i in tabIcons.indices) {
            val tab = tabLayout.getTabAt(i)
            tab?.setIcon(tabIcons[i])
        }
    }
}