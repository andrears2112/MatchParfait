package com.example.matchparfait.model.repositoy

import android.content.Context
import com.example.matchparfait.model.dataSources.AppServiceClient
import com.example.matchparfait.model.dataSources.CheckObjectResult
import com.example.matchparfait.model.dataSources.ResultInterface
import com.example.matchparfait.model.dataSources.ServiceResponse
import com.example.matchparfait.model.dataSources.Wrapper
import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.model.entitys.ProductShopBag
import com.example.matchparfait.model.entitys.ProductWishList
import com.example.matchparfait.model.entitys.ResponseService
import com.example.matchparfait.model.entitys.ShoppingCartRequest
import com.example.matchparfait.model.entitys.ShoppingCartUpdateRequest
import com.example.matchparfait.model.entitys.WishListRequest
import com.example.matchparfait.model.remote.ProductsServices
import com.example.matchparfait.model.repositoy.interfaces.ProductsRepository
import com.example.matchparfait.presenter.interfaces.ProductsPresenter
import com.example.matchparfait.utils.Helpers
import retrofit2.Response

class ProductsRepositoryImpl(productsPresenter: ProductsPresenter, context: Context) : ProductsRepository{

    private var prodPresenter : ProductsPresenter
    private var appServiceClient : AppServiceClient? = null
    private var applicationContext = context
    private var responseProducts : ServiceResponse<Wrapper<Product>, Product> = ServiceResponse<Wrapper<Product>, Product>()
    private var responseService : ServiceResponse<Wrapper<ResponseService>, ResponseService> = ServiceResponse<Wrapper<ResponseService>, ResponseService>()
    private var responseWishList : ServiceResponse<Wrapper<ProductWishList>, ProductWishList> = ServiceResponse<Wrapper<ProductWishList>, ProductWishList>()
    private var responseShopBag : ServiceResponse<Wrapper<ProductShopBag>, ProductShopBag> = ServiceResponse<Wrapper<ProductShopBag>, ProductShopBag>()

    init {
        this.prodPresenter = productsPresenter
        if(this.applicationContext != null){
            this.appServiceClient = AppServiceClient(this.applicationContext)
        }
    }

