package com.nokhyun.data.datasources

import com.nokhyun.data.common.GithubApi
import com.nokhyun.data.interfaces.IGithubRepository
import com.nokhyun.data.network.SampleClient
import com.nokhyun.data.repository.BaseRepository
import com.nokhyun.data.response.GithubReposResponse
import com.nokhyun.domain.common.NetworkError
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

/**
 * Created by Nokhyun90 on 2022.03.02
 * */
class GithubRepositoryImpl
@Inject constructor(
//    private val _githubApi: GithubApi
    private val _client: SampleClient
) : BaseRepository(), IGithubRepository {
    override suspend fun getRepos(owner: String, errorHandler: Channel<NetworkError>): List<GithubReposResponse>? =
        safeApiCall(errorHandler) {
            _client.defaultClient<GithubApi>().getRepos(owner).body()
        }
}