package com.example.matchparfait.model.repositoy.interfaces

import com.example.matchparfait.model.entitys.ProductWishList
import com.example.matchparfait.model.entitys.ShoppingCartRequest
import com.example.matchparfait.model.entitys.WishListRequest

interface ProductsRepository {
    fun GetProducts(){}

    fun AddShoppingCart(prodToShop : ShoppingCartRequest){}

    fun AddWishList(prod : WishListRequest){}

    fun GetWishList(){}

    fun DeleteWishList(id : String){}
}