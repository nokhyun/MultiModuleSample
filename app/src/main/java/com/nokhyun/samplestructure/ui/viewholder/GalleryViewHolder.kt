package com.nokhyun.samplestructure.ui.viewholder

import android.content.ContentResolver
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.nokhyun.samplestructure.databinding.ListItemGalleryBinding
import com.nokhyun.samplestructure.model.GalleryModel
import timber.log.Timber

/**
 * Created by Nokhyun90 on 2022.03.04
 * */
class GalleryViewHolder(v: ListItemGalleryBinding): BaseViewHolder<GalleryModel, ListItemGalleryBinding>(v) {
    override fun bind(item: GalleryModel, position: Int) {
        binding.ivListItemGallery.setImageBitmap(getBitmap((binding.root.context as AppCompatActivity).contentResolver, item.contentUri))
    }

    private fun getBitmap(contentResolver: ContentResolver, contentUri: Uri) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, contentUri))
    } else {
        MediaStore.Images.Media.getBitmap(contentResolver, contentUri)
    }
}