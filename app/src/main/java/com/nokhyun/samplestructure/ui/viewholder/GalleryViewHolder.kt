package com.nokhyun.samplestructure.ui.viewholder

import com.bumptech.glide.Glide
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.ListItemGalleryBinding
import com.nokhyun.samplestructure.model.GalleryModel

/**
 * Created by Nokhyun90 on 2022.03.04
 * */
class GalleryViewHolder(
    v: ListItemGalleryBinding,
    private val _onUpdateListener: (position: Int) -> Unit
) : BaseViewHolder<GalleryModel, ListItemGalleryBinding>(v) {

    override fun bind(item: GalleryModel, position: Int) {

        binding.ivListItemGallerySelect.setImageResource(
            if (item.isSelected) {
                R.drawable.outline_done_24
            } else {
                R.drawable.outline_check_circle_white_24
            }
        )

        Glide.with(binding.root.context)
            .load(item.contentUri)
            .centerCrop()
            .into(binding.ivListItemGallery)

        binding.ivListItemGallery.setOnClickListener {
            item.isSelected = !item.isSelected
            _onUpdateListener(position)
        }
    }
}