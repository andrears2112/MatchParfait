package com.example.matchparfait.model.repositoy

import android.content.Context
import com.example.matchparfait.model.dataSources.AppServiceClient
import com.example.matchparfait.model.dataSources.CheckObjectResult
import com.example.matchparfait.model.dataSources.ResultInterface
import com.example.matchparfait.model.dataSources.ServiceResponse
import com.example.matchparfait.model.dataSources.Wrapper
import com.example.matchparfait.model.entitys.AddressUser
import com.example.matchparfait.model.entitys.ResponseService
import com.example.matchparfait.model.remote.ProductsServices
import com.example.matchparfait.model.remote.UserServices
import com.example.matchparfait.model.repositoy.interfaces.UserRepository
import com.example.matchparfait.presenter.interfaces.UserPresenter
import com.example.matchparfait.utils.Helpers
import retrofit2.Response

class UserRepositoryImpl(userPresenter: UserPresenter, context: Context) :
    UserRepository {

    private var userPresenter : UserPresenter
    private var appServiceClient : AppServiceClient? = null
    private var applicationContext = context
    private var responseAddress : ServiceResponse<Wrapper<AddressUser>, AddressUser> = ServiceResponse<Wrapper<AddressUser>, AddressUser>()
    private var responseService : ServiceResponse<Wrapper<ResponseService>, ResponseService> = ServiceResponse<Wrapper<ResponseService>, ResponseService>()

    init {
        this.userPresenter = userPresenter
        if(this.applicationContext != null){
            this.appServiceClient = AppServiceClient(this.applicationContext)
        }
    }

    override fun GetAddress() {
        this.responseAddress.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(UserServices::class.java)!!.GetAddress(Helpers.getToken()),
            object : ResultInterface<Wrapper<AddressUser>> {
                override fun failWithError(message: String) {
                    userPresenter.OnErrorGettingAdress(message)
                }

                override fun notFound(message: String) {
                    userPresenter.OnErrorGettingAdress(message)
                }

                override fun success(body: Response<Wrapper<AddressUser>>) {
                    if(CheckObjectResult(body.body()!!)){
                        userPresenter.OnSuccessGettingAddress(body.body()!!.data.first())
                    }
                    else {
                        userPresenter.OnErrorGettingAdress(body.body()!!.userMsg)
                    }
                }
            }
        )
    }

    override fun EditAddress(address: AddressUser) {
        this.responseService.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(UserServices::class.java)!!.EditAddress(Helpers.getToken(), address),
            object : ResultInterface<Wrapper<ResponseService>> {
                override fun failWithError(message: String) {
                    userPresenter.OnErrorEditingAddress(message)
                }

                override fun notFound(message: String) {
                    userPresenter.OnErrorEditingAddress(message)
                }

                override fun success(body: Response<Wrapper<ResponseService>>) {
                    if(CheckObjectResult(body.body()!!)){
                        userPresenter.OnSuccessEditAddress()
                    }
                    else {
                        userPresenter.OnErrorEditingAddress(body.body()!!.userMsg)
                    }
                }
            }
        )
    }
}