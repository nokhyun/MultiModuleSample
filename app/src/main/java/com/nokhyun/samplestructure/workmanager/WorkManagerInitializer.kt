package com.nokhyun.samplestructure.workmanager

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import androidx.work.WorkManager
import timber.log.Timber

class WorkManagerInitializer : Initializer<WorkManager> {
    override fun create(context: Context): WorkManager {
        Log.e(this.javaClass.simpleName, "Work create")
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        Log.e(this.javaClass.simpleName, "Work dependencies")
        return emptyList()
    }
}


class ExampleLoggerInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Log.e(this.javaClass.simpleName, "Exam create")
        return Timber.plant(Timber.DebugTree())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        Log.e(this.javaClass.simpleName, "Exam dependencies")
        // WorkManager 가 초기화 된 후 초기화 됨.
        return listOf(WorkManagerInitializer::class.java)
    }
}