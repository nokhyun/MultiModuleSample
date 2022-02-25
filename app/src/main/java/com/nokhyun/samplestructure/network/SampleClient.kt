package com.nokhyun.samplestructure.network

import com.nokhyun.samplestructure.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SampleClient(
    val _gson: GsonConverterFactory,
    val _okHttpClient: OkHttpClient
) {

    inline fun<reified T> defaultClient(api: T){
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(_okHttpClient)
            .addConverterFactory(_gson)
            .build()
            .create(T::class.java)
    }
}