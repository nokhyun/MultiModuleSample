package com.nokhyun.domain.common

/**
 * Created by ChoKwangJun on 2022.02.25
 * 각 에러 별 필요한 변수들 있으면 추가하여 사용.
 * */
sealed class NetworkError(
    open val message: String,
    open val errorType: String
) {
    data class Timeout(override val message: String, override val errorType: String) : NetworkError(message, errorType)
    data class Session(override val message: String, override val errorType: String) : NetworkError(message, errorType)
    data class Network(override val message: String, override val errorType: String) : NetworkError(message, errorType)
    data class Unknown(override val message: String, override val errorType: String) : NetworkError(message, errorType)
}
