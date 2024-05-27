package com.example.matchparfait.view.interfaces

import com.example.matchparfait.model.entitys.AddressUser

interface UserView {

    fun OnSuccessGettingAddress(address: AddressUser){}

    fun OnErrorGettingAdress(message: String){}

    fun OnSuccessEditAddress(){}

    fun OnErrorEditingAddress(message: String){}
}