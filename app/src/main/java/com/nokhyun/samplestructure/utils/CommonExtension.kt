package com.nokhyun.samplestructure.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.nokhyun.samplestructure.R
import timber.log.Timber

/** short toast */
fun String?.showToastShort(context: Context?) {
    context?.let { ctx ->
        Toast.makeText(ctx, this ?: "", Toast.LENGTH_SHORT).show()
    }
}

/** long toast */
fun String?.showToastLong(context: Context?) {
    context?.let { ctx ->
        Toast.makeText(ctx, this ?: "", Toast.LENGTH_LONG).show()
    }
}

/**
 *  Activity
 *  @param intent
 *  */
fun AppCompatActivity?.goActivity(intent: Intent) {
    this?.startActivity(intent) ?: return
    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
}

/** Activity */
inline fun <reified T : AppCompatActivity> Activity?.goActivity(activity: Class<T>) {
    this?.startActivity(Intent(this, activity)) ?: return
    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
}

/** 단일권한 */
fun AppCompatActivity?.permission(permission: String, callback: (isGranted: Boolean) -> Unit) {
    this?.let { activity ->
        val requestActivity = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when (isGranted) {
                true -> {
                    // 권한 동의
                    Timber.e("동의했음.")
                    callback(true)
                }
                else -> {
                    callback(false)
                }
            }
        }
        requestActivity.launch(permission)
    }
}

fun AppCompatActivity?.goAppSetting() {
    this?.let { activity ->
        Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", activity.packageName, null)
            data = uri
        }.also {
            activity.startActivity(it)
        }
    }
}

