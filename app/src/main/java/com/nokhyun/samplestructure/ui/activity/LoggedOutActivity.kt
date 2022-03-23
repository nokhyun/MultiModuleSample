package com.nokhyun.samplestructure.ui.activity

import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.ActivityLoggedOutBinding

/**
 * Created by ChoKwangJun on 2022-03-23
 * */
class LoggedOutActivity: BaseActivity<ActivityLoggedOutBinding>() {
    override fun init() {
    }

    override fun setView(view: (layoutId: Int) -> Unit) {
        view(R.layout.activity_logged_out)
    }

    override fun navigator() {
    }

    override suspend fun coroutineInit() {
    }
}