package com.example.matchparfait.view.interfaces

import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.model.entitys.ProductWishList
import com.example.matchparfait.model.entitys.ShoppingCartRequest

interface ProductsView {
    fun OnProductsGetted(products : List<Product>){}

    fun OnErrorGettingProducts(message : String){}

    fun OnSuccessAddingCart(){}

    fun OnErrorAddindgCart(message: String){}

    fun OnSuccesAddingWishList(){}

    fun OnErrorAddingWishList(message: String){}

    fun OnWishListGetted(products : List<ProductWishList>){}

    fun OnErrorGettingWishList(message: String){}

    fun OnSuccessDeleteWishList(){}

    fun OnErrorDeleteWishList(message: String){}
}