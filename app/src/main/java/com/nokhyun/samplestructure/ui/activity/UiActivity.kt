package com.nokhyun.samplestructure.ui.activity

//import com.nokhyun.samplestructure.ui.common.IndentLeadingMarginSpan
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.StaticLayout
import android.text.TextPaint
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.google.android.material.chip.Chip
import com.nokhyun.samplestructure.BR
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.ActivityUiBinding
import com.nokhyun.samplestructure.viewmodel.UIViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UiActivity : AppCompatActivity() {

    private val uiViewModel: UIViewModel by viewModels()

    private lateinit var binding: ActivityUiBinding

    private val ids by lazy {
        listOf(
            binding.layoutCustomRadio.radioButton1.id,
            binding.layoutCustomRadio.radioButton2.id,
            binding.layoutCustomRadio.radioButton3.id
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityUiBinding?>(this, R.layout.activity_ui).apply {
            setVariable(BR.view, uiViewModel)
        }
        binding.layoutCustomRadio.radioGroup.children.zip(ids.asSequence()) { view, id ->
            (view as RadioButton).id = ids[id]
        }.also {
            binding.layoutCustomRadio.radioGroup.check(binding.layoutCustomRadio.radioButton1.id)
        }

        binding.layoutCustomRadio.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            Timber.e("text: ${findViewById<RadioButton>(checkedId).text} :: checkedId: $checkedId")
        }

        binding.tvMargin.text = SpannableStringBuilder(resources.getString(R.string.activity_ui_text)).apply {
//            setSpan(IndentLeadingMarginSpan(), 0, length, 0)
        }

//        Timber.e("uiViewModel.getFood: ${uiViewModel.getFood}")
        widthCompare()
    }

    private fun widthCompare() {
        Timber.e("widthCompare")
//        val txt = "헬로우"
        val txt = "안녕"
        val textSize = 16f
        val typeface = resources.getFont(com.google.android.exoplayer2.ui.R.font.roboto_medium_numbers)

// Chip 생성 및 설정
        val chip = Chip(this).apply {
            this.textSize = textSize
            this.typeface = typeface
            this.text = txt
            // 필요에 따라 추가적인 간격 설정
            // 예: setPadding(16, 16, 16, 16) // 패딩 값 설정
        }

// TextPaint를 사용하여 폰트 및 텍스트 크기 설정
        val paint = TextPaint().apply {
            this.textSize = textSize
            this.typeface = typeface
        }

// 각 문자의 너비를 측정하여 전체 텍스트의 폭 계산
        val textWidth = measureTextWidth(paint, txt)

        val dummyViewGroup = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }

// Chip을 LinearLayout에 추가
        dummyViewGroup.addView(chip)

// LinearLayout을 루트 뷰에 추가
        (binding.root as ViewGroup).addView(dummyViewGroup)

// Chip의 너비를 측정하여 로그로 출력
        chip.post {
            val chipWidth = chip.width
            Timber.e("chipWidth: $chipWidth :: measureTextWidth: ${paint.measureText(txt)}")

            // 오차 계산
            val error = Math.abs(chipWidth - textWidth)
            Timber.e("Error: $error")

            Timber.e("after chipWidth: $chipWidth :: measureTextWidth: ${paint.measureText(txt) + error}")
        }

    }

    private fun measureTextWidth(paint: Paint, text: String): Float {
        var width = 0f
        val widths = FloatArray(text.length)
        paint.getTextWidths(text, widths)
        for (w in widths) {
            width += w
        }
        return width
    }

    private fun dpToPx(context: Context, dp: Float): Float {
        val density = context.resources.displayMetrics.density
        return dp * density
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        return dpToPx(context, dp.toFloat()).toInt()
    }

    private fun pxToDp(context: Context, px: Float): Float {
        val density = context.resources.displayMetrics.density
        return px / density
    }

    private fun pxToDp(context: Context, px: Int): Int {
        return pxToDp(context, px.toFloat()).toInt()
    }

    private fun calculateTextWidth(text: String, paint: TextPaint): Int {
        val staticLayout = StaticLayout.Builder.obtain(text, 0, text.length, paint, paint.measureText(text).toInt())
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(1.0f, 1.0f)
            .setIncludePad(false)
            .build()
        return staticLayout.width
    }
}

@BindingAdapter("indentText")
fun TextView.setIndentLeadingMarginSpan(indentText: CharSequence?) {
    indentText ?: return

    this.text = SpannableStringBuilder(indentText).apply {
//        setSpan(IndentLeadingMarginSpan(), 0, length, 0)
    }
}