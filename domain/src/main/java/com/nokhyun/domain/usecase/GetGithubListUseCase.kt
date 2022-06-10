package com.nokhyun.domain.usecase

import com.nokhyun.domain.common.NetworkError
import com.nokhyun.domain.entity.ReposEntity
import com.nokhyun.domain.model.ReposResponse
import com.nokhyun.domain.repository.GithubRepository
import kotlinx.coroutines.channels.Channel

/**
 * Created by ChoKwangJun on 2022.02.25
 * */
class GetGithubListUseCase(
    private val _gitHubRepos: GithubRepository
) : GithubRepository {
    override suspend fun getRepoList(errorHandler: Channel<NetworkError>, owner: String): List<ReposEntity>? {
        return _gitHubRepos.getRepoList(errorHandler, owner)
    }

}

