package com.project.firstkotlin.screens.login

interface LoginContract {
    interface LoginView {
        fun goToRegister()
        fun goToMain()
    }

    interface LoginPresenter {
        fun onViewAttach(view: LoginView?)
        fun onViewDetach()
        fun onLoginClick()
        fun onRegisterClick()
    }
}