package com.example.matchparfait.presenter.interfaces

import com.example.matchparfait.model.entitys.PayRequest
import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.model.entitys.ProductShopBag
import com.example.matchparfait.model.entitys.ProductWishList
import com.example.matchparfait.model.entitys.ShoppingCartRequest
import com.example.matchparfait.model.entitys.ShoppingCartUpdateRequest
import com.example.matchparfait.model.entitys.WishListRequest

interface ProductsPresenter {

    fun GetProducts(){}

    fun OnProductsGetted(products : List<Product>){}

    fun OnErrorGettingProducts(message : String){}

    fun GetShoppingCart(){}

    fun OnSuccesGettingCart(products : List<ProductShopBag>){}

    fun OnErrorGettingCart(message: String){}

    fun AddShoppingCart(prodToShop : ShoppingCartRequest){}

    fun OnSuccessAddingCart(){}

    fun OnErrorAddindgCart(message: String){}

    fun DeleteShoppingCart(product : ShoppingCartUpdateRequest){}

    fun OnDeleteOnCartSucces(){}

    fun OnErrorDeleteCart(message: String){}

    fun EditQuantityShoppingCart(product: ShoppingCartUpdateRequest){}

    fun OnSuccesEditQuantity(){}

    fun OnErrorEditQuantity(message: String){}

    fun AddWishList(prod : WishListRequest){}

    fun OnSuccesAddingWishList(){}

    fun OnErrorAddingWishList(message: String){}

    fun GetWishList(){}

    fun OnWishListGetted(products : List<ProductWishList>){}

    fun OnErrorGettingWishList(message: String){}

    fun DeleteWishList(prod : ProductWishList){}

    fun OnSuccessDeleteWishList(){}

    fun OnErrorDeleteWishList(message: String){}

    fun CompleteSale(request : PayRequest){}

    fun OnSuccessPayment(){}

    fun OnErrorPayment(message: String){}
}