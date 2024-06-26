package com.example.matchparfait.presenter.interfaces

import com.example.matchparfait.model.entitys.AddressUser
import com.example.matchparfait.model.entitys.HistoryUser
import com.example.matchparfait.model.entitys.User

interface UserPresenter {

    fun GetAddress(){}

    fun OnSuccessGettingAddress(address: AddressUser){}

    fun OnErrorGettingAdress(message: String){}

    fun EditAddress(address : AddressUser){}

    fun OnSuccessEditAddress(){}

    fun OnErrorEditingAddress(message: String){}

    fun RegisterUser(user : User){}

    fun OnRegisterSuccess(){}

    fun OnErrorRegisterSuccess(message: String){}

    fun GetHistory(){}

    fun OnSuccessGetingHistory(history : List<HistoryUser>){}

    fun OnErrorGettingHistory(message: String){}
}