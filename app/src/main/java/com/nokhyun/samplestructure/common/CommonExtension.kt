package com.nokhyun.samplestructure.common

import android.content.Context
import android.widget.Toast

/** short toast */
fun String?.showToastShort(context: Context?) {
    context?.let { ctx ->
        Toast.makeText(ctx, this?: "", Toast.LENGTH_SHORT).show()
    }
}

/** long toast */
fun String?.showToastLong(context: Context?) {
    context?.let { ctx ->
        Toast.makeText(ctx, this?: "", Toast.LENGTH_LONG).show()
    }
}