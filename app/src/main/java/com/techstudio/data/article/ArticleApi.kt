package com.techstudio.data.article

import com.techstudio.data.article.model.ArticleList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Single

interface ArticleApi {

    @GET("emailed/{period}.json")
    fun getMostEmailed(
        @Path("period") period: Int,
        @Query("api-key") key: String
    ): Single<ArticleList>

    @GET("shared/{period}.json")
    fun getMostShared(
        @Path("period") period: Int,
        @Query("api-key") key: String
    ): Single<ArticleList>

    @GET("viewed/{period}.json")
    fun getMostViewed(
        @Path("period") period: Int,
        @Query("api-key") key: String
    ): Single<ArticleList>
}