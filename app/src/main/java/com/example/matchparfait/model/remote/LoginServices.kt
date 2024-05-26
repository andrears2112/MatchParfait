package com.example.matchparfait.model.remote

import com.example.matchparfait.model.dataSources.Wrapper
import com.example.matchparfait.model.entitys.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginServices {
    @POST("/api/auth/login")
    fun Login(@Body user: User): Call<Wrapper<User>>
}