package com.techstudio.data.article

import com.techstudio.BuildConfig
import com.techstudio.model.Article
import com.techstudio.model.toLocal
import rx.Single

class ArticleRestGateway(
    private val articleApi: ArticleApi
) : ArticleRemoteGateway {

    override fun getMostEmailedArticles(period: Int): Single<List<Article>> =
        articleApi.getMostEmailed(period, BuildConfig.API_KEY).map {
            it.results.map { it.toLocal() }
        }

    override fun getMostSharedArticles(period: Int): Single<List<Article>> =
        articleApi.getMostShared(period, BuildConfig.API_KEY).map {
            it.results.map { it.toLocal() }
        }

    override fun getMostViewedArticles(period: Int): Single<List<Article>> =
        articleApi.getMostViewed(period, BuildConfig.API_KEY).map {
            it.results.map { it.toLocal() }
        }
}

interface ArticleRemoteGateway {
    fun getMostEmailedArticles(period: Int): Single<List<Article>>
    fun getMostSharedArticles(period: Int): Single<List<Article>>
    fun getMostViewedArticles(period: Int): Single<List<Article>>
}