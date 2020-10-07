package com.project.firstkotlin.screens.login

class LoginPresenter : LoginContract.LoginPresenter {

    var view: LoginContract.LoginView? = null

    override fun onViewAttach(view: LoginContract.LoginView?) {
        this.view = view
    }

    override fun onViewDetach() {
        view = null
    }

    override fun onLoginClick() {
        view?.goToMain()
    }

    override fun onRegisterClick() {
        view?.goToRegister()
    }

}
