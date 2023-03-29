package com.nokhyun.samplestructure.ui.fragment

import android.view.View
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>() {
    override fun init() {
    }

    override fun navigator() {
    }

    override fun setView(view: (layoutId: Int) -> View): View = view(R.layout.fragment_main)
}