package com.nokhyun.samplestructure.utils

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.nokhyun.samplestructure.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

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

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}

fun NavController.safeNavigate(
    @IdRes currentDestinationId: Int,
    @IdRes id: Int,
    args: Bundle? = null
) {
    if (currentDestinationId == currentDestination?.id) {
        navigate(id, args)
    }
}

fun Fragment.launchStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}

fun AppCompatActivity.launchCreated(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED, block)
    }
}

fun AppCompatActivity.launchStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}

fun AppCompatActivity.launchResumed(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED, block)
    }
}

fun isOverDeviceVersion(versionCodes: Int): Boolean = Build.VERSION.SDK_INT > versionCodes
fun isLowerDeviceVersion(versionCodes: Int): Boolean = Build.VERSION.SDK_INT < versionCodes

private fun Context.isCheckSelfPermission(permission: String): Boolean = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
fun Context.isCheckSelfPermissions(permissions: Array<String>): Boolean = permissions
    .map { isCheckSelfPermission(it) }
    .toList()
    .all { it }

suspend fun downloadImageAndSave(context: Context, imageUrl: String, fileName: String) = withContext(Dispatchers.IO){
    try {
        val url = URL(imageUrl)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val inputStream: InputStream = connection.inputStream

        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES.plus("/download"))
                put(MediaStore.Images.Media.IS_PENDING, 1) // Pending 상태로 설정
            }

            val uri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            Timber.e("uri: $uri")
            uri?.let {
                context.contentResolver.openOutputStream(it)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                }
                values.clear()
                values.put(MediaStore.Images.Media.IS_PENDING, 0) // Pending 상태 해제
                context.contentResolver.update(uri, values, null, null)
                Timber.e("DownloadImage Image saved successfully at $uri")
            }

        } else {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloadsDir.exists()) {
                downloadsDir.mkdirs()
            }

            val file = File(downloadsDir, fileName)

            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }

            Timber.e("DownloadImage Image saved successfully at ${file.absolutePath}")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Timber.e("DownloadImageError saving image: ${e.message}")
    }
}