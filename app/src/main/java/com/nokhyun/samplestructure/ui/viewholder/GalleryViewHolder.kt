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

        if (item.isSelected) {
            // todo 이미지 구하면 우측상단에 체크표시로 수정
            binding.ivListItemGallery.setImageResource(R.drawable.ic_launcher_background)
        } else {
            Glide.with(binding.root.context)
                .load(item.contentUri)
                .centerCrop()
                .into(binding.ivListItemGallery)
        }

        binding.ivListItemGallery.setOnClickListener {
            item.isSelected = !item.isSelected
            _onUpdateListener(position)
        }
    }
}