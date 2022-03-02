package com.nokhyun.samplestructure.module

import com.nokhyun.data.datasources.GithubRepositoryImpl
import com.nokhyun.data.interfaces.IGithubRepository
import com.nokhyun.data.network.SampleClient
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
        client: SampleClient
    ): IGithubRepository = GithubRepositoryImpl(client)
}