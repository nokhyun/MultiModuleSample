package com.nokhyun.samplestructure.module

import com.nokhyun.data.datasources.GithubRepositoryImpl
import com.nokhyun.domain.repository.IGithubRepository
import com.nokhyun.domain.usecase.RepoListUseCase
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
object UseCaseModule {

    @Provides
    @Singleton
    fun provideRepoListUseCase(githubRepository: IGithubRepository) = RepoListUseCase(githubRepository)

}