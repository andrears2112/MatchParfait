package com.example.matchparfait.model.repositoy.interfaces

import com.example.matchparfait.model.entitys.ProductShopBag
import com.example.matchparfait.model.entitys.ProductWishList
import com.example.matchparfait.model.entitys.ShoppingCartRequest
import com.example.matchparfait.model.entitys.ShoppingCartUpdateRequest
import com.example.matchparfait.model.entitys.WishListRequest

interface ProductsRepository {
    fun GetProducts(){}

    fun GetShoppingCart(){}

    fun AddShoppingCart(prodToShop : ShoppingCartRequest){}

    fun DeleteShoppingCart(product : ShoppingCartUpdateRequest){}

    fun EditQuantityShoppingCart(product: ShoppingCartUpdateRequest){}

    fun AddWishList(prod : WishListRequest){}

    fun GetWishList(){}

    fun DeleteWishList(prod : ProductWishList){}
}