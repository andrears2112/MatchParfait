package com.example.matchparfait.model

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/api/v1/products")
    fun getData() : Call<String>
}