    override fun GetProducts() {
        this.responseProducts.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(ProductsServices::class.java)!!.GetProducts(Helpers.getToken()),
            object : ResultInterface<Wrapper<Product>> {
                override fun failWithError(message: String) {
                    prodPresenter.OnErrorGettingProducts(message)
                }

                override fun notFound(message: String) {
                    prodPresenter.OnErrorGettingProducts(message)
                }

                override fun success(body: Response<Wrapper<Product>>) {
                    if(CheckObjectResult(body.body()!!)){
                        Helpers.saveProducts(body.body()!!.data)
                        prodPresenter.OnProductsGetted(body.body()!!.data)
                    }
                    else {
                        prodPresenter.OnErrorGettingProducts(body.body()!!.userMsg)
                    }
                }
            }
        )
    }

    override fun AddShoppingCart(prodToShop: ShoppingCartRequest) {
        this.responseService.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(ProductsServices::class.java)!!.AddShoppingCart(Helpers.getToken(), prodToShop),
            object : ResultInterface<Wrapper<ResponseService>> {
                override fun failWithError(message: String) {
                    prodPresenter.OnErrorAddindgCart(message)
                }

                override fun notFound(message: String) {
                    prodPresenter.OnErrorAddindgCart(message)
                }

                override fun success(body: Response<Wrapper<ResponseService>>) {
                    if(CheckObjectResult(body.body()!!)){
                        prodPresenter.OnSuccessAddingCart()
                    }
                    else {
                        prodPresenter.OnErrorAddindgCart(body.body()!!.userMsg)
                    }
                }
            }
        )
    }

    override fun AddWishList(prod : WishListRequest) {
        this.responseService.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(ProductsServices::class.java)!!.AddWishList(Helpers.getToken(), prod),
            object : ResultInterface<Wrapper<ResponseService>> {
                override fun failWithError(message: String) {
                    prodPresenter.OnErrorAddingWishList(message)
                }

                override fun notFound(message: String) {
                    prodPresenter.OnErrorAddingWishList(message)
                }

                override fun success(body: Response<Wrapper<ResponseService>>) {
                    if(CheckObjectResult(body.body()!!)){
                        prodPresenter.OnSuccesAddingWishList()
                    }
                    else {
                        prodPresenter.OnErrorAddingWishList(body.body()!!.userMsg)
                    }
                }
            }
        )
    }

    override fun GetWishList() {
        this.responseWishList.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(ProductsServices::class.java)!!.GetWishList(Helpers.getToken()),
            object : ResultInterface<Wrapper<ProductWishList>> {
                override fun failWithError(message: String) {
                    prodPresenter.OnErrorGettingWishList(message)
                }

                override fun notFound(message: String) {
                    prodPresenter.OnErrorGettingWishList(message)
                }

                override fun success(body: Response<Wrapper<ProductWishList>>) {
                    if(CheckObjectResult(body.body()!!)){
                        prodPresenter.OnWishListGetted(body.body()!!.data)
                    }
                    else {
                        prodPresenter.OnErrorGettingWishList(body.body()!!.userMsg)
                    }
                }
            }
        )
    }

    override fun DeleteWishList(prod : ProductWishList) {
        this.responseService.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(ProductsServices::class.java)!!.DeleteWishList(Helpers.getToken(), prod),
            object : ResultInterface<Wrapper<ResponseService>> {
                override fun failWithError(message: String) {
                    prodPresenter.OnErrorDeleteWishList(message)
                }

                override fun notFound(message: String) {
                    prodPresenter.OnErrorDeleteWishList(message)
                }

                override fun success(body: Response<Wrapper<ResponseService>>) {
                    if(CheckObjectResult(body.body()!!)){
                        prodPresenter.OnSuccessDeleteWishList()
                    }
                    else {
                        prodPresenter.OnErrorDeleteWishList(body.body()!!.userMsg)
                    }
                }
            }
        )
    }

    override fun GetShoppingCart() {
        this.responseShopBag.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(ProductsServices::class.java)!!.GetShoppingCart(Helpers.getToken()),
            object : ResultInterface<Wrapper<ProductShopBag>> {
                override fun failWithError(message: String) {
                    prodPresenter.OnErrorGettingCart(message)
                }

                override fun notFound(message: String) {
                    prodPresenter.OnErrorGettingCart(message)
                }

                override fun success(body: Response<Wrapper<ProductShopBag>>) {
                    if(CheckObjectResult(body.body()!!)){
                        prodPresenter.OnSuccesGettingCart(body.body()!!.data)
                    }
                    else {
                        prodPresenter.OnErrorGettingCart(body.body()!!.userMsg)
                    }
                }
            }
        )
    }

    override fun DeleteShoppingCart(product: ShoppingCartUpdateRequest) {
        this.responseService.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(ProductsServices::class.java)!!.DeleteShoppingCart(Helpers.getToken(), product),
            object : ResultInterface<Wrapper<ResponseService>> {
                override fun failWithError(message: String) {
                    prodPresenter.OnErrorDeleteCart(message)
                }

                override fun notFound(message: String) {
                    prodPresenter.OnErrorDeleteCart(message)
                }

                override fun success(body: Response<Wrapper<ResponseService>>) {
                    if(CheckObjectResult(body.body()!!)){
                        prodPresenter.OnDeleteOnCartSucces()
                    }
                    else {
                        prodPresenter.OnErrorDeleteCart(body.body()!!.userMsg)
                    }
                }
            }
        )
    }

    override fun EditQuantityShoppingCart(product: ShoppingCartUpdateRequest) {
        this.responseService.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(ProductsServices::class.java)!!.EditQuantityShoppingCart(Helpers.getToken(), product),
            object : ResultInterface<Wrapper<ResponseService>> {
                override fun failWithError(message: String) {
                    prodPresenter.OnErrorEditQuantity(message)
                }

                override fun notFound(message: String) {
                    prodPresenter.OnErrorEditQuantity(message)
                }

                override fun success(body: Response<Wrapper<ResponseService>>) {
                    if(CheckObjectResult(body.body()!!)){
                        prodPresenter.OnSuccesEditQuantity()
                    }
                    else {
                        prodPresenter.OnErrorEditQuantity(body.body()!!.userMsg)
                    }
                }
            }
        )
    }
}