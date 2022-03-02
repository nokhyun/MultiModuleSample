package com.nokhyun.data.common

import com.nokhyun.data.common.URL.REPOS
import com.nokhyun.domain.model.ReposResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Nokhyun90 on 2022.02.25
 * */
interface GithubApi {
    @GET(REPOS)
    suspend fun getRepos(@Path("owner") owner: String): Response<List<ReposResponse>>
}