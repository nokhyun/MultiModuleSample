package com.nokhyun.samplestructure.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Created by Nokhyun90 on 2021.02.24
 * */
abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    private lateinit var _binding: V
    val binding: V
        get() = _binding

    abstract fun init()
    abstract fun navigator()

    abstract fun setView(view: (layoutId: Int) -> View): View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // view 에 관련된 부분만 처리
        return setView { layoutId ->
            _binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, container, false)
            _binding.lifecycleOwner = viewLifecycleOwner
            _binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init
        init()
        navigator()
    }


}