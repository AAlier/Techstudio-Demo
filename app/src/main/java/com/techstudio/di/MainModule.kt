package com.techstudio.di

import com.techstudio.data.article.*
import com.techstudio.data.db.DatabaseHelper
import com.techstudio.ui.detail.ArticleViewModel
import com.techstudio.ui.favourite.FavouriteViewModel
import com.techstudio.ui.main.emailed.MostEmailedViewModel
import com.techstudio.ui.main.shared.MostSharedViewModel
import com.techstudio.ui.main.viewed.MostViewedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

object MainModule : InjectionModule {
    override fun create(): Module {
        return module {
            single { DatabaseHelper(get()) }
            single { ArticleRestGateway(get()) } bind ArticleRemoteGateway::class
            single { ArticleInMemoryGateway() } bind ArticleLocalGateway::class
            single {
                ArticleInteractor(
                    get(),
                    get(),
                    get()
                )
            }
            single { get<Retrofit>().create(ArticleApi::class.java) }

            viewModel { MostEmailedViewModel(get()) }
            viewModel { MostSharedViewModel(get()) }
            viewModel { MostViewedViewModel(get()) }
            viewModel { FavouriteViewModel(get()) }
            viewModel { (id: Long) -> ArticleViewModel(get(), id) }
        }
    }
}