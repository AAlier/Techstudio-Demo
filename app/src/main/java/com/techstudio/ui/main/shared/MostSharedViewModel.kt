package com.techstudio.ui.main.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techstudio.data.article.ArticleInteractor
import com.techstudio.model.Article
import com.techstudio.model.Period
import com.techstudio.util.SingleLiveEvent
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MostSharedViewModel(
    private val articleInteractor: ArticleInteractor
) : ViewModel() {
    val items = MutableLiveData<List<Article>>()
    val isRefreshing = MutableLiveData<Boolean>()
    val error = SingleLiveEvent<Throwable>()

    init {
        getArticles()
    }

    fun getArticles(period: Period = Period.MONTH) {
        isRefreshing.postValue(true)
        articleInteractor.getMostShared(period.durationInDays)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    items.postValue(it)
                    isRefreshing.postValue(false)
                }, {
                    error.postValue(it)
                    isRefreshing.postValue(false)
                }
            )
    }

    fun onAddFavourite(article: Article) {
        articleInteractor.insertArticleFavorite(article)
    }
}