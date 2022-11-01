package com.nokhyun.samplestructure.module

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import timber.log.Timber
import javax.inject.Inject


object CustomModule {
}

@EntryPoint
@InstallIn(ActivityComponent::class)
interface SampleEntryPoint{
    fun getMoney(): Money
}

class Money @Inject constructor(): SampleEntryPoint {
    val money
        get() = "Money!!!"

    override fun getMoney(): Money {
        return this
    }
}