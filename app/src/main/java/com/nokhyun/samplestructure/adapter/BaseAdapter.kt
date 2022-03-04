package com.nokhyun.samplestructure.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Nokhyun90 on 2022.03.04
 * */
abstract class BaseAdapter<ITEM, VH : RecyclerView.ViewHolder>(diffUtil: DiffUtil.ItemCallback<ITEM>) : ListAdapter<ITEM, VH>(diffUtil) {
}