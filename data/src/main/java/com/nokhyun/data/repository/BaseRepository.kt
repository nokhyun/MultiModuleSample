package com.nokhyun.data.repository

import com.nokhyun.domain.common.NetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by Nokhyun90 on 2022.02.25
 * */
open class BaseRepository {

    suspend inline fun <T> safeApiCall(errorHandler: Channel<NetworkError>, crossinline block: suspend () -> T): T? =
        runCatching {
            withContext(Dispatchers.IO) {
                block()
            }
        }.onFailure { throwable ->
                throwable.printStackTrace()
                withContext(Dispatchers.Main) {
                    when (throwable) {
                        is HttpException -> {
                            errorHandler.send(NetworkError.Session(message = throwable.message(), errorType = throwable.code().toString()))
                        }
                        is SocketTimeoutException -> {
                            errorHandler.send(NetworkError.Timeout(message = throwable.message ?: "", errorType = "timeout"))
                        }
                        is IOException -> {
                            errorHandler.send(NetworkError.Network(message = throwable.message ?: "", errorType = "IO"))
                        }
                        else -> {
                            errorHandler.send(NetworkError.Unknown(message = throwable.message?: "", "unknown"))
                        }
                    }
                }
            }.getOrNull()
}
