package com.nokhyun.samplestructure.ui.activity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nokhyun.samplestructure.databinding.ListStaggedItemBinding

data class Item(
    val height: Int
)

class StaggeredAdapter : ListAdapter<Item, StaggedViewHolder>(object : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.height == newItem.height
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaggedViewHolder {
        return StaggedViewHolder(ListStaggedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StaggedViewHolder, position: Int) {
        with(holder) {
            binding.root.updateLayoutParams<ViewGroup.LayoutParams> {
                height = getItem(position).height
            }

            binding.tv.text = getItem(position).height.toString()
        }
    }
}

class StaggedViewHolder(val binding: ListStaggedItemBinding) : ViewHolder(binding.root)