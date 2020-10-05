package com.project.firstkotlin.di

import com.project.firstkotlin.screens.demo.DemoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun bindDemoActivity() : DemoActivity
}