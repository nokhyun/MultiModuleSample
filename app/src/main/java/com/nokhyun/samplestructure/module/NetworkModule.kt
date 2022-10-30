package com.nokhyun.samplestructure.module

import com.nokhyun.data.network.SampleClient
import com.nokhyun.samplestructure.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Nokhyun90 on 2022.02.25
 * */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("network")
    fun provideNetworkInterceptor(): Interceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())

        // TODO
//        response.newBuilder().code(200).build()
//        when(response.code) {
//
//        }

        response.newBuilder().build()
    }

    @Provides
    @Singleton
    @Named("app")
    fun provideAppInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder().apply {
            // TODO Header
//            addHeader("auth", "header test")
        }.build()

        val response = chain.proceed(request)

        response
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(@Named("network") networkInterceptor: Interceptor, @Named("app") appInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(appInterceptor)
            .addNetworkInterceptor(networkInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()

    @Provides
    @Singleton
    fun provideGson(): GsonConverterFactory = GsonConverterFactory.create()


    @Provides
    @Singleton
    fun provideClient(
        gson: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): SampleClient = SampleClient(gson, okHttpClient)

}