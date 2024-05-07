package com.example.searchandcollect.model

import java.util.Date

data class SearchModel(
    val thumbnailUrl: String,
    val displaySiteName: String,
    val dateTime: String,
    val isLiked: Boolean
)
