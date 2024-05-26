package com.example.matchparfait.presenter.interfaces

import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.model.entitys.ProductWishList
import com.example.matchparfait.model.entitys.ShoppingCartRequest
import com.example.matchparfait.model.entitys.WishListRequest

interface ProductsPresenter {

    fun GetProducts(){}

    fun OnProductsGetted(products : List<Product>){}

    fun OnErrorGettingProducts(message : String){}

    fun AddShoppingCart(prodToShop : ShoppingCartRequest){}

    fun OnSuccessAddingCart(){}

    fun OnErrorAddindgCart(message: String){}

    fun AddWishList(prod : WishListRequest){}

    fun OnSuccesAddingWishList(){}

    fun OnErrorAddingWishList(message: String){}

    fun GetWishList(){}

    fun OnWishListGetted(products : List<ProductWishList>){}

    fun OnErrorGettingWishList(message: String){}

    fun DeleteWishList(prod : ProductWishList){}

    fun OnSuccessDeleteWishList(){}

    fun OnErrorDeleteWishList(message: String){}
}