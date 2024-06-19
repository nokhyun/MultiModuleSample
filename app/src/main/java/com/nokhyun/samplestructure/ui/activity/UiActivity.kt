package com.nokhyun.samplestructure.ui.activity

//import com.nokhyun.samplestructure.ui.common.IndentLeadingMarginSpan
import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.os.Bundle
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.StaticLayout
import android.text.TextPaint
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.doOnLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
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
        rv()
    }

    private fun rv() {
//        val wordString = "ability, able, about, above, accept, according, account, across, act, action, activity, actually, add, address, administration, admit, adult, affect, after, again, against, age, agency, agent, ago, agree, agreement, ahead, air, all, allow, almost, alone, along, already, also, although, always, American, among, amount, analysis, and, animal, another, answer, any, anyone, anything, appear, apply, approach, area, argue, arm, around, arrive, art, article, artist, as, ask, assume, at, attack, attention, attorney, audience, author, authority, available, avoid, away".split(",")
//
//        val list: List<FlexBoxModel> = mutableListOf<FlexBoxModel>().apply {
//            wordString.forEach {
//                add(FlexBoxModel(it))
//            }
//        }
//
//        binding.rvFlex.apply {
//            adapter = FlexBoxTestAdapter().apply { submitList(list) }
//            layoutManager = object : FlexboxLayoutManager(this@UiActivity) {
//                override fun getWidth(): Int {
//                    val minusValue = dpToPx(this@UiActivity, 24)
//                    return 1080 - minusValue.times(2)
//                }
//
//                override fun getLeftDecorationWidth(child: View): Int {
//                    return dpToPx(this@UiActivity, 3)
//                }
//
//                override fun getRightDecorationWidth(child: View): Int {
//                    return dpToPx(this@UiActivity, 3)
//                }
//
////                override fun getLargestMainSize(): Int {
////                    return 1
////                }
////
////                override fun getSumOfCrossSize(): Int {
////                    val r = super.getSumOfCrossSize()
////                    Timber.e("getSumOfCrossSize: $r")
////                    return 300
////                }
////
////                override fun getMaxLine(): Int {
////                    return super.getMaxLine()
////                }
////
////                override fun getDecorationLengthCrossAxis(view: View?): Int {
////                    return dpToPx(this@UiActivity, 4)
////                }
//////
////                override fun getDecorationLengthMainAxis(view: View?, index: Int, indexInFlexLine: Int): Int {
////                    return dpToPx(this@UiActivity, 24)
////                }
//
////                override fun getDecoratedLeft(child: View): Int {
////                    return 10
////                }
////
////                override fun getDecoratedRight(child: View): Int {
////                    return 10
////                }
//
//                override fun setFlexLines(flexLines: MutableList<FlexLine>?) {
//                    Timber.e("flexLines: $flexLines")
//                    super.setFlexLines(flexLines)
//                }
//
//            }.apply {
//                justifyContent = JustifyContent.FLEX_START
//                flexWrap = FlexWrap.WRAP
//            }
//        }
    }

    private fun widthCompare() {
        val text = "CheckBox A1"
        val paintWidth = TextPaint().apply {
            textSize = 12.dpToPx().toFloat()
            // 폰트 필요 시 사용
//            typeface = ResourcesCompat.getFont(this@UiActivity, com.google.android.exoplayer2.ui.R.font.roboto_medium_numbers)
        }.measureText(text).toInt()
        // 컴포넌트 width 랑 비교할 땐 어느정도 보정 값 필요. (padding or margin 값 등 변수가 있을 수 있음)
        binding.checkbox1.doOnLayout {
            Timber.e("paintWidth: ${paintWidth + 26.dpToPx()} ::" +
                    " componentWidth: ${it.apply { measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED) }.measuredWidth} ::" +
                    " componentTextWidth: ${TextPaint().apply { textSize = 12.dpToPx().toFloat() }.measureText((it as TextView).text.toString())}"
            )
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

    private fun Int.dpToPx(): Int = this * Resources.getSystem().displayMetrics.density.toInt()

//    private fun dpToPx(context: Context, dp: Int): Int {
//        return dpToPx(context, dp.toFloat()).toInt()
//    }

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