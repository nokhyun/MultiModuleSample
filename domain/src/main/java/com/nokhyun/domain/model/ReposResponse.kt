package com.nokhyun.domain.model

import com.google.gson.annotations.SerializedName


/**
 * Created by Nokhyun90 on 2022.02.25
 * */
data class ReposResponse(
    @SerializedName("id")
//    val id: String,
//    val node_id: String,
    val name: String,
    val full_name: String,
    val private: String,
//    val owner: Any,
)
