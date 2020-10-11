package com.project.firstkotlin.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.project.firstkotlin.data.model.Note
import com.project.firstkotlin.data.repository.LoginRepository
import com.project.firstkotlin.data.repository.NoteRepository
import com.project.firstkotlin.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : ViewModel() {
    private val loginRepository = LoginRepository()

    fun loginByEmail(email: String, password: String) : Boolean {
        return loginRepository.loginByEmail(email, password)
    }

    class LoginViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(application) as T
            }

            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}