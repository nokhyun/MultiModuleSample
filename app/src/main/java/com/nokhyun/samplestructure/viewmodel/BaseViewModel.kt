package com.nokhyun.samplestructure.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokhyun.domain.common.NetworkError
import com.nokhyun.samplestructure.utils.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onClosed
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by ChoKwangJun on 2022-02-24
 * */
abstract class BaseViewModel : ViewModel() {

    private val _errorHandler = Channel<NetworkError>()
    val errorHandler = _errorHandler

    init {
        viewModelScope.launch(Dispatchers.Main) {
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

}