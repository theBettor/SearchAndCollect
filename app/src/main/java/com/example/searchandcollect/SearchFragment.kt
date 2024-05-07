package com.example.searchandcollect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.searchandcollect.databinding.FragmentSearchBinding
import com.example.searchandcollect.databinding.ItemRvBinding
import com.example.searchandcollect.model.SearchModel


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var myAdapter: MyAdapter
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
}
