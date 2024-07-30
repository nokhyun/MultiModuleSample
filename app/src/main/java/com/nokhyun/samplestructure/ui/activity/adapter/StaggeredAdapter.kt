package com.nokhyun.samplestructure.ui.activity.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.nokhyun.samplestructure.databinding.ListStaggedItemBinding
import timber.log.Timber


data class Item(
    val width: Int = 300,
    val height: Int,
    val url: String
)

class StaggeredAdapter :
    ListAdapter<Item, StaggedViewHolder>(object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.height == newItem.height
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaggedViewHolder {
        return StaggedViewHolder(
            ListStaggedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StaggedViewHolder, position: Int) {
        with(holder) {
//            binding.image.updateLayoutParams<ViewGroup.LayoutParams> {
//                height = getItem(position).height
//            }

            binding.tv.text = getItem(position).height.toString()

//            Glide.with(itemView.context)
//                .asBitmap()
//                .load(getItem(position).url)
//                .into(binding.image)

            Glide.with(itemView.context)
                .asBitmap()
                .load(getItem(position).url)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                    ) {
//                        val width = resource.width
//                        val height = resource.height
//                        val ratio = height / width
//                        Timber.e("width: $width :: height: $height")
                        binding.image.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // 필요시 구현
                    }
                })
        }
    }
}

class StaggedViewHolder(val binding: ListStaggedItemBinding) : ViewHolder(binding.root)

class ImageModel {
    var width: Int = 0
    var height: Int = 0
    var ratio: Float = 0f
    var url: String? = null
}

class DynamicHeightImageView : AppCompatImageView {
    private var whRatio = 0f

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context) : super(context)

    fun setRatio(ratio: Float) {
        whRatio = ratio
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (whRatio != 0f) {
            val width = measuredWidth
            val height = (whRatio * width).toInt()
            Timber.e("width: $width :: height: $height")
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        }
    }
}