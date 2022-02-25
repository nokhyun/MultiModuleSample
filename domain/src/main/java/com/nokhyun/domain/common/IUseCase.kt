package com.nokhyun.domain.common

import kotlinx.coroutines.channels.Channel

/**
 * Created by Nokhyun90 on 2022.02.25
 * Rx 사용 시 errorHandler 타입변경 및 suspend 제거 후 사용
 * */
interface IUseCase<T, R> {
    suspend fun invoke(request: T, errorHandler: Channel<NetworkError>): R
}