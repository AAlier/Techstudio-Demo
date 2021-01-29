package com.techstudio.data.article

import com.techstudio.model.Article

interface ArticleLocalGateway {
    fun getArticle(id: Long): Article?
    fun saveArticles(items: List<Article>)
}