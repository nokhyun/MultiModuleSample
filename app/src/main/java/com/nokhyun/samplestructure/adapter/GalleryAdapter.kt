package com.nokhyun.samplestructure.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nokhyun.samplestructure.adapter.diffutil.GalleryDiffUtil
import com.nokhyun.samplestructure.databinding.ListItemGalleryBinding
import com.nokhyun.samplestructure.model.GalleryModel
import com.nokhyun.samplestructure.ui.viewholder.GalleryViewHolder
import timber.log.Timber

/**
 * Created by Nokhyun90 on 2022.03.04
 * */
class GalleryAdapter(diffUtil: GalleryDiffUtil) : BaseAdapter<GalleryModel, GalleryViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder =
        GalleryViewHolder(ListItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}