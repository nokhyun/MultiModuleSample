package com.nokhyun.data.datasources

import com.nokhyun.data.common.GithubApi
import com.nokhyun.data.network.SampleClient
import com.nokhyun.domain.entity.ReposEntity

class GitHubRemoteDateSourceImpl(
    private val _client: SampleClient,
) : GitHubRemoteDataSource {
    override suspend fun getRemoteDataSource(owner: String): List<ReposEntity> {
        // 해당 부분에서 통신 오류에 대한 처리 필요
        val result = _client.defaultClient<GithubApi>().getRepos(owner).body()

        // Mapper class 필요
        return result?.map {
            ReposEntity(
                name = it.name,
                full_name = it.full_name,
                private = it.private
            )
        }!!.toList()
    }
}