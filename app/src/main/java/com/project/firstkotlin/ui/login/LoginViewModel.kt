package com.project.firstkotlin.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.firstkotlin.data.repository.Repository

class LoginViewModel : ViewModel() {

    private val repository = Repository.getInstance()

    val isLogin = MutableLiveData<Boolean>()

    fun login(email: String, password: String) =
        repository.login(email, password).addOnCompleteListener {
            isLogin.value = it.isSuccessful
        }
}