package com.example.matchparfait.presenter

import android.content.Context
import com.example.matchparfait.model.entitys.PayRequest
import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.model.entitys.ProductShopBag
import com.example.matchparfait.model.entitys.ProductWishList
import com.example.matchparfait.model.entitys.ShoppingCartRequest
import com.example.matchparfait.model.entitys.ShoppingCartUpdateRequest
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

    override fun DeleteShoppingCart(product : ShoppingCartUpdateRequest){
        this.productsRepo.DeleteShoppingCart(product)
    }

    override fun OnDeleteOnCartSucces(){
        this.productsView.OnDeleteOnCartSucces()
    }

    override fun OnErrorDeleteCart(message: String){
        this.productsView.OnErrorDeleteCart(message)
    }

    override fun EditQuantityShoppingCart(product: ShoppingCartUpdateRequest){
        this.productsRepo.EditQuantityShoppingCart(product)
    }

    override fun OnSuccesEditQuantity(){
        this.productsView.OnSuccesEditQuantity()
    }

    override fun OnErrorEditQuantity(message: String){
        this.productsView.OnErrorEditQuantity(message)
    }

    override fun GetShoppingCart(){
        this.productsRepo.GetShoppingCart()
    }

    override fun OnSuccesGettingCart(products : List<ProductShopBag>){
        this.productsView.OnSuccesGettingCart(products)
    }

    override fun OnErrorGettingCart(message: String){
        this.productsView.OnErrorGettingCart(message)
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

    override fun DeleteWishList(prod : ProductWishList){
        this.productsRepo.DeleteWishList(prod)
    }

    override fun OnSuccessDeleteWishList(){
        this.productsView.OnSuccessDeleteWishList()
    }

    override fun OnErrorDeleteWishList(message: String){
        this.productsView.OnErrorDeleteWishList(message)
    }

    override fun CompleteSale(request : PayRequest){
        this.productsRepo.CompleteSale(request)
    }

    override fun OnSuccessPayment(){
        this.productsView.OnSuccessPayment()
    }

    override fun OnErrorPayment(message: String){
        this.OnErrorPayment(message)
    }
}