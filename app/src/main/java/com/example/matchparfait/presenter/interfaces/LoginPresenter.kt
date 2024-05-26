package com.example.matchparfait.presenter.interfaces

import com.example.matchparfait.model.entitys.User

interface LoginPresenter {
    fun Login(mail : String, password : String){}

    fun OnLoginSuccess(user : User){}

    fun OnLoginError(message : String){}
}