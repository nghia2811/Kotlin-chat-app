package com.project.firstkotlin.ui.createchat

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewchatViewModel(application: Application) : ViewModel() {

    class NewchatViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewchatViewModel::class.java)) {
                return NewchatViewModel(application) as T
            }

            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}