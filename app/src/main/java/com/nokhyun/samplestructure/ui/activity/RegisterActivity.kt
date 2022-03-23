package com.nokhyun.samplestructure.ui.activity

import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.ActivityRegisterBinding

/**
 * Created by ChoKwangJun on 2022-03-23
 * */
class RegisterActivity: BaseActivity<ActivityRegisterBinding>() {
    override fun init() {
    }

    override fun setView(view: (layoutId: Int) -> Unit) {
        view(R.layout.activity_register)
    }

    override fun navigator() {
    }

    override suspend fun coroutineInit() {
    }
}