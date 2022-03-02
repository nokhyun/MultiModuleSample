package com.nokhyun.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SampleClient(
    val _gson: GsonConverterFactory,
    val _okHttpClient: OkHttpClient
) {

    val baseUrl = "https://api.github.com"

    inline fun <reified T> defaultClient(): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(_okHttpClient)
            .addConverterFactory(_gson)
            .build()
            .create(T::class.java)
}