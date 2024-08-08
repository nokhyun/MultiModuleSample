package com.nokhyun.samplestructure.ui.fragment

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentSkeletonBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SkeletonFragment : BaseFragment<FragmentSkeletonBinding>() {
    override fun init() {
        lifecycleScope.launch {
            delay(3000)
            binding.skeleton.isVisible = false
        }
    }

    override fun navigator() {
    }

    override fun setView(view: (layoutId: Int) -> View): View = view(R.layout.fragment_skeleton)
}