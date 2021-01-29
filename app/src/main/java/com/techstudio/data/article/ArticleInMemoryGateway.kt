package com.techstudio.data.article

import com.techstudio.model.Article

class ArticleInMemoryGateway : ArticleLocalGateway {
    private val articles = hashSetOf<Article>()

    override fun saveArticles(items: List<Article>) {
        articles.addAll(items)
    }

    override fun getArticle(id: Long) = articles.find { it.id == id }
}