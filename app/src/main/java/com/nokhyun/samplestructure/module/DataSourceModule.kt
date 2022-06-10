package com.nokhyun.samplestructure.module

import com.nokhyun.data.datasources.GitHubRemoteDataSource
import com.nokhyun.data.datasources.GitHubRemoteDateSourceImpl
import com.nokhyun.data.network.SampleClient
import com.nokhyun.data.repository.GithubRepositoryImpl
import com.nokhyun.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Nokhyun90 on 2022.03.02
 * */
@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideGithubDataSource(
        client: SampleClient,
        remoteDataSource: GitHubRemoteDataSource
    ): GithubRepository = GithubRepositoryImpl(client, remoteDataSource)

    @Provides
    @Singleton
    fun provideGithubRemoteDateSource(
        client: SampleClient
    ): GitHubRemoteDataSource = GitHubRemoteDateSourceImpl(client)
}