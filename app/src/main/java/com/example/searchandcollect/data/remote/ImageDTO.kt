package com.example.searchandcollect.data.remote

import com.google.gson.annotations.SerializedName
import java.util.Date

//  서버 통신시 request body 또는 response body에서 사용할 JSON형태의 모델 클래스 작성 -> Kotlin에서는 data class 형태로 작성한다!
//- 변수명은 원래 서버에서 사용하는 값과 똑같이 작성해야 된다.
//- 만약 앱 내에서 다른 변수명으로 사용하고 싶다면 아래 코드처럼 ' @SerializedName("서버에서 변수명") val 앱내변수명:자료형 ' 을 사용한다.
data class Image(val response: ImageResponse)

data class ImageResponse(
//    @SerializedName("documents")
//    val dustDocuments: DustDocuments
    @SerializedName("documents") val documents: List<ImageDocuments>
)

data class ImageDocuments(
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    @SerializedName("display_sitename") val displaySiteName: String, // site_name해줬다가 다시 정정
    @SerializedName("datetime") val dateTime: Date
)

