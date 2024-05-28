package com.example.matchparfait.model.repositoy.interfaces

import com.example.matchparfait.model.entitys.Card

interface CardRepository {

    fun GetCard(){}

    fun EditCard(card : Card){}

    fun AddCard(card : Card){}
}