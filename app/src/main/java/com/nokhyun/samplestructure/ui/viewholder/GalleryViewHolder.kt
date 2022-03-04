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
        binding.ivListItemGallery.setImageURI(item.contentUri)
    }
}