package com.nokhyun.samplestructure.ui.activity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.nokhyun.samplestructure.databinding.ListFlexItemBinding
import timber.log.Timber

class FlexBoxTestAdapter : ListAdapter<FlexBoxModel, FlexBoxTestViewHolder>(object : DiffUtil.ItemCallback<FlexBoxModel>() {
    override fun areItemsTheSame(oldItem: FlexBoxModel, newItem: FlexBoxModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: FlexBoxModel, newItem: FlexBoxModel): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlexBoxTestViewHolder {
        return FlexBoxTestViewHolder(ListFlexItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FlexBoxTestViewHolder, position: Int) {
        val lp = holder.itemView.layoutParams as? FlexboxLayoutManager.LayoutParams
        holder.binding.tv.text = getItem(position).name
        val totalWidth = 900
//
        val width = (holder.binding.tv.paint.measureText(getItem(position).name) + 28)
//
        val r = (width / totalWidth) * 2
        Timber.e("flexBasisPercent: $r :: item: ${getItem(position).name}")

        if (position == 4) {
//            lp?.flexBasisPercent = 1f
            lp?.minWidth = 345
            holder.itemView.layoutParams = lp
        }
    }
}

class FlexBoxTestViewHolder(val binding: ListFlexItemBinding) : RecyclerView.ViewHolder(binding.root)

data class FlexBoxModel(
    val name: String
)