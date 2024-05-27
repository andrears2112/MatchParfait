package com.example.matchparfait.model.entitys

data class ShoppingCartRequest(
    val productId: String = "",
    val color : String = "",
    val cantidad : Int = 0
)

data class ShoppingCartUpdateRequest(
    val cartId : String = "",
    val productId : String = "",
    val cantidad : Int = 0,
    val color : String = "",
)

data class WishListRequest(
    val productId: String = "",
    val color : String = ""
)

data class PayRequest(
    val totalAmount : String = "",
    val cardId : String = ""
)
