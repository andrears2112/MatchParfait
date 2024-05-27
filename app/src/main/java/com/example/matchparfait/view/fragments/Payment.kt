package com.example.matchparfait.view.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.AddressUser
import com.example.matchparfait.model.entitys.Card
import com.example.matchparfait.model.entitys.PayRequest
import com.example.matchparfait.model.entitys.ProductShopBag
import com.example.matchparfait.presenter.CardPresenterImpl
import com.example.matchparfait.presenter.ProductsPresenterImpl
import com.example.matchparfait.presenter.UserPresenterImpl
import com.example.matchparfait.presenter.interfaces.CardPresenter
import com.example.matchparfait.presenter.interfaces.ProductsPresenter
import com.example.matchparfait.presenter.interfaces.UserPresenter
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.components.AlertDialog
import com.example.matchparfait.view.components.Loading
import com.example.matchparfait.view.interfaces.CardView
import com.example.matchparfait.view.interfaces.ProductsView
import com.example.matchparfait.view.interfaces.UserView

class Payment : Fragment(), View.OnClickListener, UserView, ProductsView, CardView{

    private lateinit var titleAddress : TextView
    private lateinit var address : TextView
    private lateinit var editAddress : TextView
    private lateinit var titleCard : TextView
    private lateinit var headline : TextView
    private lateinit var card : TextView
    private lateinit var editCard : TextView
    private lateinit var finishBtn : TextView
    private lateinit var total : TextView
    private lateinit var presenterUser : UserPresenter
    private lateinit var loadingServices : Loading
    private lateinit var alertDialog : AlertDialog
    private lateinit var alertDialogPayment : AlertDialog
    private lateinit var loading : ProgressBar
    private lateinit var completeAddress : AddressUser
    private lateinit var actualCard : Card
    private lateinit var productsPresenter: ProductsPresenter
    private lateinit var cardPresenter : CardPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.cardPresenter = CardPresenterImpl(this, this.requireContext())
        this.productsPresenter = ProductsPresenterImpl(this, this.requireContext())
        this.presenterUser = UserPresenterImpl(this, this.requireContext())
        this.alertDialog = AlertDialog(this.requireContext())
        this.loadingServices = Loading(this.requireContext())
        this.loading = view.findViewById(R.id.progressBar)
        this.titleAddress = view.findViewById(R.id.titleAddress)
        this.address = view.findViewById(R.id.address)
        this.editAddress = view.findViewById(R.id.edit_address)
        this.titleCard = view.findViewById(R.id.titleCard)
        this.headline = view.findViewById(R.id.cardHeadline)
        this.card = view.findViewById(R.id.card)
        this.editCard = view.findViewById(R.id.edit_payType)
        this.total = view.findViewById(R.id.amount)
        this.finishBtn = view.findViewById(R.id.pay_btn)

        this.alertDialogPayment = AlertDialog(this.requireContext()){
            findNavController().navigate(R.id.principal)
        }

        this.editAddress.setOnClickListener(this)
        this.editCard.setOnClickListener(this)
        this.finishBtn.setOnClickListener(this)

        this.presenterUser.GetAddress()
    }

    override fun OnSuccessGettingAddress(address: AddressUser) {
        this.completeAddress = AddressUser("México", address.state, address.municipality, address.postal_code, address.suburb, address.street, address.ext_num, address.int_num)

        if (address.int_num != ""){
            this.address.text = address.postal_code+", "+address.street+", #"+address.ext_num+", "+address.int_num+", "+address.suburb+", "+address.municipality+", "+address.state+", México."
        }
        else {
            this.address.text = address.postal_code+", "+address.street+", #"+address.ext_num+", "+address.suburb+", "+address.municipality+", "+address.state+", México."
        }

        this.cardPresenter.GetCard()
    }

    override fun OnSuccessGettingCard(card: Card) {

        this.actualCard = card
        if(card.cardId == "" ){
            this.card.visibility = View.VISIBLE
            this.card.text = "Ops, neceitas agregar una tarjeta"
            this.editCard.text = "Agregar"
        }
        else {
            this.card.visibility = View.VISIBLE
            this.headline.visibility = View.VISIBLE
            this.card.text = card.cardNumber
            this.headline.text = card.titular
            this.finishBtn.isEnabled = true
            this.finishBtn.backgroundTintList = ColorStateList.valueOf((Color.parseColor("#9B0E28")))
        }

        this.loading.visibility = View.GONE
        this.titleAddress.visibility = View.VISIBLE
        this.address.visibility = View.VISIBLE
        this.editAddress.visibility = View.VISIBLE
        this.titleCard.visibility = View.VISIBLE
        this.editCard.visibility = View.VISIBLE
        this.total.visibility = View.VISIBLE
        this.finishBtn.visibility = View.VISIBLE
        this.total.text = Helpers.getTotal()
    }

    override fun OnErrorGettingCard(message: String) {
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
        Log.d("ERROR CARD", message)
    }

    override fun OnErrorGettingAdress(message: String) {
        this.loadingServices.dismiss()
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
        Log.d("ERROR ADDRESS", message)
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == this.editAddress.id){
            Helpers.saveAddressToEdit(this.completeAddress)
            findNavController().navigate(R.id.editAddress)
        }
        if(p0.id == this.editCard.id){
            Helpers.saveCard(this.actualCard)
            findNavController().navigate(R.id.editCard)
        }
        if(p0.id == this.finishBtn.id){
            this.loadingServices.show()
            val request = PayRequest(Helpers.getTotal(), this.actualCard.cardId)
            this.productsPresenter.CompleteSale(request)
        }
    }

    override fun OnSuccessPayment() {
        this.loadingServices.dismiss()
        this.alertDialogPayment.setImage(R.drawable.ic_star_happy)
        this.alertDialogPayment.setMessage("¡Gracias por tu compra! Puedes ver el estatus de tu pedido en el apartado Historial dentro de tu perfil")
        this.alertDialogPayment.show()
    }

    override fun OnErrorPayment(message: String) {
        this.loadingServices.dismiss()
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
        Log.d("ERROR PAY", message)
    }
}