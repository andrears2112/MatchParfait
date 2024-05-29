package com.example.matchparfait.model.entitys

data class Card (
    var cardId : String = "",
    var titular : String = "",
    var cardNumber : String = "",
    var expDate : String = "",
    var cvv : String = ""
)