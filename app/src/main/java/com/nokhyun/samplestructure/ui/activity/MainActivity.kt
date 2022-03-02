package com.nokhyun.samplestructure.ui.activity

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.utils.showToastShort
import com.nokhyun.samplestructure.databinding.ActivityMainBinding
import com.nokhyun.samplestructure.viewmodel.BaseViewModel
import com.nokhyun.samplestructure.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ActorScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by Nokhyun90 on 2022-02-11
 * */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val _mainViewModel: MainViewModel by viewModels()

    override fun init() {
        // lifecyclerScope



        var count = 0

//        CoroutineScope(Dispatchers.Main).launch {
        binding.btnTest.singleClick() {
//                log("${System.currentTimeMillis()}:: 눌러!!!: $count")
            log("${System.currentTimeMillis()}:: 눌러!!!")
//                count++


        }

//        binding.test.singleClick(2000) {
//        binding.test.test(CoroutineScope(Dispatchers.Main)) {
        binding.test.test1 {
            log("이게맞나")
        }

//        }
//

        _mainViewModel.getRepoList()
    }

    override fun setView(view: (layoutId: Int) -> Unit) {
        view(R.layout.activity_main)
    }

    fun log(msg: String) {
        Timber.e(msg)
    }

    override suspend fun coroutineInit() {

    }

    override fun navigator() {
        _mainViewModel.baseResultNavigator.observe(this){ result ->
            when(result){
                is BaseViewModel.BaseResult.ToastMsg -> result.message.showToastShort(this)
                is BaseViewModel.BaseResult.ServerError -> result.message.showToastShort(this)
                else -> { }
            }
        }
    }

}

/** kotlin actor 2022 중에 수정 될 수 있다고 함. */
@OptIn(ObsoleteCoroutinesApi::class)
fun View.singleClick(delay: Long = 500, click: (v: View) -> Unit) {
    var clickTime: Long = 0
    val event = CoroutineScope(Dispatchers.Main).actor<View>(Dispatchers.Main) {
        for (event in channel) {
            Timber.e("actor: $event")
            click(event)
        }
    }
    setOnClickListener {
        if (System.currentTimeMillis() > clickTime + delay) {
            clickTime = System.currentTimeMillis()
            event.trySend(it)
        }
    }
}

fun View.test(coroutineScope: CoroutineScope, click: (v: View) -> Unit) {
//    coroutineScope.launch {
    val flow = MutableSharedFlow<View>()

    coroutineScope.launch {
        setOnClickListener {
//            flow.emit(this@test)
        }
    }

    suspend {
        flow.collectLatest {
            click.invoke(it)
        }
    }
//    }

}

fun View.test1(click: (v: View) -> Unit) {
    val c = Channel<View>()

    c.trySend(this)

    setOnClickListener {
        Timber.e("alskdjl")
        suspend {
            click(c.receive())
        }
    }
}

fun <E> ActorScope<E>.delay(clickTime: Long) {

}

suspend fun test() {
    val sharedFlow = MutableSharedFlow<Any>()
    sharedFlow.emit("asd")

}