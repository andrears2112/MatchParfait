package com.example.matchparfait.model.entitys

data class Card (
    val cardId : String = "",
    val titular : String = "",
    val cardNumber : String = "",
    val expDate : String = "",
    val cvv : String = ""
)