package com.example.matchparfait.presenter

import android.content.Context
import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.model.entitys.ProductWishList
import com.example.matchparfait.model.entitys.ShoppingCartRequest
import com.example.matchparfait.model.entitys.WishListRequest
import com.example.matchparfait.model.repositoy.ProductsRepositoryImpl
import com.example.matchparfait.model.repositoy.interfaces.ProductsRepository
import com.example.matchparfait.presenter.interfaces.ProductsPresenter
import com.example.matchparfait.view.interfaces.ProductsView

class ProductsPresenterImpl(view : ProductsView, context: Context) : ProductsPresenter {

    private var productsRepo : ProductsRepository
    private var productsView : ProductsView
    private var context : Context

    init {
        this.productsRepo = ProductsRepositoryImpl(this, context)
        this.productsView = view
        this.context = context
    }

    override fun GetProducts() {
        this.productsRepo.GetProducts()
    }

    override fun OnProductsGetted(products: List<Product>) {
        this.productsView.OnProductsGetted(products)
    }

    override fun OnErrorGettingProducts(message: String) {
        this.productsView.OnErrorGettingProducts(message)
    }

    override fun AddShoppingCart(prodToShop : ShoppingCartRequest){
        this.productsRepo.AddShoppingCart(prodToShop)
    }

    override fun OnSuccessAddingCart(){
        this.productsView.OnSuccessAddingCart()
    }

    override fun OnErrorAddindgCart(message: String){
        this.productsView.OnErrorAddindgCart(message)
    }

    override fun AddWishList(prod : WishListRequest){
        this.productsRepo.AddWishList(prod)
    }

    override fun OnSuccesAddingWishList(){
        this.productsView.OnSuccesAddingWishList()
    }

    override fun OnErrorAddingWishList(message: String){
        this.productsView.OnErrorAddindgCart(message)
    }

    override fun GetWishList(){
        this.productsRepo.GetWishList()
    }

    override fun OnWishListGetted(products : List<ProductWishList>){
        this.productsView.OnWishListGetted(products)
    }

    override fun OnErrorGettingWishList(message: String){
        this.productsView.OnErrorGettingWishList(message)
    }

    override fun DeleteWishList(id : String){
        this.productsRepo.DeleteWishList(id)
    }

    override fun OnSuccessDeleteWishList(){
        this.productsView.OnSuccessDeleteWishList()
    }

    override fun OnErrorDeleteWishList(message: String){
        this.OnErrorDeleteWishList(message)
    }

}