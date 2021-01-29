package com.techstudio.di

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.GsonBuilder
import com.techstudio.BuildConfig
import com.techstudio.data.network.adapter.DateTimeTypeAdapter
import com.techstudio.data.network.environment.Environment
import com.techstudio.data.network.environment.EnvironmentManager
import com.techstudio.data.network.errors.ServerErrorInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object NetworkModule : InjectionModule {
    private const val DEFAULT_CONNECT_TIMEOUT_SECONDS = 60L
    private const val DEFAULT_READ_TIMEOUT_SECONDS = 60L
    private const val DEFAULT_DISK_CACHE_SIZE = 256 * 1024 * 1024L

    override fun create(): Module {
        return module {
            single { PreferenceManager.getDefaultSharedPreferences(get()) }

            single {
                EnvironmentManager(sharedPreferences = get())
            }
            single { get<EnvironmentManager>().loadEnvironment() }

            single {
                Retrofit.Builder()
                    .baseUrl("${get<Environment>().restAddress}/")
                    .addConverterFactory(GsonConverterFactory.create(get()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .callFactory(get<OkHttpClient>())
                    .build()
            }

            single {
                GsonBuilder()
                    .apply { if (BuildConfig.DEBUG) setPrettyPrinting() }
                    .registerTypeAdapter(Calendar::class.java, DateTimeTypeAdapter)
                    .create()
            }

            single {
                OkHttpClient.Builder()
                    .readTimeout(DEFAULT_CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .connectTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .cache(Cache(get<Context>().cacheDir, DEFAULT_DISK_CACHE_SIZE))
                    .addInterceptor(ServerErrorInterceptor(get()))
                    .build()
            }
        }
    }
}