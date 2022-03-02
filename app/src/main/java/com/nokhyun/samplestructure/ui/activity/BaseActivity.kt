package com.nokhyun.samplestructure.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Created by Nokhyun90 on 2022-02
 * */
abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    private var _binding: V? = null
    protected val binding: V
        get() = _binding!!

    private lateinit var coroutineContext: CompletableJob

    abstract fun init()
    abstract fun setView(view: (layoutId: Int) -> Unit)
    abstract fun navigator()
    abstract suspend fun coroutineInit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView { layoutId ->
            Log.e(this.javaClass.simpleName, "setView")
            _binding = DataBindingUtil.setContentView(this, layoutId)
        }
        setCoroutine()
        init()
        navigator()
    }

    private fun setCoroutine(){
        coroutineContext = SupervisorJob()
        CoroutineScope(coroutineContext).launch {
            coroutineInit()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}