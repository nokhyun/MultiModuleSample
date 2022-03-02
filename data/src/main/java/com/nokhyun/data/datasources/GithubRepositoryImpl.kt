package com.nokhyun.data.datasources

import com.nokhyun.data.common.GithubApi
import com.nokhyun.data.network.SampleClient
import com.nokhyun.data.repository.BaseRepository
import com.nokhyun.domain.common.NetworkError
import com.nokhyun.domain.model.ReposResponse
import com.nokhyun.domain.repository.IGithubRepository
import kotlinx.coroutines.channels.Channel
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Nokhyun90 on 2022.03.02
 * */
class GithubRepositoryImpl
@Inject constructor(
//    private val _githubApi: GithubApi
    private val _client: SampleClient
) : BaseRepository(), IGithubRepository {
    override suspend fun getRepoList(errorHandler: Channel<NetworkError>, owner: String): List<ReposResponse>? =
        safeApiCall(errorHandler) {
            val body = _client.defaultClient<GithubApi>().getRepos(owner).body()
            Timber.e("result: $body")
            body
        }
}