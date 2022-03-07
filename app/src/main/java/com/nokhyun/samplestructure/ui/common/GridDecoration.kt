package com.nokhyun.samplestructure.ui.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridDecoration: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val idx =  (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
        val position = parent.getChildLayoutPosition(view)

        when(idx){
            0 -> outRect.right = 4 / 2
            else -> outRect.left = 4 / 2
        }

        when{
            position < 2 -> outRect.top = 0
            else -> outRect.top = 4
        }
    }
}