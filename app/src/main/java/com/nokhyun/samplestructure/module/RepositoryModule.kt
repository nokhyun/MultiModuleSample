package com.nokhyun.samplestructure.module

import com.nokhyun.data.datasources.GitHubRemoteDataSource
import com.nokhyun.data.repository.GithubRepositoryImpl
import com.nokhyun.data.network.SampleClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Nokhyun90 on 2022.02.25
 * */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGithubRepo(client: SampleClient, gitHubRemoteDataSource: GitHubRemoteDataSource) = GithubRepositoryImpl(client, gitHubRemoteDataSource)
}