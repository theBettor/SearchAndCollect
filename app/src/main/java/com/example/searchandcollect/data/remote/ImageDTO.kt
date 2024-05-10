package com.example.searchandcollect.data.remote

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Image(val response: ImageResponse)

data class ImageResponse(

    @SerializedName("documents") val documents: List<ImageDocuments>
)

data class ImageDocuments(
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    @SerializedName("display_sitename") val displaySiteName: String,
    @SerializedName("datetime") val dateTime: Date
)

