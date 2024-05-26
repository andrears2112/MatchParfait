package com.example.matchparfait.model.remote

import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.model.dataSources.Wrapper
import com.example.matchparfait.model.entitys.ProductWishList
import com.example.matchparfait.model.entitys.ResponseService
import com.example.matchparfait.model.entitys.ShoppingCartRequest
import com.example.matchparfait.model.entitys.WishListRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST

interface ProductsServices {

    @GET("/api/productDetail")
    fun GetProducts(
        @Header("Authorization") str: String)
    : Call<Wrapper<Product>>

    @POST("/api/shoppingCart")
    fun AddShoppingCart(
        @Header("Authorization") str: String,
        @Body product: ShoppingCartRequest)
    : Call<Wrapper<ResponseService>>

    @POST("/api/wishList")
    fun AddWishList(
        @Header("Authorization") str: String,
        @Body prod : WishListRequest)
            : Call<Wrapper<ResponseService>>

    @GET("/api/wishList")
    fun GetWishList(
        @Header("Authorization") str: String)
            : Call<Wrapper<ProductWishList>>

    @HTTP(method = "DELETE", path = "/api/wishList", hasBody = true)
    fun DeleteWishList(
        @Header("Authorization") str: String,
        @Body prod : ProductWishList)
            : Call<Wrapper<ResponseService>>
}