package com.techstudio.data.article

import com.techstudio.data.db.DatabaseHelper
import com.techstudio.model.Article
import rx.Single

class ArticleInteractor(
    private val remoteGateway: ArticleRemoteGateway,
    private val localGateway: ArticleLocalGateway,
    private val database: DatabaseHelper
) {

    fun loadMostEmailed(period: Int): Single<List<Article>> {
        return remoteGateway.getMostEmailedArticles(period)
            .flatMap {
                localGateway.saveArticles(it)
                return@flatMap Single.just(it)
            }
    }

    fun loadMostShared(period: Int): Single<List<Article>> {
        return remoteGateway.getMostSharedArticles(period)
            .flatMap {
                localGateway.saveArticles(it)
                return@flatMap Single.just(it)
            }
    }

    fun loadMostViewed(period: Int): Single<List<Article>> {
        return remoteGateway.getMostViewedArticles(period)
            .flatMap {
                localGateway.saveArticles(it)
                return@flatMap Single.just(it)
            }
    }

    fun getArticle(id: Long) = database.getArticle(id) ?: localGateway.getArticle(id)

    fun removeArticleFavorite(id: Long) {
        database.deleteArticle(id)
    }

    fun insertArticleFavorite(article: Article) {
        database.insertArticle(article)
    }

    fun getFavourites(): Single<List<Article>> {
        return Single.just(database.getAllArticles())
    }

    fun toggle(id: Long): Single<Article> {
        return Single.create {
            val article = getArticle(id)
                ?: return@create it.onError(throw IllegalStateException("Article not found"))
            if (article.isFavourite) {
                database.deleteArticle(article.id)
            } else {
                database.insertArticle(article)
            }
            it.onSuccess(article.apply { isFavourite = !article.isFavourite })
        }
    }
}