package com.techstudio.ui.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techstudio.data.article.ArticleInteractor
import com.techstudio.model.Article
import com.techstudio.util.SingleLiveEvent
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class FavouriteViewModel(
    private val articleInteractor: ArticleInteractor
) : ViewModel() {
    val items = MutableLiveData<List<Article>>()
    val error = SingleLiveEvent<Throwable>()

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

    fun toggle(id: Long) {
        articleInteractor.toggle(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getArticles()
            }, {
                error.postValue(it)
            })
    }
}