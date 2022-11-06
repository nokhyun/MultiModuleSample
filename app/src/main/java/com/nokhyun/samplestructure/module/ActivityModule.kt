package com.nokhyun.samplestructure.module

import com.nokhyun.samplestructure.adapter.GalleryAdapter
import com.nokhyun.samplestructure.adapter.diffutil.GalleryDiffUtil
import com.nokhyun.samplestructure.delegate.FoodDelegate
import com.nokhyun.samplestructure.delegate.FoodDelegateImpl
import com.nokhyun.samplestructure.model.GalleryModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import timber.log.Timber

/**
 * Created by Nokhyun90 on 2022.03.04
 * */
@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    @ActivityScoped
    fun provideGalleryDiffUtil() = GalleryDiffUtil()

    @Provides
    @ActivityScoped
    fun provideGalleyAdapter(galleryDiffUtil: GalleryDiffUtil) = GalleryAdapter(galleryDiffUtil)
}