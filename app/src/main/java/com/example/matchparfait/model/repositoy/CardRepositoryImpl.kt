package com.example.matchparfait.model.repositoy

import android.content.Context
import com.example.matchparfait.model.dataSources.AppServiceClient
import com.example.matchparfait.model.dataSources.CheckObjectResult
import com.example.matchparfait.model.dataSources.ResultInterface
import com.example.matchparfait.model.dataSources.ServiceResponse
import com.example.matchparfait.model.dataSources.Wrapper
import com.example.matchparfait.model.entitys.Card
import com.example.matchparfait.model.entitys.ResponseService
import com.example.matchparfait.model.remote.CardServices
import com.example.matchparfait.model.remote.ProductsServices
import com.example.matchparfait.model.repositoy.interfaces.CardRepository
import com.example.matchparfait.presenter.interfaces.CardPresenter
import com.example.matchparfait.utils.Helpers
import retrofit2.Response

class CardRepositoryImpl(cardPresenter: CardPresenter, context: Context) : CardRepository {

    private var cardPresenter : CardPresenter
    private var appServiceClient : AppServiceClient? = null
    private var applicationContext = context
    private var responseService : ServiceResponse<Wrapper<ResponseService>, ResponseService> = ServiceResponse<Wrapper<ResponseService>, ResponseService>()
    private var responseCard : ServiceResponse<Wrapper<Card>, Card> = ServiceResponse<Wrapper<Card>, Card>()

    init {
        this.cardPresenter = cardPresenter
        if(this.applicationContext != null){
            this.appServiceClient = AppServiceClient(this.applicationContext)
        }
    }

    override fun GetCard() {
        this.responseCard.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(CardServices::class.java)!!.GetCard(Helpers.getToken()),
            object : ResultInterface<Wrapper<Card>> {
                override fun failWithError(message: String) {
                    cardPresenter.OnErrorGettingCard(message)
                }

                override fun notFound(message: String) {
                    cardPresenter.OnErrorGettingCard(message)
                }

                override fun success(body: Response<Wrapper<Card>>) {
                    if(body.body()!!.userMsg.isEmpty()){
                        if(body.body()!!.data.isEmpty()){
                            cardPresenter.OnSuccessGettingCard(Card())
                        }
                        else {
                            cardPresenter.OnSuccessGettingCard(body.body()!!.data.first())
                        }
                    }
                    else {
                        cardPresenter.OnErrorGettingCard(body.body()!!.userMsg)
                    }
                }
            }
        )
    }

    override fun EditCard(card: Card) {
        this.responseService.GetRequestForObject(
            this.appServiceClient?.GetDefaultConnectionWithServices()?.
            create(CardServices::class.java)!!.EditCard(Helpers.getToken(), card),
            object : ResultInterface<Wrapper<ResponseService>> {
                override fun failWithError(message: String) {
                    cardPresenter.OnErrorEditCard(message)
                }

                override fun notFound(message: String) {
                    cardPresenter.OnErrorEditCard(message)
                }

                override fun success(body: Response<Wrapper<ResponseService>>) {
                    if(CheckObjectResult(body.body()!!)){
                        cardPresenter.OnSuccessEditCard()
                    }
                    else {
                        cardPresenter.OnErrorEditCard(body.body()!!.userMsg)
                    }
                }
            }
        )
    }

}