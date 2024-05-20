package com.nokhyun.samplestructure.utils

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

@HiltAndroidApp
class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SampleApplication)
            androidLogger()
            modules(firstModule)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("location", "Location", NotificationManager.IMPORTANCE_HIGH)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

// TODO Koin -> Hilt Migration 테스트 샘플용
val firstModule = module {
    // TODO
}