package com.example.searchandcollect.data.remote.retrofit

import com.example.searchandcollect.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetWorkClient {

    // Gson은 Json 데이터를 가공하는데 있어 좀 더 편하고 효율적으로 관리할 수 있도록 도와주는 라이브러리로
    // google에서 제공하는 Json을 줄여 Gson이라고 부른다고 보면 되겠습니다.
    private const val IMAGE_BASE_URL = "https://dapi.kakao.com"
    // 서비스 URL에 있음


    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    private val imageRetrofit = Retrofit.Builder() // 아래에서 create될때 url 들어가는 곳
        .baseUrl(IMAGE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
        .client(createOkHttpClient())
        .build()

    val imageNetWork: NetWorkInterface = imageRetrofit.create(NetWorkInterface::class.java)

}