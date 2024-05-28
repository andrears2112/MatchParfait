package com.example.matchparfait.presenter.interfaces

import com.example.matchparfait.model.entitys.Card

interface CardPresenter {

    fun GetCard(){}

    fun OnSuccessGettingCard(card : Card){}

    fun OnErrorGettingCard(message: String){}

    fun EditCard(card : Card){}

    fun OnSuccessEditCard(){}

    fun OnErrorEditCard(message: String){}

    fun AddCard(card : Card){}

    fun OnSuccessAddingCard(){}

    fun OnErrorAddingCard(message: String){}
}