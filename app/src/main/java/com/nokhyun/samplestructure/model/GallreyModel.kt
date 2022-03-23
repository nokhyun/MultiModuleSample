package com.nokhyun.samplestructure.model

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Nokhyun90 on 2022.03.04
 * */
@Parcelize
data class GalleryModel(
    val displayName: String,
    val contentUri: Uri,
    var bitMap: Bitmap? = null,
    var isSelected: Boolean = false
) : Parcelable
