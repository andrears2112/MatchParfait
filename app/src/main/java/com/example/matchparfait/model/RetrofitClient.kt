package com.example.matchparfait.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object etrofitClient {

    private var retrofit=Retrofit.Builder()
        .baseUrl("https://makeup-api.herokuapp.com/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    // retrofit service clinet
    val service= retrofit.create(ServiceClients::class.java)
}