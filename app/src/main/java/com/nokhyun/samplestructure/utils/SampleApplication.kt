package com.nokhyun.samplestructure.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(object: Timber.DebugTree(){
            override fun createStackElementTag(element: StackTraceElement): String {
                return "${element.fileName}:${element.methodName}:${element.lineNumber}"
            }
        })
    }
}