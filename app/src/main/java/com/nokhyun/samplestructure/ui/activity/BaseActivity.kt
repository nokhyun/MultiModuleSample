package com.nokhyun.samplestructure.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.nokhyun.samplestructure.R
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * Created by Nokhyun90 on 2022-02
 * */
abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    private lateinit var _binding: V
    protected val binding: V
        get() = _binding

    private lateinit var _supervisorJob: Job

    abstract fun init()
    abstract fun setView(view: (layoutId: Int) -> Unit)
    abstract fun navigator()
    abstract suspend fun coroutineInit()            // 이거 필요여부에 대한 생각. 다시 해보자.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView { layoutId ->
            Timber.e("setView")
            _binding = DataBindingUtil.setContentView(this, layoutId)
            _binding.lifecycleOwner = this
        }
        setCoroutine()
        init()
        navigator()
    }

    private fun setCoroutine() {
        _supervisorJob = SupervisorJob()
        CoroutineScope(Dispatchers.Main + _supervisorJob).launch {
            coroutineInit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}