package com.example.matchparfait.model.repositoy

import android.content.Context
import com.example.matchparfait.model.dataSources.AppServiceClient
import com.example.matchparfait.model.dataSources.CheckObjectResult
import com.example.matchparfait.model.dataSources.ResultInterface
import com.example.matchparfait.model.dataSources.ServiceResponse
import com.example.matchparfait.model.dataSources.Wrapper
import com.example.matchparfait.model.entitys.User
import com.example.matchparfait.model.entitys.UserFactory
import com.example.matchparfait.model.remote.LoginServices
import com.example.matchparfait.model.repositoy.interfaces.LoginRepository
import com.example.matchparfait.presenter.interfaces.LoginPresenter
import com.example.matchparfait.utils.Helpers
import retrofit2.Response

class LoginRepositoryImpl(logPresenter : LoginPresenter, context: Context?) : LoginRepository {

    private var logPresenter: LoginPresenter
    private var appServiceClient : AppServiceClient? = null
    private var applicationContext = context
    private var responseLogin : ServiceResponse<Wrapper<User>, User> = ServiceResponse<Wrapper<User>, User>()

    init{
        this.logPresenter = logPresenter
        if (this.applicationContext != null) {
            this.appServiceClient = AppServiceClient(this.applicationContext!!)
        }
    }

    override fun Login(mail : String, password : String) {
        this.responseLogin.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(LoginServices::class.java)!!.Login(UserFactory.userForLogin(mail, password)),
            object: ResultInterface<Wrapper<User>> {
                override fun failWithError(message: String) {
                    logPresenter.OnLoginError(message)
                }

                override fun notFound(message: String) {
                    logPresenter.OnLoginError(message)
                }

                override fun success(body: Response<Wrapper<User>>) {
                    if(CheckObjectResult(body.body()!!)){
                        Helpers.startSession(body.body()!!.tokenSession, body.body()!!.data.first())
                        logPresenter.OnLoginSuccess(body.body()!!.data.first())
                    }
                    else {
                        logPresenter.OnLoginError(body.body()!!.userMsg)
                    }
                }

                override fun notFoundUser(body: Response<Wrapper<User>>) {
                    logPresenter.OnLoginError("Usuario o contraseña incorrecta")
                }
            })
    }
}