package com.nokhyun.domain.usecase

import com.nokhyun.domain.common.IUseCase
import com.nokhyun.domain.common.NetworkError
import kotlinx.coroutines.channels.Channel

/**
 * Created by ChoKwangJun on 2022.02.25
 * */
class GithubUseCase(
    // todo repo
): IUseCase<Any, Any> {

    override suspend fun invoke(request: Any, errorHandler: Channel<NetworkError>): Any {
        return "todo"
    }
}

