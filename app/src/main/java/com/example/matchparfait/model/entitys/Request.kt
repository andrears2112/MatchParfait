package com.example.matchparfait.model.entitys

data class ShoppingCartRequest(
    val productId: String = "",
    val color : String = "",
    val cantidad : Int = 0
)

data class WishListRequest(
    val productId: String = "",
    val color : String = ""
)
