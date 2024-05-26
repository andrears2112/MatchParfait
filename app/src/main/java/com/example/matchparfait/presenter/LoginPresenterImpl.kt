package com.example.matchparfait.presenter

import android.content.Context
import com.example.matchparfait.model.entitys.User
import com.example.matchparfait.model.repositoy.LoginRepositoryImpl
import com.example.matchparfait.model.repositoy.interfaces.LoginRepository
import com.example.matchparfait.presenter.interfaces.LoginPresenter
import com.example.matchparfait.view.interfaces.LoginView

class LoginPresenterImpl(view : LoginView, context : Context) : LoginPresenter {

    private val loginRepo : LoginRepository
    private var loginView: LoginView
    private var context : Context

    init {
        this.loginRepo = LoginRepositoryImpl(this, context)
        this.loginView = view
        this.context = context
    }

    override fun Login(mail: String, password: String) {
        this.loginRepo.Login(mail, password)
    }

    override fun OnLoginSuccess(user: User) {
        this.loginView.OnLoginSuccces(user)
    }

    override fun OnLoginError(message: String) {
        this.loginView.OnLoginError(message)
    }
}