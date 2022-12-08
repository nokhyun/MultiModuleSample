package com.nokhyun.samplestructure.module

import android.content.Context
import com.nokhyun.samplestructure.delegate.FoodDelegate
import com.nokhyun.samplestructure.delegate.FoodDelegateImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

//    @Provides
//    @ViewModelScoped
//    fun provideFoodDelegate(@ApplicationContext context: Context): FoodDelegate = FoodDelegateImpl()
}