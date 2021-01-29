package com.techstudio.ui.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techstudio.data.article.ArticleInteractor
import com.techstudio.model.Article
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class FavouriteViewModel(
    private val articleInteractor: ArticleInteractor
) : ViewModel() {
    val items = MutableLiveData<List<Article>>()

    init {
        getArticles()
    }

    fun getArticles() {
        articleInteractor.getFavourites()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    items.postValue(it)
                },
                {
                    it.printStackTrace()
                }
            )
    }

    fun removeArticle(id: Long) {
        articleInteractor.removeArticleFavorite(id)
    }
}