package com.nokhyun.samplestructure.ui.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Nokhyun90 on 2022.03.04
 * */
abstract class BaseViewHolder<ITEM, V: ViewDataBinding>(val binding: V): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: ITEM, position: Int)
}