package com.nokhyun.samplestructure.ui.common

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.core.view.doOnPreDraw
import com.nokhyun.samplestructure.R
import timber.log.Timber

/**
 * Created by Nokhyun90 on 2023.04.05
 * */
class SkeletonLayout : LinearLayout {

    private var deviceHeight: Int? = null

    init {
        orientation = VERTICAL
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        if (context != null && attrs != null) init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        Timber.e("[init] SkeletonLayout")
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkeletonLayout)
        val layoutRes = typedArray.getResourceId(R.styleable.SkeletonLayout_layoutRes, -1)
        val skeletonView = createSkeleton(context, layoutRes)

        addView(skeletonView)

        deviceHeight = getDeviceHeight(context)
        onDrawSkeletonView(context, layoutRes)

        typedArray.recycle()
    }

    private fun onDrawSkeletonView(context: Context, layoutRes: Int) {
        getChildAt(0).doOnPreDraw {
            val divResult = deviceHeight?.div(it.measuredHeight)

            for (i in 0 until (divResult ?: 0)) {
                addView(createSkeleton(context, layoutRes))
            }
        }
    }


    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)

        exploreViewGroup(this).also {
            removeAllViews()
        }
    }

    private fun createSkeleton(context: Context, @LayoutRes layoutRes: Int) =
        LayoutInflater.from(context).inflate(layoutRes, null)

    private fun getDeviceHeight(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            val insets =
                windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.height() - insets.bottom - insets.top
        } else {
            windowManager.defaultDisplay.height
        }
    }

    private fun exploreViewGroup(viewGroup: ViewGroup) {
        val childCount = viewGroup.childCount
        for (i in 0 until childCount) {
            when (val childView = viewGroup.getChildAt(i)) {
                is ViewGroup -> exploreViewGroup(childView)
                is ShimmerView -> childView.stop()
            }
        }
    }
}