package com.nokhyun.samplestructure.utils

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions


/**
 * Created by Nokhyun90 on 2022.03.08
 * */
@GlideModule
class SampleGlideModule: AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {

        // https://bumptech.github.io/glide/javadocs/490/com/bumptech/glide/load/engine/cache/MemorySizeCalculator.Builder.html
        val calculator = MemorySizeCalculator.Builder(context).setMemoryCacheScreens(100f).build()

        // https://bumptech.github.io/glide/javadocs/410/com/bumptech/glide/GlideBuilder.html
        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(1024 * 1024 * 200))
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, 1024 * 1024 * 300))

        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565).disallowHardwareConfig())

        builder.setLogLevel(Log.DEBUG)
    }
}