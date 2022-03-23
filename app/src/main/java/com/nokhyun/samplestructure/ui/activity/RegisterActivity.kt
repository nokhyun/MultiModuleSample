package com.nokhyun.samplestructure.ui.activity

import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.ActivityRegisterBinding
import com.nokhyun.samplestructure.ui.fragment.RegisterFragment

/**
 * Created by ChoKwangJun on 2022-03-23
 * */
class RegisterActivity: BaseActivity<ActivityRegisterBinding>() {
    override fun init() {
        showFragment<Any>(binding.flRegisterContainer.id, RegisterFragment())
    }

    override fun setView(view: (layoutId: Int) -> Unit) {
        view(R.layout.activity_register)
    }

    override fun navigator() {
    }

    override suspend fun coroutineInit() {
    }

    override fun onBackPressed() {
        when{
            supportFragmentManager.backStackEntryCount > 1 -> {
                supportFragmentManager.popBackStack()
            }
            else -> finish()
        }
    }
}