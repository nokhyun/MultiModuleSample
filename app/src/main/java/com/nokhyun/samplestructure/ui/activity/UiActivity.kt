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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nokhyun.samplestructure.BR
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.ActivityUiBinding
import com.nokhyun.samplestructure.ui.activity.adapter.Item
import com.nokhyun.samplestructure.ui.activity.adapter.StaggeredAdapter
import com.nokhyun.samplestructure.viewmodel.UIViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.random.Random

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

    private val staggeredAdapter by lazy { StaggeredAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivityUiBinding?>(this, R.layout.activity_ui).apply {
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

        binding.tvMargin.text =
            SpannableStringBuilder(resources.getString(R.string.activity_ui_text)).apply {
//            setSpan(IndentLeadingMarginSpan(), 0, length, 0)
            }

//        Timber.e("uiViewModel.getFood: ${uiViewModel.getFood}")
        widthCompare()
        rv()
    }

    val items: List<Item>
        get() {
            // 1부터 1000 사이의 랜덤 값 25개 생성
            val height = MutableList(70) { Random.nextInt(200, 1001) }
            val images = listOf(
                "https://cdn.pixabay.com/photo/2013/09/03/19/18/tennis-178696_1280.jpg",
                "https://cdn.pixabay.com/photo/2018/07/28/14/30/soccer-3568168_1280.jpg",
                "https://cdn.pixabay.com/photo/2016/10/16/07/29/the-eiffel-tower-1744574_1280.jpg",
                "https://cdn.pixabay.com/photo/2017/08/06/00/02/court-2586882_1280.jpg",
                "https://cdn.pixabay.com/photo/2024/07/07/07/30/bird-8878566_1280.jpg",
                "https://cdn.pixabay.com/photo/2022/02/11/23/09/san-francisco-7008147_1280.jpg",
                "https://cdn.pixabay.com/photo/2013/09/03/19/18/tennis-178696_1280.jpg",
                "https://cdn.pixabay.com/photo/2018/07/28/14/30/soccer-3568168_1280.jpg",
                "https://cdn.pixabay.com/photo/2016/10/16/07/29/the-eiffel-tower-1744574_1280.jpg",
                "https://cdn.pixabay.com/photo/2017/08/06/00/02/court-2586882_1280.jpg",
                "https://cdn.pixabay.com/photo/2024/07/07/07/30/bird-8878566_1280.jpg",
                "https://cdn.pixabay.com/photo/2022/02/11/23/09/san-francisco-7008147_1280.jpg",
                "https://cdn.pixabay.com/photo/2013/09/03/19/18/tennis-178696_1280.jpg",
                "https://cdn.pixabay.com/photo/2018/07/28/14/30/soccer-3568168_1280.jpg",
                "https://cdn.pixabay.com/photo/2016/10/16/07/29/the-eiffel-tower-1744574_1280.jpg",
                "https://cdn.pixabay.com/photo/2017/08/06/00/02/court-2586882_1280.jpg",
                "https://cdn.pixabay.com/photo/2024/07/07/07/30/bird-8878566_1280.jpg",
                "https://cdn.pixabay.com/photo/2022/02/11/23/09/san-francisco-7008147_1280.jpg",
                "https://cdn.pixabay.com/photo/2013/09/03/19/18/tennis-178696_1280.jpg",
                "https://cdn.pixabay.com/photo/2018/07/28/14/30/soccer-3568168_1280.jpg",
                "https://cdn.pixabay.com/photo/2016/10/16/07/29/the-eiffel-tower-1744574_1280.jpg",
                "https://cdn.pixabay.com/photo/2017/08/06/00/02/court-2586882_1280.jpg",
                "https://cdn.pixabay.com/photo/2024/07/07/07/30/bird-8878566_1280.jpg",
                "https://cdn.pixabay.com/photo/2022/02/11/23/09/san-francisco-7008147_1280.jpg",
                "https://cdn.pixabay.com/photo/2013/09/03/19/18/tennis-178696_1280.jpg",
                "https://cdn.pixabay.com/photo/2018/07/28/14/30/soccer-3568168_1280.jpg",
                "https://cdn.pixabay.com/photo/2016/10/16/07/29/the-eiffel-tower-1744574_1280.jpg",
                "https://cdn.pixabay.com/photo/2017/08/06/00/02/court-2586882_1280.jpg",
                "https://cdn.pixabay.com/photo/2024/07/07/07/30/bird-8878566_1280.jpg",
                "https://cdn.pixabay.com/photo/2022/02/11/23/09/san-francisco-7008147_1280.jpg",
                "https://cdn.pixabay.com/photo/2024/04/21/14/13/pelican-8710717_640.jpg",
                "https://cdn.pixabay.com/photo/2018/07/28/14/30/soccer-3568168_1280.jpg",
                "https://cdn.pixabay.com/photo/2016/10/16/07/29/the-eiffel-tower-1744574_1280.jpg",
                "https://cdn.pixabay.com/photo/2017/08/06/00/02/court-2586882_1280.jpg",
                "https://cdn.pixabay.com/photo/2024/07/07/07/30/bird-8878566_1280.jpg",
                "https://cdn.pixabay.com/photo/2022/02/11/23/09/san-francisco-7008147_1280.jpg",
                "https://cdn.pixabay.com/photo/2013/09/03/19/18/tennis-178696_1280.jpg",
                "https://cdn.pixabay.com/photo/2024/04/21/14/13/pelican-8710717_640.jpg",
                "https://cdn.pixabay.com/photo/2016/10/16/07/29/the-eiffel-tower-1744574_1280.jpg",
                "https://cdn.pixabay.com/photo/2017/08/06/00/02/court-2586882_1280.jpg",
                "https://cdn.pixabay.com/photo/2024/07/07/07/30/bird-8878566_1280.jpg",
                "https://cdn.pixabay.com/photo/2022/02/11/23/09/san-francisco-7008147_1280.jpg",
                "https://cdn.pixabay.com/photo/2013/09/03/19/18/tennis-178696_1280.jpg",
                "https://cdn.pixabay.com/photo/2018/07/28/14/30/soccer-3568168_1280.jpg",
                "https://cdn.pixabay.com/photo/2016/10/16/07/29/the-eiffel-tower-1744574_1280.jpg",
                "https://cdn.pixabay.com/photo/2017/08/06/00/02/court-2586882_1280.jpg",
                "https://cdn.pixabay.com/photo/2024/07/07/07/30/bird-8878566_1280.jpg",
                "https://cdn.pixabay.com/photo/2022/02/11/23/09/san-francisco-7008147_1280.jpg",
                "https://cdn.pixabay.com/photo/2013/09/03/19/18/tennis-178696_1280.jpg",
                "https://cdn.pixabay.com/photo/2018/07/28/14/30/soccer-3568168_1280.jpg",
                "https://cdn.pixabay.com/photo/2016/10/16/07/29/the-eiffel-tower-1744574_1280.jpg",
                "https://cdn.pixabay.com/photo/2017/08/06/00/02/court-2586882_1280.jpg",
                "https://cdn.pixabay.com/photo/2017/07/31/17/12/water-2559064_640.jpg",
                "https://cdn.pixabay.com/photo/2022/02/11/23/09/san-francisco-7008147_1280.jpg",
                "https://cdn.pixabay.com/photo/2013/09/03/19/18/tennis-178696_1280.jpg",
                "https://cdn.pixabay.com/photo/2018/07/28/14/30/soccer-3568168_1280.jpg",
                "https://cdn.pixabay.com/photo/2016/10/16/07/29/the-eiffel-tower-1744574_1280.jpg",
                "https://cdn.pixabay.com/photo/2017/08/06/00/02/court-2586882_1280.jpg",
                "https://cdn.pixabay.com/photo/2024/07/07/07/30/bird-8878566_1280.jpg",
                "https://cdn.pixabay.com/photo/2022/02/11/23/09/san-francisco-7008147_1280.jpg",
                "https://cdn.pixabay.com/photo/2013/09/03/19/18/tennis-178696_1280.jpg",
                "https://cdn.pixabay.com/photo/2018/07/28/14/30/soccer-3568168_1280.jpg",
                "https://cdn.pixabay.com/photo/2016/10/16/07/29/the-eiffel-tower-1744574_1280.jpg",
                "https://cdn.pixabay.com/photo/2017/07/31/17/12/water-2559064_640.jpg",
                "https://cdn.pixabay.com/photo/2024/07/07/07/30/bird-8878566_1280.jpg",
                "https://cdn.pixabay.com/photo/2022/02/11/23/09/san-francisco-7008147_1280.jpg",
                "https://cdn.pixabay.com/photo/2013/09/03/19/18/tennis-178696_1280.jpg",
                "https://cdn.pixabay.com/photo/2018/07/28/14/30/soccer-3568168_1280.jpg",
                "https://cdn.pixabay.com/photo/2016/10/16/07/29/the-eiffel-tower-1744574_1280.jpg",
                "https://cdn.pixabay.com/photo/2017/07/31/17/12/water-2559064_640.jpg",
                "https://cdn.pixabay.com/photo/2024/07/07/07/30/bird-8878566_1280.jpg",
                "https://cdn.pixabay.com/photo/2022/02/11/23/09/san-francisco-7008147_1280.jpg",
                "https://cdn.pixabay.com/photo/2013/09/03/19/18/tennis-178696_1280.jpg",
                "https://cdn.pixabay.com/photo/2018/07/28/14/30/soccer-3568168_1280.jpg",
                "https://cdn.pixabay.com/photo/2016/10/16/07/29/the-eiffel-tower-1744574_1280.jpg",
                "https://cdn.pixabay.com/photo/2017/08/06/00/02/court-2586882_1280.jpg",
            ).shuffled()
//        val height = mutableListOf(628, 968, 538, 983, 630, 218, 977, 317, 949, 892, 241, 232, 437, 715, 810, 995, 847, 453, 743, 523, 794, 880, 765, 905, 576)
//        val height = mutableListOf(628, 968, 538, 983, 630, 218, 977, 317)
//            val height = mutableListOf(648, 444, 358, 699, 997, 543, 706, 871, 680, 718, 931, 286, 510, 769, 471, 629, 461, 617, 461, 567, 231, 568, 399, 347, 817)

            binding.tvList.text = height.toString()
            println("Original list: $height")
            val totalSum = height.sum()
            // 모든 짝수 인덱스에 대해 검사 및 교환
//        for (i in 2 until height.size step 2) {
//            // 현재 짝수 인덱스 값을 제외한 나머지 값들의 합
//            val remainingSum = totalSum - height[i]
//
//            if (height[i] > remainingSum) {
//                // i번 인덱스와 1번 인덱스의 값을 서로 교환
//                height[i] = height[1].also { height[1] = height[i] }
//            }
//        }
//
//        for (i in 0 until height.size - 1 step 2) {
//            // i 인덱스와 i+1 인덱스의 값을 비교하여 i가 더 크면 교환
//            if (height[i] > height[i + 1]) {
//                // i번 인덱스와 i+1번 인덱스의 값을 서로 교환
//                height[i] = height[i + 1].also { height[i + 1] = height[i] }
//            }
//        }

//        for (i in 0 until height.size - 1) {
//            // 짝수 인덱스(i)와 그 다음 홀수 인덱스(i+1)의 값을 비교하여 값이 크면 위치를 교환
//            if (i % 2 == 0 && height[i] > height[i + 1]) {
//                // 짝수 인덱스(i)와 그 다음 홀수 인덱스(i+1)의 값을 교환
//                val temp = height[i]
//                height[i] = height[i + 1]
//                height[i + 1] = temp
//            }
//        }

//            var i = 0 // 시작 인덱스
//
//            while (i < height.size - 1) {
//                // 현재 인덱스(i)와 그 다음 홀수 인덱스(i+1)의 값을 비교하여 값이 크면 위치를 교환
//                if (i % 2 == 0 && height[i] > height[i + 1]) {
//                    // 현재 인덱스(i)와 그 다음 홀수 인덱스(i+1)의 값을 교환
//                    val temp = height[i]
//                    height[i] = height[i + 1]
//                    height[i + 1] = temp
//
//                    // 스위칭 후 다음 값부터 다시 비교하기 위해 인덱스 증가
//                    i += 2
//                } else {
//                    // 다음 값부터 다시 비교하기 위해 인덱스 증가
//                    i++
//                }
//            }

            println("result list: $height")
            return height.zip(images) { height, path ->
                Item(
                    height = height,
                    url = path
                )
            }
        }

    private fun rv() {
        // staggered
        binding.rvFlex.apply {
            setHasFixedSize(true)
            adapter = staggeredAdapter.apply {
                submitList(items)
            }
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
//            layoutManager = object : StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL) {
//                override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
//                    // 호출할 때마다 아이템들의 위치를 다시 설정
//                    removeAllViews()
//
//                    // 현재 화면에 보여지는 아이템들의 개수
//                    val itemCount = itemCount
//                    val columnCount = spanCount
//
//                    // 홀수 인덱스는 왼쪽, 짝수 인덱스는 오른쪽에 배치
//                    var leftColumn = 0
//                    var rightColumn = 1
//
//                    for (i in 0 until itemCount) {
//                        // 아이템의 높이 가져오기
//                        val height = recycler?.getViewForPosition(i)?.height ?: 0
//
//                        // 높이가 홀수 인덱스보다 클 경우, 오른쪽에 배치
//                        if (height > getColumnHeight(leftColumn) + getColumnHeight(rightColumn)) {
//                            layoutDecoratedWithMargins(
//                                getChildAt(i)!!, width / 2, getColumnHeight(leftColumn),
//                                width, getColumnHeight(leftColumn) + height
//                            )
//                            rightColumn += 2
//                        } else {
//                            // 그렇지 않으면 왼쪽에 배치
//                            if(children.count() > 0) {
//                                layoutDecoratedWithMargins(
//                                    getChildAt(i)!!, 0, getColumnHeight(rightColumn),
//                                    width / 2, getColumnHeight(rightColumn) + height
//                                )
//                                leftColumn += 2
//                            }
//                        }
//                    }
//                }
//
//                private fun getColumnHeight(column: Int): Int {
//                    var columnHeight = 0
//                    for (i in 0 until childCount) {
//                        val view = getChildAt(i)
//                        columnHeight += view?.height?.plus((view.layoutParams as RecyclerView.LayoutParams).topMargin) ?: 0
//                    }
//                    return columnHeight
//                }
//
//            }
            itemAnimator = null

//            addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                    super.onScrollStateChanged(recyclerView, newState)
//                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                        recyclerView.invalidateItemDecorations()
//                    }
//                }})
        }

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
            Timber.e(
                "paintWidth: ${paintWidth + 26.dpToPx()} ::" +
                        " componentWidth: ${
                            it.apply {
                                measure(
                                    View.MeasureSpec.UNSPECIFIED,
                                    View.MeasureSpec.UNSPECIFIED
                                )
                            }.measuredWidth
                        } ::" +
                        " componentTextWidth: ${
                            TextPaint().apply {
                                textSize = 12.dpToPx().toFloat()
                            }.measureText((it as TextView).text.toString())
                        }"
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
        val staticLayout = StaticLayout.Builder.obtain(
            text,
            0,
            text.length,
            paint,
            paint.measureText(text).toInt()
        )
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