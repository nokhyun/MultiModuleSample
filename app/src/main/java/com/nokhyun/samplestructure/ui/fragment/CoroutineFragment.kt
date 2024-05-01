package com.nokhyun.samplestructure.ui.fragment

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentCoroutineBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class CoroutineFragment : BaseFragment<FragmentCoroutineBinding>() {
    private var job: Job? = null

    private val galaxy = Galaxy()
    private val apple = Apple()
    private val phones: Flow<PhoneModel> = listOf(
        galaxy,
        apple
    ).asFlow()

    val firstGalaxy: () -> PhoneModel = { galaxy }
    val firstApple: () -> PhoneModel = { apple }

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
                val s1 = suspend { Timber.e("suspend 1") }
                val s2 = suspend { Timber.e("suspend 2") }
                val s3 = suspend {
                    delay(3000L)
                    s1.invoke()
                }
                Timber.e("s3: ${s3()}")
                initGetGalaxy()
                    .collect {
                        Timber.e("first phoneModel: ${it.phoneName() + it.numbering()}")
                    }
            }

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

                        cancellableContinuation.resume(Unit)
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
                    Timber.e("suspendCancellableCoroutine withTimeoutOrNull result: $it")
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

            launch {
                Timber.e("click result: ${click()}")
            }

            launch {
                startOngoingMessage(this)
            }

            launch {
                val collect = FlowCollector<Int> {
                    Timber.e("collect: $it")
                }

                val numbers = listOf(1, 2, 3, 4, 5, 6).asFlow()
                val foldResult = numbers.fold(100) { acc: Int, value: Int -> acc + value }
                Timber.e("foldResult: $foldResult")
                val reduceResult = numbers.reduce { acc, i -> acc + i }
                Timber.e("reduceResult: $reduceResult")

                numbers
                    .scan(1) { accumulator: Int, value: Int ->  // 리스트의 누적 합을 계산하며, 리스트의 사이즈가 5일 경우 사이즈 만큼 collect를 함.
                        Timber.e("accumulator: $accumulator")
                        if (accumulator == 2) throw Throwable("retryWhenTest Exception!!!")
                        value + accumulator
                    }
                    .retryWhen { cause, attempt ->
                        Timber.e("retryWhen attempt: $attempt")
                        delay(1000)
                        if (attempt == 3L) {
                            false
                        } else {
                            true
                        }
                    }
                    .catch {
                        Timber.e("")
                    }
                    .collect(collect)
            }
        }

        binding.btnCancel.setOnClickListener {
            job?.cancel()
        }
    }

    private fun startOngoingMessage(scope: CoroutineScope) {
        job = scope.launch {
            val ongoingMessageCollect = FlowCollector<String> { Timber.e("ongoingMessage receive: $it") }
            ongoingMessage()
                .onStart { Timber.e("ongoingMessage onStart") }
                .onCompletion { Timber.e("ongoingMessage onCompletion") }
                .collect(ongoingMessageCollect)
        }
    }

    private fun ongoingMessage() = callbackFlow {
        repeat(10) {
            delay(1000L)
            send("message #$it")
        }
        awaitClose {
            Timber.e("ongoingMessage flow close!!!")
        }
    }

    /** 1회성 callback 에 사용 */
    suspend fun click(): String = suspendCancellableCoroutine { continuation ->
        binding.btnSuspendCoroutine.setOnClickListener {
            if (!continuation.isCompleted) continuation.resume("Click!!!")
            startOngoingMessage(viewLifecycleOwner.lifecycleScope)
        }

        continuation.invokeOnCancellation {
            continuation.resumeWithException(it ?: Throwable("Click Error"))
        }
    }

    override fun navigator() {
    }

    override fun setView(view: (layoutId: Int) -> View): View {
        return view(R.layout.fragment_coroutine)
    }

    suspend fun initGetGalaxy() = suspendCoroutine { continuation ->
        continuation.resume(firstGalaxy
            .asFlow()
            .flatMapLatest { flowOf(firstApple(), it) }
            .filterIsInstance<Galaxy>()
            .map { it.phoneType }
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}

interface PhoneModel {
    val phoneType: PhoneType
}

class Galaxy : PhoneModel {
    override val phoneType: PhoneType = PhoneType.SAMSUNG
}

class Apple : PhoneModel {
    override val phoneType: PhoneType = PhoneType.APPLE
}


/*
* 기존은 inline 키워드를 사용했으나, deprecated 가 되면서 @JvmInline annotation을 사용하도록 변경되었다.
* 기본 생성자 프로퍼티가 하나인 클래스 앞에 inline 을 추가하면 해당 객체를 사용하는 위치가 모두 해당 프로퍼티로 변경된다.
* inline 클래스의 메소드는 모두 정적 메소드로 만들어진다.
* value class 는 새로운 자료형을 만들떄 사용된다.
* decompile 시 value class -> public final class
* 아래 예시.
* */
@JvmInline
value class Id(val id: Int)

@JvmInline
value class PhoneName(val phoneName: String)

@JvmInline
value class Numbering(val numbering: String)

data class Object(val id: Id)

enum class PhoneType(
    private val id: Id,
    private val phoneName: PhoneName,
    private val numbering: Numbering
) {
    SAMSUNG(Id(0), PhoneName("Galaxy"), Numbering(" s24 Ultra")),
    APPLE(Id(1), PhoneName("iPhone"), Numbering(" 15 pro"));

    fun id() = this.id.id
    fun phoneName() = this.phoneName.phoneName
    fun numbering() = this.numbering.numbering
}