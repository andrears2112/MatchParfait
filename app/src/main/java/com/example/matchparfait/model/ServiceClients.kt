package com.example.matchparfait.model

import com.example.matchparfait.model.entitys.ProductItem
import retrofit2.Call
import retrofit2.http.GET

interface ServiceClients {

    @GET("products.json")
    fun getAllProducts(): Call<List<ProductItem>>
}