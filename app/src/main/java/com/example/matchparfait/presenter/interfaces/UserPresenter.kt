package com.example.matchparfait.presenter.interfaces

import com.example.matchparfait.DisableMessage
import com.example.matchparfait.model.entitys.AddressUser

interface UserPresenter {

    fun GetAddress(){}

    fun OnSuccessGettingAddress(address: AddressUser){}

    fun OnErrorGettingAdress(message: String){}

    fun EditAddress(address : AddressUser){}

    fun OnSuccessEditAddress(){}

    fun OnErrorEditingAddress(message: String){}
}