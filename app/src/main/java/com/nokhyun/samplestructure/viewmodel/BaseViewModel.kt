package com.nokhyun.samplestructure.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokhyun.domain.common.NetworkError
import com.nokhyun.samplestructure.utils.SingleLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onClosed
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import timber.log.Timber

/**
 * Created by ChoKwangJun on 2022-02-24
 * */
abstract class BaseViewModel : ViewModel() {

    /*
     * Channel.CONFLATED 최대 하나의 요소를 버퍼링하고 모든 후속 및 호출을 병합. 항상 마지막 요소를 보냄.
     * 에러메세지 보내는데 굳이 여러번 보낼필요 없음. 필요하다면 인자 값 변경.
     * https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-channel/index.html
     * */
    private val _errorHandler = Channel<NetworkError>(Channel.CONFLATED)
    protected val errorHandler = _errorHandler


    /*
    * supervisorJob
    * https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-supervisor-job.html
    * */
    private val _supervisorJob = SupervisorJob()
    protected val supervisorJob = _supervisorJob

    /*
    * CoroutineExceptionHandler
    * https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/index.html
    * */
    private val _coroutineErrorHandler = CoroutineExceptionHandler { ctx, e ->
        Timber.e("CoroutineExceptionHandler: ${e.message}")
        ctx.cancel()
    }
    protected val coroutineErrorHandler = _coroutineErrorHandler

    init {
        viewModelScope.launch {
            _errorHandler.receiveCatching()
                .onSuccess {
                    when (it) {
                        is NetworkError.Network -> {
                            Timber.e("Network errorType: ${it.errorType}")
                            Timber.e("Network msg: ${it.message}")
                            baseResultNavigator.postValue(BaseResult.ServerError(message = it.message))
                        }
                        is NetworkError.Timeout -> {
                            Timber.e("Timeout")
                        }
                        is NetworkError.Session -> {
                            Timber.e("Session")
                        }
                        is NetworkError.Unknown -> {
                            Timber.e("Unknown")
                        }
                        else -> {
                            Timber.e("???")
                        }
                    }
                }.onFailure {
                    Timber.e("onFailure")
                }.onClosed {
                    Timber.e("onClosed")
                }
        }
    }

    // 공통으로 처리하는 것만 추가하여 사용
    sealed class BaseResult(
        open val message: String
    ) {
        data class ToastMsg(override val message: String) : BaseResult(message)
        data class ServerError(override val message: String) : BaseResult(message)
    }

    private val _baseResultNavigator: SingleLiveData<BaseResult> = SingleLiveData()
    val baseResultNavigator: SingleLiveData<BaseResult> = _baseResultNavigator

    override fun onCleared() {
        super.onCleared()
        _errorHandler.close()
    }

}