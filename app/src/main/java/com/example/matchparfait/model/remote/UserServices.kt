package com.example.matchparfait.model.remote

import com.example.matchparfait.model.dataSources.Wrapper
import com.example.matchparfait.model.entitys.AddressUser
import com.example.matchparfait.model.entitys.ProductShopBag
import com.example.matchparfait.model.entitys.ResponseService
import com.example.matchparfait.model.entitys.ShoppingCartUpdateRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header

interface UserServices {

    @GET("/api/shoppingCart/address")
    fun GetAddress(
        @Header("Authorization") str: String)
            : Call<Wrapper<AddressUser>>

    @HTTP(method = "PUT", path = "/api/shoppingCart/address", hasBody = true)
    fun EditAddress(
        @Header("Authorization") str: String,
        @Body product: AddressUser
    ): Call<Wrapper<ResponseService>>
}