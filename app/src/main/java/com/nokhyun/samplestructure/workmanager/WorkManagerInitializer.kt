package com.nokhyun.samplestructure.workmanager

import android.content.Context
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import timber.log.Timber

//class WorkManagerInitializer : Initializer<WorkManager> {
//    override fun create(context: Context): WorkManager {
//        val configuration = Configuration.Builder().build()
//        WorkManager.initialize(context, configuration)
//        return WorkManager.getInstance(context)
//    }
//
//    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
//        return emptyList<Class<out Initializer<*>>>().toMutableList()
//    }
//}


class ExampleLoggerInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        return Timber.plant(Timber.DebugTree())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // WorkManager 가 초기화 된 후 초기화 됨.
//        return listOf(WorkManagerInitializer::class.java).toMutableList()
        return emptyList()
    }
}