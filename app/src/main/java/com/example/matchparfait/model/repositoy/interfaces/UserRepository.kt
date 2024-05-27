package com.example.matchparfait.model.repositoy.interfaces

import com.example.matchparfait.model.entitys.AddressUser

interface UserRepository {

    fun GetAddress(){}

    fun EditAddress(address : AddressUser){}
}