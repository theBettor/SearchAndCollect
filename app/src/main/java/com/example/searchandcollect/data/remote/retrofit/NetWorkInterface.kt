package com.example.searchandcollect.data.remote.retrofit

import com.example.searchandcollect.BuildConfig
import com.example.searchandcollect.data.remote.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetWorkInterface {
    @Headers("Authorization: ${BuildConfig.API_KEY}")
//    @Headers("Authorization: KakaoAK ${BuildConfig.REST_API_KEY}")

    @GET("/v2/search/image")
//    suspend fun getDust(@QueryMap param: HashMap<String, String>): DustResponse
    suspend fun getImage(
        @Query("query") query: String,
        @Query("sort") sort: String = "recency",
        @Query("size") size: Int = 80
    ): ImageResponse
} // 해시맵은 요청 값들이 들어간다. 요청변수(request parameter) 키값