package com.example.matchparfait.view.fragments

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.AddressUser
import com.example.matchparfait.model.entitys.Card
import com.example.matchparfait.presenter.CardPresenterImpl
import com.example.matchparfait.presenter.UserPresenterImpl
import com.example.matchparfait.presenter.interfaces.CardPresenter
import com.example.matchparfait.presenter.interfaces.UserPresenter
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.utils.Preferences
import com.example.matchparfait.view.activities.Login
import com.example.matchparfait.view.components.AlertDialog
import com.example.matchparfait.view.components.Loading
import com.example.matchparfait.view.interfaces.CardView
import com.example.matchparfait.view.interfaces.UserView

class ProfileInit : Fragment(), View.OnClickListener, UserView, CardView {

    private lateinit var name : TextView
    private lateinit var title_mail : TextView
    private lateinit var mail : TextView
    private lateinit var title_phone : TextView
    private lateinit var phone : TextView
    private lateinit var titleAddress : TextView
    private lateinit var address : TextView
    private lateinit var editAddress : TextView
    private lateinit var titleCard : TextView
    private lateinit var headline : TextView
    private lateinit var card : TextView
    private lateinit var editCard : TextView
    private lateinit var presenterUser : UserPresenter
    private lateinit var cardPresenter : CardPresenter
    private lateinit var loadingServices : Loading
    private lateinit var loading : ProgressBar
    private lateinit var completeAddress : AddressUser
    private lateinit var alertDialog : AlertDialog
    private lateinit var actualCard : Card
    private lateinit var historyBtn : Button
    private lateinit var logoutBtn : Button
    private lateinit var preferences: Preferences<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = Preferences(this.requireContext())
        this.cardPresenter = CardPresenterImpl(this, this.requireContext())
        this.presenterUser = UserPresenterImpl(this, this.requireContext())
        this.loadingServices = Loading(this.requireContext())
        this.alertDialog = AlertDialog(this.requireContext())
        this.loading = view.findViewById(R.id.progressBar)
        this.name = view.findViewById(R.id.name)
        this.title_mail = view.findViewById(R.id.title_mail)
        this.mail = view.findViewById(R.id.mail)
        this.title_phone = view.findViewById(R.id.title_phone)
        this.phone = view.findViewById(R.id.phone)
        this.titleAddress = view.findViewById(R.id.titleAddress)
        this.address = view.findViewById(R.id.address)
        this.editAddress = view.findViewById(R.id.edit_address)
        this.titleCard = view.findViewById(R.id.titleCard)
        this.headline = view.findViewById(R.id.cardHeadline)
        this.card = view.findViewById(R.id.card)
        this.editCard = view.findViewById(R.id.edit_payType)
        this.historyBtn = view.findViewById(R.id.history_btn)
        this.logoutBtn = view.findViewById(R.id.btn_logout)

        this.editAddress.setOnClickListener(this)
        this.editCard.setOnClickListener(this)
        this.historyBtn.setOnClickListener(this)
        this.logoutBtn.setOnClickListener(this)

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

    override fun OnErrorGettingAdress(message: String) {
        this.loadingServices.dismiss()
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
        Log.d("ERROR ADDRESS", message)
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
        }

        this.titleCard.visibility = View.VISIBLE
        this.editCard.visibility = View.VISIBLE
        this.loading.visibility = View.GONE
        this.name.visibility = View.VISIBLE
        this.title_mail.visibility = View.VISIBLE
        this.mail.visibility = View.VISIBLE
        this.title_phone.visibility = View.VISIBLE
        this.phone.visibility = View.VISIBLE
        this.titleAddress.visibility = View.VISIBLE
        this.address.visibility = View.VISIBLE
        this.editAddress.visibility = View.VISIBLE

        this.name.text = Helpers.getUser().name+" "+Helpers.getUser().last_name1+" "+Helpers.getUser().last_name2
        this.mail.text = Helpers.getUser().email
        this.phone.text = Helpers.getUser().phone_number
    }

    override fun OnErrorGettingCard(message: String) {
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
        Log.d("ERROR CARD", message)
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == this.editAddress.id){
            Helpers.setSourceEdit("profile")
            Helpers.saveAddressToEdit(this.completeAddress)
            findNavController().navigate(R.id.editAddress_profile)
        }
        if(p0.id == this.editCard.id){
            Helpers.setSourceEdit("profile")
            Helpers.saveCard(this.actualCard)
            findNavController().navigate(R.id.edit_payType)
        }
        if(p0.id == this.historyBtn.id){
            findNavController().navigate(R.id.history)
        }
        if(p0.id == this.logoutBtn.id){
            this.preferences.RemovePreference("mail")
            this.preferences.RemovePreference("password")
            Helpers.destroySession()
            startActivity(Intent(this.requireContext(), Login::class.java))
        }
    }
}