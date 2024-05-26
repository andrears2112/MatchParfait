package com.example.matchparfait.model.repositoy.interfaces

interface LoginRepository {
    fun Login(mail : String, password : String){}
}