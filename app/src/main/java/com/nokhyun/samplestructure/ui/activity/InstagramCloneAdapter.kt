package com.nokhyun.samplestructure.ui.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nokhyun.samplestructure.databinding.ListInstagramCloneItemBinding

/**
 * 샘플이므로 내부에서 리스트 생성
 * */
class InstagramCloneAdapter: RecyclerView.Adapter<InstagramCloneViewHolder>() {

    private val values: List<String> = mutableListOf<String>().apply {
        repeat(50){
            add("Hello $it")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstagramCloneViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return InstagramCloneViewHolder(ListInstagramCloneItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: InstagramCloneViewHolder, position: Int) {

        holder.run {
            binding.textValue = values[position]

            binding.executePendingBindings()
        }
    }
}

class InstagramCloneViewHolder(val binding: ListInstagramCloneItemBinding): ViewHolder(binding.root)
