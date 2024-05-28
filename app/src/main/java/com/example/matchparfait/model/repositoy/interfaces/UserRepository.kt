package com.example.matchparfait.model.repositoy.interfaces

import com.example.matchparfait.model.entitys.AddressUser
import com.example.matchparfait.model.entitys.User

interface UserRepository {

    fun GetAddress(){}

    fun EditAddress(address : AddressUser){}

    fun RegisterUser(user : User){}
}