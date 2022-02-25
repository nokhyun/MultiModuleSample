package com.nokhyun.data.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Nokhyun90 on 2022.02.25
 * */
data class GithubReposResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("created_at")
    val date: String,
    @SerializedName("html_url")
    val url: String
)
