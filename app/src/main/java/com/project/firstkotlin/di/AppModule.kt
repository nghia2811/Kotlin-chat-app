package com.project.firstkotlin.di

import com.project.firstkotlin.network.GithubService
import com.project.firstkotlin.ImageLoader
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideGithubApi() = GithubService.create()

    @Provides
    fun provideImageLoader() = ImageLoader()
}