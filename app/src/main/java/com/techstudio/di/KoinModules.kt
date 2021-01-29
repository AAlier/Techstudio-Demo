package com.techstudio.di

object KoinModules {

    fun create() = listOf(
        NetworkModule.create(),
        MainModule.create()
    )

}