package com.nokhyun.samplestructure.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.nokhyun.samplestructure.R
import timber.log.Timber

val Int.dp: Int
get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

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

fun TextView.changeKeywordColor(keyword: String?) {
    if (!keyword.isNullOrEmpty()) {
        val start = this.text.indexOf(keyword)
        val end = start + keyword.length

        if (start != -1) {
            SpannableStringBuilder(this.text).apply {
                this@apply.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.purple_700)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }.run {
                this@changeKeywordColor.text = this@run
            }
        }
    }
}

fun Context.hasLocationPermission(): Boolean =
    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED