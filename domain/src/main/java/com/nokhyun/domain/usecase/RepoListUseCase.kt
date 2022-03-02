package com.nokhyun.domain.usecase

import com.nokhyun.domain.common.NetworkError
import com.nokhyun.domain.model.ReposResponse
import com.nokhyun.domain.repository.IGithubRepository
import kotlinx.coroutines.channels.Channel

/**
 * Created by ChoKwangJun on 2022.02.25
 * */
class RepoListUseCase(
    private val _gitHubRepos: IGithubRepository
) : IGithubRepository {
    override suspend fun getRepoList(errorHandler: Channel<NetworkError>, owner: String): List<ReposResponse>? {
        return _gitHubRepos.getRepoList(errorHandler, owner)
    }

}

