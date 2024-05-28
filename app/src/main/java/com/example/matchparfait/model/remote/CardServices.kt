package com.example.matchparfait.model.remote

import com.example.matchparfait.model.dataSources.Wrapper
import com.example.matchparfait.model.entitys.Card
import com.example.matchparfait.model.entitys.ResponseService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST

interface CardServices {

    @GET("/api/cards")
    fun GetCard(
        @Header("Authorization") str: String)
            : Call<Wrapper<Card>>

    @HTTP(method = "PUT", path = "/api/cards", hasBody = true)
    fun EditCard(
        @Header("Authorization") str: String,
        @Body product: Card
    ): Call<Wrapper<ResponseService>>

    @POST("/api/cards")
    fun AddCard(
        @Header("Authorization") str: String,
        @Body product: Card
    ): Call<Wrapper<ResponseService>>
}