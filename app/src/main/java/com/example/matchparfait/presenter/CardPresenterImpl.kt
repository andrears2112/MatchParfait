package com.example.matchparfait.presenter

import android.content.Context
import com.example.matchparfait.model.entitys.Card
import com.example.matchparfait.model.repositoy.CardRepositoryImpl
import com.example.matchparfait.model.repositoy.interfaces.CardRepository
import com.example.matchparfait.presenter.interfaces.CardPresenter
import com.example.matchparfait.view.interfaces.CardView

class CardPresenterImpl(view : CardView, context: Context) : CardPresenter {

    private var cardRepo : CardRepository
    private var cardView : CardView
    private var context : Context

    init {
        this.cardRepo = CardRepositoryImpl(this, context)
        this.cardView = view
        this.context = context
    }

    override fun GetCard() {
        this.cardRepo.GetCard()
    }

    override fun OnSuccessGettingCard(card : Card){
        this.cardView.OnSuccessGettingCard(card)
    }

    override fun OnErrorGettingCard(message: String){
        this.cardView.OnErrorGettingCard(message)
    }

    override fun EditCard(card : Card){
        this.cardRepo.EditCard(card)
    }

    override fun OnSuccessEditCard(){
        this.cardView.OnSuccessEditCard()
    }

    override fun OnErrorEditCard(message: String){
        this.cardView.OnErrorEditCard(message)
    }

    override fun AddCard(card : Card){
        this.cardRepo.AddCard(card)
    }

    override fun OnSuccessAddingCard(){
        this.cardView.OnSuccessAddingCard()
    }

    override fun OnErrorAddingCard(message: String){
        this.cardView.OnErrorAddingCard(message)
    }
}