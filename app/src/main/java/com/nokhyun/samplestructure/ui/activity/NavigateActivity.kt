package com.nokhyun.samplestructure.ui.activity

import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.ActivityNavigateBinding

class NavigateActivity : BaseActivity<ActivityNavigateBinding>() {
    override fun init() {
    }

    override fun setView(view: (layoutId: Int) -> Unit) {
        view(R.layout.activity_navigate)
    }

    override fun navigator() {

    }

    override suspend fun coroutineInit() {
    }
}