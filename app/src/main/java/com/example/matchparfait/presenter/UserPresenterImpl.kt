package com.example.matchparfait.presenter

import android.content.Context
import com.example.matchparfait.model.entitys.AddressUser
import com.example.matchparfait.model.repositoy.UserRepositoryImpl
import com.example.matchparfait.model.repositoy.interfaces.UserRepository
import com.example.matchparfait.presenter.interfaces.UserPresenter
import com.example.matchparfait.view.interfaces.UserView

class UserPresenterImpl(view : UserView, context: Context) : UserPresenter {

    private var userRepo : UserRepository
    private var userView : UserView
    private var context : Context

    init {
        this.userRepo = UserRepositoryImpl(this, context)
        this.userView = view
        this.context = context
    }

    override fun GetAddress(){
        this.userRepo.GetAddress()
    }

    override fun OnSuccessGettingAddress(address: AddressUser){
        this.userView.OnSuccessGettingAddress(address)
    }

    override fun OnErrorGettingAdress(message: String){
        this.userView.OnErrorGettingAdress(message)
    }

    override fun EditAddress(address : AddressUser){
        this.userRepo.EditAddress(address)
    }

    override fun OnSuccessEditAddress(){
        this.userView.OnSuccessEditAddress()
    }

    override fun OnErrorEditingAddress(message: String){
        this.userView.OnErrorEditingAddress(message)
    }
}