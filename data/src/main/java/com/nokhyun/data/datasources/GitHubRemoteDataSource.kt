package com.nokhyun.data.datasources

import com.nokhyun.domain.entity.ReposEntity

interface GitHubRemoteDataSource {
    suspend fun getRemoteDataSource(owner: String): List<ReposEntity>
}