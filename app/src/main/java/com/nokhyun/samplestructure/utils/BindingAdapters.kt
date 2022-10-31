package com.nokhyun.samplestructure.utils

import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.nokhyun.samplestructure.R
import timber.log.Timber

object BindingAdapters {

    @BindingAdapter("keyword")
    @JvmStatic
    fun changeColor(tv: TextView, keyword: String) {
        if (keyword.isEmpty()) return

        val start = tv.text.toString().indexOf(keyword)
        val end = start + keyword.length

        if (start != -1) {
            SpannableStringBuilder(tv.text.toString()).apply {
                setSpan(ForegroundColorSpan(ContextCompat.getColor(tv.context, R.color.purple_700)), start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)
            }.run {
                tv.text = this@run
            }
        }
    }
}