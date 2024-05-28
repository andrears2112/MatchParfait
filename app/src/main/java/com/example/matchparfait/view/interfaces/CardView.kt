package com.example.matchparfait.view.interfaces

import com.example.matchparfait.model.entitys.Card

interface CardView {

    fun OnSuccessGettingCard(card : Card){}

    fun OnErrorGettingCard(message: String){}

    fun OnSuccessEditCard(){}

    fun OnErrorEditCard(message: String){}

    fun OnSuccessAddingCard(){}

    fun OnErrorAddingCard(message: String){}
}