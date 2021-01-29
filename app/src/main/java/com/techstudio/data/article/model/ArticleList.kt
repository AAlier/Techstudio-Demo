package com.techstudio.data.article.model

import com.google.gson.annotations.SerializedName

data class ArticleList(
    @SerializedName("status")
    val status: Status,
    @SerializedName("num_results")
    val numResults: Int,
    @SerializedName("results")
    val results: List<Article>
)