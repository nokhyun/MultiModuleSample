package com.nokhyun.samplestructure.ui.fragment

import android.os.Bundle
import android.view.View
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentRegisterBinding

class RegisterFragment: BaseFragment<FragmentRegisterBinding>() {
    override fun init() {
    }

    override fun navigator() {
    }

    override fun setView(view: (layoutId: Int) -> View): View = view(R.layout.fragment_register)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}