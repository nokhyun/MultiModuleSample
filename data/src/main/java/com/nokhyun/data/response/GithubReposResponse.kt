package com.nokhyun.data.response


/**
 * Created by Nokhyun90 on 2022.02.25
 * */
data class GithubReposResponse(
    val result: List<Repos>
){
    data class Repos(
        val id: String,
        val node_id: String,
        val name: String,
        val full_name: String,
        val private: String,
        val owner: Any,
    )
}
