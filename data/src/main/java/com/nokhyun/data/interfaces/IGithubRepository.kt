package com.nokhyun.data.interfaces

import com.nokhyun.data.response.GithubReposResponse
import com.nokhyun.domain.common.NetworkError
import kotlinx.coroutines.channels.Channel
/**
 * Created by Nokhyun90 on 2022.03.02
 * */
interface IGithubRepository {
    suspend fun getRepos(owner: String, errorHandler: Channel<NetworkError>): List<GithubReposResponse>?

}