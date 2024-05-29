package com.example.matchparfait.model.repositoy

import android.content.Context
import com.example.matchparfait.model.dataSources.AppServiceClient
import com.example.matchparfait.model.dataSources.CheckObjectResult
import com.example.matchparfait.model.dataSources.ResultInterface
import com.example.matchparfait.model.dataSources.ServiceResponse
import com.example.matchparfait.model.dataSources.Wrapper
import com.example.matchparfait.model.entitys.AddressUser
import com.example.matchparfait.model.entitys.HistoryUser
import com.example.matchparfait.model.entitys.ResponseService
import com.example.matchparfait.model.entitys.User
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
    private var responseHistory : ServiceResponse<Wrapper<HistoryUser>, HistoryUser> = ServiceResponse<Wrapper<HistoryUser>, HistoryUser>()


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

    override fun RegisterUser(user: User) {
        this.responseService.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(UserServices::class.java)!!.RegisterUser(user),
            object : ResultInterface<Wrapper<ResponseService>> {
                override fun failWithError(message: String) {
                    userPresenter.OnErrorRegisterSuccess(message)
                }

                override fun notFound(message: String) {
                    userPresenter.OnErrorRegisterSuccess(message)
                }

                override fun success(body: Response<Wrapper<ResponseService>>) {
                    if(CheckObjectResult(body.body()!!)){
                        userPresenter.OnRegisterSuccess()
                    }
                    else {
                        userPresenter.OnErrorRegisterSuccess(body.body()!!.userMsg)
                    }
                }
            }
        )
    }

    override fun GetHistory() {
        this.responseHistory.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(UserServices::class.java)!!.GetHistory(Helpers.getToken()),
            object : ResultInterface<Wrapper<HistoryUser>> {
                override fun failWithError(message: String) {
                    userPresenter.OnErrorGettingHistory(message)
                }

                override fun notFound(message: String) {
                    userPresenter.OnErrorGettingHistory(message)
                }

                override fun success(body: Response<Wrapper<HistoryUser>>) {
                    if(body.body()!!.userMsg == ""){
                        userPresenter.OnSuccessGetingHistory(body.body()!!.data)
                    }
                    else {
                        userPresenter.OnErrorGettingHistory(body.body()!!.userMsg)
                    }
                }
            }
        )
    }
}