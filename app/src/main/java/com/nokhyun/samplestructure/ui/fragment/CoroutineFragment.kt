package com.nokhyun.samplestructure.ui.fragment

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentCoroutineBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CoroutineFragment : BaseFragment<FragmentCoroutineBinding>() {
    override fun init() {
        val ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e("throwable: $throwable")
        }
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

        // suspendCoroutine
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                withTimeoutOrNull(3000) {
                    suspendCoroutine<String> { continuation ->    // 3초 이후에 취소 안됨
                        Timber.e("suspendCoroutine")
                        continuation.resume("진행")
                    }.let {
                        Timber.e("resume: $it")
                    }
                }.let {
                    // 취소가 안되므로 동작하지 않음
                    Timber.e("withTimeoutOrNull result: $it")
                }
            }

            launch {
                withTimeoutOrNull(3000) {
                    suspendCancellableCoroutine<Unit> { cancellableContinuation ->
                        Timber.e("suspendCancellableCoroutine") // 3초 이후에 취소됨


                        /*
                        * 코루틴의 스코프가 취소 될 때 호출
                        * Job 이 취소되지 않는한 호출되지 않으며, 정상적으로 resume 된다면 해당 블록에 정의된 코드는 동작하지 않음.
                        * */
//                        cancellableContinuation.resume(Unit)
                        cancellableContinuation.invokeOnCancellation {
                            /*
                            * Thread-safe 한 함수들만 호출되어야함.
                            * 만약 취소하는 블록을 실행하는 디스패처가 Main 이 아니라면 어떤 스레드에서도 호출될 수 있음
                            * */
                            Timber.e("Cancel!")
                        }
                    }
                }.let {
                    // 취소가 되므로 동작함.
                    Timber.e("withTimeoutOrNull result: $it")
                }
            }

            launch {
                withTimeoutOrNull(3000) {
                    delay(3000)
                    Timber.e("normal withTimeoutOrNull")
                }.let {
                    Timber.e("normal  withTimeoutOrNull result: $it")
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