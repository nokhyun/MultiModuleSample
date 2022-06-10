package com.nokhyun.data.repository

import com.nokhyun.data.datasources.GitHubRemoteDataSource
import com.nokhyun.data.network.SampleClient
import com.nokhyun.domain.common.NetworkError
import com.nokhyun.domain.entity.ReposEntity
import com.nokhyun.domain.repository.GithubRepository
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

/**
 * Created by Nokhyun90 on 2022.03.02
 * */
class GithubRepositoryImpl
@Inject constructor(
//    private val _githubApi: GithubApi
    private val _client: SampleClient,
    private val remoteDataSource: GitHubRemoteDataSource

) : BaseRepository(), GithubRepository {
    override suspend fun getRepoList(errorHandler: Channel<NetworkError>, owner: String): List<ReposEntity>? {
//        safeApiCall(errorHandler) {
//            val body = _client.defaultClient<GithubApi>().getRepos(owner).body()
//            Timber.e("result: $body")
//            body
//        }
        return remoteDataSource.getRemoteDataSource(owner)
    }
}