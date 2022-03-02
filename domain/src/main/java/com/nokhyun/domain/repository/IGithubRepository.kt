package com.nokhyun.domain.repository

import com.nokhyun.domain.common.NetworkError
import com.nokhyun.domain.model.ReposResponse
import kotlinx.coroutines.channels.Channel

/**
 * Created by Nokhyun90 on 2022-03.02
 * */
interface IGithubRepository {
    suspend fun getRepoList(errorHandler: Channel<NetworkError>, owner: String): List<ReposResponse>?
}