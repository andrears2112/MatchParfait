package com.example.matchparfait.view.interfaces

import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.model.entitys.ProductShopBag
import com.example.matchparfait.model.entitys.ProductWishList
import com.example.matchparfait.model.entitys.ShoppingCartRequest
import com.example.matchparfait.model.entitys.ShoppingCartUpdateRequest

interface ProductsView {
    fun OnProductsGetted(products : List<Product>){}

    fun OnErrorGettingProducts(message : String){}

    fun OnSuccesGettingCart(products : List<ProductShopBag>){}

    fun OnErrorGettingCart(message: String){}

    fun OnSuccessAddingCart(){}

    fun OnErrorAddindgCart(message: String){}

    fun OnDeleteOnCartSucces(){}

    fun OnErrorDeleteCart(message: String){}

    fun OnSuccesEditQuantity(){}

    fun OnErrorEditQuantity(message: String){}

    fun OnSuccesAddingWishList(){}

    fun OnErrorAddingWishList(message: String){}

    fun OnWishListGetted(products : List<ProductWishList>){}

    fun OnErrorGettingWishList(message: String){}

    fun OnSuccessDeleteWishList(){}

    fun OnErrorDeleteWishList(message: String){}
}