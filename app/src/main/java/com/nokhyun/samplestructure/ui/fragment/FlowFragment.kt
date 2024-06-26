package com.nokhyun.samplestructure.ui.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.nokhyun.samplestructure.BR
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentFlowBinding
import com.nokhyun.samplestructure.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class FlowFragment : BaseFragment<FragmentFlowBinding>() {

    private val flowViewModel: FlowViewModel by viewModels()

    override fun init() {
        binding.setVariable(BR.viewModel, flowViewModel)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    flowViewModel.defaultFlow
                        .map {
                            delay(1000L)
                            it
                        }
                        .collect {
//                        Timber.e("defaultFlow1: $it")
                        }
                }

                launch {
                    flowViewModel.defaultFlow
                        .map {
                            delay(1000L)
                            it
                        }
                        .collect {
//                        Timber.e("defaultFlow2: $it")
                        }
                }

                launch {
                    flowViewModel.callbackFlowFrom().collect {
                        Timber.e(it)
                    }
                }

                launch {
                    flowViewModel.channelFlowFrom().collect {
                        Timber.e(it)
                    }
                }

                launch {
                    with(binding.tvSample) {
                        flowViewModel.textViewState
                            .filterNotNull()
                            .collect { state ->
                                when (val value = state) {
                                    is TextViewState.TextView -> text = value.text
                                    is TextViewState.Visible -> visibility = value.visibilityType.visibility
                                }
                            }
                    }
                }
            }
        }
    }

    override fun navigator() {

    }

    override fun setView(view: (layoutId: Int) -> View): View = view(R.layout.fragment_flow)
}

class FlowViewModel : BaseViewModel() {
    private val _textViewState: MutableStateFlow<TextViewState?> = MutableStateFlow(null)
    val textViewState: StateFlow<TextViewState?> = _textViewState.asStateFlow()

    init {
        viewModelScope.launch {
            _textViewState.value = TextViewState.TextView("Hello World")
            delay(1500L)
            _textViewState.value = TextViewState.Visible(VisibilityType.VISIBLE)
        }
    }

    val defaultFlow = mutableListOf<Int>().apply {
        for (i in 1 until 10) {
            add(i)
        }
    }.asFlow()
        .onStart { delay(1000L) }

    private val _flowBufferValue: MutableStateFlow<Int> = MutableStateFlow(0)
    val flowBufferValue: StateFlow<Int> = _flowBufferValue.asStateFlow()

    private val _sharedFlowValue: MutableSharedFlow<FlowState> = MutableSharedFlow(1, 1, BufferOverflow.DROP_OLDEST)
    val sharedFlowValue: SharedFlow<FlowState> = _sharedFlowValue.asSharedFlow()
        .onSubscription {
            Timber.e("onSubscription")
        }

    private val p1Flow: Flow<Boolean> = flowOf(true)
    private val p2Flow: Flow<Boolean> = flowOf(true)
    private val p3Flow: Flow<Boolean> = flowOf(true)
    private val p4Flow: Flow<Boolean> = flowOf(true)
    private val p5Flow: Flow<Boolean> = flowOf(true)
    private val p6Flow: Flow<Boolean> = flowOf(true)
    private val p7Flow: Flow<Boolean> = flowOf(true)
    private val p8Flow: Flow<Boolean> = flowOf(true)
    private val p123Flow = combine(p1Flow, p2Flow, p3Flow) { p1, p2, p3 ->
        p1 && p2 && p3
    }

    private val flowArray = arrayOf(p1Flow, p2Flow, p3Flow, p4Flow, p5Flow, p6Flow, p7Flow)

    val result1Flow: StateFlow<String> = combine(*flowArray) {
        it.all { it }.toString()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")


    val resultFlow: StateFlow<String> = combine(p123Flow, p4Flow) { p123, p4 ->
        (p123 && p4).toString()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    init {
        viewModelScope.launch {
            launch {
                (1..1000).asFlow()
                    .map {
                        delay(50)
                        it
                    }.buffer(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
                    .onStart { _flowBufferValue.value = 0 }
                    .onEach {
                        delay(1500)
                        _flowBufferValue.value = it
                    }.launchIn(this)
            }

            launch {
                sharedFlowValue
                    .collectLatest {
                        Timber.e("collectLatest11: $it")

                        if (it is FlowState.Success) {
                            it.onSuccess()
                        }
                    }
            }
        }
    }

    fun callbackFlowFrom() = callbackFlow<String> {
        launch(Dispatchers.Default) {
            defaultFlow.collect {
                trySendBlocking(it.toString().plus(":: ONE"))
            }
        }

        launch(Dispatchers.IO) {
            defaultFlow.collect {
                trySendBlocking(it.toString().plus(":: TWO"))
                    .onFailure { exception ->
                        throw exception!!
                    }
            }
        }


        /*
        * callbackFlow 필수
        *  java.lang.IllegalStateException: 'awaitClose { yourCallbackOrListener.cancel() }' should be used in the end of callbackFlow block.
        * */
        awaitClose {
            Timber.e("awaitClose callbackFlow")
        }
    }


    /*
    * flow 사용 시 ThreadSafe 가 아니라 런타임 시 오류 발생
    * */
    fun channelFlowFrom() = channelFlow<String> {
        launch(Dispatchers.Default) {
            trySendBlocking("Channel Hello")
        }

        trySendBlocking("Channel Hi")

        // 필수 아님
        awaitClose {
            Timber.e("awaitClose channelFlowFrom")
        }
    }

    /** CHANGE_CLICK Button */
    fun click() {
        getSharedFlowValue { state ->
            if (state != null && state is FlowState.Success)
                Timber.e("result: $state")
            _sharedFlowValue.tryEmit(FlowState.None)

            _textViewState.value = textViewState.value?.state<TextViewState.Visible>()?.copy(visibilityType = VisibilityType.GONE)
        }
    }

    fun changeFlowState() {
        viewModelScope.launch {
            _sharedFlowValue.tryEmit(FlowState.Success("내용...") {
                Timber.e("function call")
            })
        }
    }

    private fun getSharedFlowValue(value: (FlowState?) -> Unit) {
        viewModelScope.launch {
            value(sharedFlowValue.firstOrNull())
        }
    }
}

/**
 * collect, collectLatest 체크용
 * */
class FlowExam {
    private val _sharedFlow: MutableSharedFlow<Int> = MutableSharedFlow(1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    private suspend fun valueEmit(value: Int) {
        _sharedFlow.emit(value)
    }

    suspend fun start() {
        repeat(100) {
            valueEmit(it)
        }
    }

    fun notSuspendFunctionStart() {
        CoroutineScope(Dispatchers.Default).launch {
            repeat(100) {
                _sharedFlow.emit(it)
            }
        }
    }
}

/**
 * sealed interface 를 이용한 State 관리 샘플.
 * */
sealed interface TextViewState {
    data class TextView(val text: String) : TextViewState
    data class Visible(val visibilityType: VisibilityType) : TextViewState
}

inline fun <reified R> TextViewState.state(): R? =
    if (this is R) this else null

enum class VisibilityType {
    VISIBLE, INVISIBLE, GONE;

    val visibility: Int
        get() = when (this) {
            VISIBLE -> 0x00000000
            INVISIBLE -> 0x00000004
            GONE -> 0x00000008
        }
}

sealed interface FlowState {
    data object None : FlowState

    data class Success(
        val message: String,
        val onSuccess: () -> Unit,
    ) : FlowState
}