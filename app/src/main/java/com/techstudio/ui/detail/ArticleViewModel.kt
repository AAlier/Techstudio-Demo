package com.techstudio.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techstudio.data.article.ArticleInteractor
import com.techstudio.model.Article
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ArticleViewModel(
    private val articleInteractor: ArticleInteractor,
    private val id: Long
) : ViewModel() {

    val article = MutableLiveData<Article>(articleInteractor.getArticle(id))

    fun toggleFavourite() {
        articleInteractor.toggle(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                article.postValue(it)
            }, {
                it.printStackTrace()
            })
    }
}