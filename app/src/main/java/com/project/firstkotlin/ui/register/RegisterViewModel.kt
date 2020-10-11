package com.project.firstkotlin.ui.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.firstkotlin.data.repository.UserRepository

class RegisterViewModel(application: Application) : ViewModel() {
    val userRepository = UserRepository()

    fun registerByEmail(email: String, password: String, name: String, address: String) : Boolean {
        return userRepository.registerAccount(email, password, name, address)
    }

    class RegisterViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                return RegisterViewModel(application) as T
            }

            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}