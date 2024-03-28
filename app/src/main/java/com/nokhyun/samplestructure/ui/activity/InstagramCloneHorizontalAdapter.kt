package com.nokhyun.samplestructure.ui.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nokhyun.samplestructure.databinding.ListInstagramCloneHorizontalItemBinding

/**
 * 샘플이므로 내부에서 리스트 생성
 * */
class InstagramCloneHorizontalAdapter : RecyclerView.Adapter<InstagramCloneHorizontalViewHolder>() {

    private val instagramCloneAdapter by lazy { InstagramCloneAdapter() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstagramCloneHorizontalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return InstagramCloneHorizontalViewHolder(ListInstagramCloneHorizontalItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: InstagramCloneHorizontalViewHolder, position: Int) {

        holder.run {
            binding.rvHorizontal.apply {
                setHasFixedSize(true)
                adapter = instagramCloneAdapter
                layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            }
            binding.executePendingBindings()
        }
    }
}

class InstagramCloneHorizontalViewHolder(val binding: ListInstagramCloneHorizontalItemBinding) : RecyclerView.ViewHolder(binding.root)


