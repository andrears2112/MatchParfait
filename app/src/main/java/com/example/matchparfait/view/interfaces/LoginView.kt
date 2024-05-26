package com.example.matchparfait.view.interfaces

import com.example.matchparfait.model.entitys.User

interface LoginView {
    fun OnLoginSuccces(user : User){}

    fun OnLoginError(message : String){}
}