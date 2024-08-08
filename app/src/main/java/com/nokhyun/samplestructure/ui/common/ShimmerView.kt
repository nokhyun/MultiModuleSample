package com.nokhyun.samplestructure.ui.common

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

/**
 * Created by Nokhyun90 on 2024.08.08
 * */
class ShimmerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private var shader: LinearGradient? = null
    private var animator: ObjectAnimator? = null
    private var translateX: Float = 0f
    private val colorInts =
        intArrayOf(
            Color.parseColor("#E0E0E0"),
            Color.parseColor("#FFFFFF"),
            Color.parseColor("#E0E0E0"),
        )

    @SuppressLint("AnimatorKeep")
    private fun initShimmer() {
        shader = LinearGradient(
            0f, 0f, width.toFloat().div(2), 0f, // 수평 방향으로 그라데이션 적용
            colorInts,
            floatArrayOf(0f, 0.5f, 1f),
            Shader.TileMode.CLAMP
        )
        paint.shader = shader

        animator =
            ObjectAnimator.ofFloat(this, "translateX", -width.toFloat(), width.toFloat()).apply {
                duration = 1200
                repeatCount = ValueAnimator.INFINITE
                addUpdateListener {
                    invalidate()
                }
                start()
            }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0) {
            initShimmer()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        shader?.let {
            val matrix = Matrix()
            matrix.setTranslate(translateX, 0f)
            it.setLocalMatrix(matrix)
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        }
    }

    fun setTranslateX(value: Float) {
        translateX = value
        invalidate()
    }

    fun stop() {
        animator?.cancel()
    }
}