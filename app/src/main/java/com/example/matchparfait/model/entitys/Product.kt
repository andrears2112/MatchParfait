package com.example.matchparfait.model.entitys

import android.provider.ContactsContract.CommonDataKinds.Phone
import android.service.autofill.FieldClassification

data class Product(
    var productId : String = "",
    var productName : String = "",
    var productBrand : String = "",
    var price : Int = 0,
    var quantity : Int = 0,
    var stars : Int = 0,
    var photo: String = "",
    var description : String = "",
    var ingredients : String = "",
    var colors : String = "",
    var photos : String = "",
    var type : String = "",
    var notes : String = "",
    var addition_date : String = "",
    var classification: String = ""
)

data class ProductWishList(
    var wish_listId : String = "",
    var productId : String = "",
    var productName : String = "",
    var productBrand : String = "",
    var price : Int = 0,
    var photo: String = "",
    var color : String = "",
    var classification: String = ""
)
