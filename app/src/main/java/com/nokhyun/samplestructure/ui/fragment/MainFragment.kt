package com.nokhyun.samplestructure.ui.fragment

import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding>() {
    override fun init() {
        lifecycleScope.launch {
            launch {
                delay(3000)
                binding.clMain.addView(TextView(requireContext()).apply {
                    text = "아니 무슨!!!!"
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28f)
                })
            }

            launch {
                delay(2000)
                binding.clMain.addView(TextView(requireContext()).apply {
                    text = "아니 무슨일이람"
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 36f)
                })
            }
        }


//        binding.clMain.doOnNextLayout {
//            TransitionManager.beginDelayedTransition(binding.clMain, Fade(Fade.MODE_OUT))
//        }
    }

    override fun navigator() {
    }

    override fun setView(view: (layoutId: Int) -> View): View = view(R.layout.fragment_main)
}