package com.nokhyun.samplestructure.viewmodel

import androidx.lifecycle.ViewModel
import com.nokhyun.samplestructure.utils.SingleLiveData

/**
 * Created by ChoKwangJun on 2022-02-24
 * */
abstract class BaseViewModel : ViewModel() {

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