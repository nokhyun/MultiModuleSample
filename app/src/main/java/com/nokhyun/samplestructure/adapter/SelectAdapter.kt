package com.nokhyun.samplestructure.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.ListItemHeaderBinding
import com.nokhyun.samplestructure.databinding.ListItemSelectedBinding
import com.nokhyun.samplestructure.ui.activity.MainActivity

sealed class SelectedUiState {
    data class Header(val header: String) : SelectedUiState()
    data class Body(val bodyValue: BodyValue) : SelectedUiState()
}

// 클래스 내부의 변수 값을 변경하지 말고 새로 생성해야함.
data class BodyValue(val text: String, val isSelected: Boolean = false)

class SelectedViewHolder(val view: ListItemSelectedBinding) : RecyclerView.ViewHolder(view.root)
class SelectedHeaderViewHolder(val view: ListItemHeaderBinding) : RecyclerView.ViewHolder(view.root)

class SelectAdapter(
    private val mainActivity: MainActivity
) : ListAdapter<SelectedUiState, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<SelectedUiState>() {
    override fun areItemsTheSame(oldItem: SelectedUiState, newItem: SelectedUiState): Boolean {
        return (oldItem is SelectedUiState.Header && newItem is SelectedUiState.Header && oldItem.header == newItem.header) ||
                (oldItem is SelectedUiState.Body && newItem is SelectedUiState.Body && oldItem.bodyValue.isSelected == newItem.bodyValue.isSelected)
    }

    override fun areContentsTheSame(oldItem: SelectedUiState, newItem: SelectedUiState): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.list_item_header -> SelectedHeaderViewHolder(ListItemHeaderBinding.inflate(inflater, parent, false))
            R.layout.list_item_selected -> SelectedViewHolder(ListItemSelectedBinding.inflate(inflater, parent, false))
            else -> throw RuntimeException()
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val uiState = getItem(position)) {
            is SelectedUiState.Body -> {
                (holder as SelectedViewHolder).apply {
                    view.mainActivity = mainActivity
                    view.uiState = uiState
                }

                holder.view.executePendingBindings()
            }
            is SelectedUiState.Header -> {
                (holder as SelectedHeaderViewHolder).apply {
                    view.mainActivity = mainActivity
                    view.uiState = uiState
                }

                holder.view.executePendingBindings()
            }
            else -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SelectedUiState.Header -> R.layout.list_item_header
            is SelectedUiState.Body -> R.layout.list_item_selected
        }
    }
}