package com.example.matchparfait.view.interfaces

import com.example.matchparfait.model.entitys.AddressUser
import com.example.matchparfait.model.entitys.User

interface UserView {

    fun OnSuccessGettingAddress(address: AddressUser){}

    fun OnErrorGettingAdress(message: String){}

    fun OnSuccessEditAddress(){}

    fun OnErrorEditingAddress(message: String){}

    fun OnRegisterSuccess(){}

    fun OnErrorRegisterSuccess(message: String){}
}