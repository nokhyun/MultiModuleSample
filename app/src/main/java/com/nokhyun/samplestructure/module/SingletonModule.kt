package com.nokhyun.samplestructure.module

import com.nokhyun.samplestructure.delegate.FoodDelegate
import com.nokhyun.samplestructure.delegate.FoodDelegateImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

//    @Provides
//    @Singleton
//    fun provideFoodDelegate(): FoodDelegate = FoodDelegateImpl()
}