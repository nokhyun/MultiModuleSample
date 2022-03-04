package com.nokhyun.samplestructure.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.nokhyun.samplestructure.model.GalleryModel
import javax.inject.Inject

/**
 * Created by Nokhyun90 on 2022.03.04
 * */
class GalleryDiffUtil: DiffUtil.ItemCallback<GalleryModel>() {
    override fun areItemsTheSame(oldItem: GalleryModel, newItem: GalleryModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: GalleryModel, newItem: GalleryModel): Boolean {
        return oldItem.contentUri == newItem.contentUri
    }
}