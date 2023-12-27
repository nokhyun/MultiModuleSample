package com.nokhyun.samplestructure.ui.fragment

import android.view.View
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentCoroutineBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import timber.log.Timber

class CoroutineFragment : BaseFragment<FragmentCoroutineBinding>() {
    override fun init() {
        val ceh = CoroutineExceptionHandler { coroutineContext, throwable -> }
        val supervisorJob = SupervisorJob()
        CoroutineScope(Dispatchers.Default + ceh).launch {
            launch(supervisorJob) {
                Timber.e("child1")
                throw Exception("child1")
            }

            launch {
                delay(1000L)
                Timber.e("child2")
            }

            supervisorScope {
                launch {
                    Timber.e("child3")
                    throw Exception("child3")
                }

                launch {
                    delay(1000L)
                    Timber.e("child4")
                }
            }
        }
    }

    override fun navigator() {
    }

    override fun setView(view: (layoutId: Int) -> View): View {
        return view(R.layout.fragment_coroutine)
    }
}