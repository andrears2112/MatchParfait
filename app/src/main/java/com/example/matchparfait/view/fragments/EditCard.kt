package com.example.matchparfait.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.AddressUser
import com.example.matchparfait.model.entitys.Card
import com.example.matchparfait.presenter.CardPresenterImpl
import com.example.matchparfait.presenter.interfaces.CardPresenter
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.components.AlertDialog
import com.example.matchparfait.view.components.DatePicker
import com.example.matchparfait.view.components.Loading
import com.example.matchparfait.view.interfaces.CardView

class EditCard : Fragment(), View.OnClickListener, CardView {

    private lateinit var headline: EditText
    private lateinit var number: EditText
    private lateinit var cvv: EditText
    private lateinit var datePicker: DatePicker
    private lateinit var saveBtn: Button
    private lateinit var cancelBtn: TextView
    private lateinit var cardPresenter : CardPresenter
    private lateinit var loadingServices : Loading
    private lateinit var alertDialog : AlertDialog
    private lateinit var cardInitial: Card

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.cardPresenter = CardPresenterImpl(this, this.requireContext())
        this.alertDialog = AlertDialog(this.requireContext()){
            if(Helpers.getSourceEdit() == "profile"){
                findNavController().navigate(R.id.init )
            }
            else {
                findNavController().navigate(R.id.payment )
            }
        }
        this.loadingServices = Loading(this.requireContext())
        headline = view.findViewById(R.id.headline)
        number = view.findViewById(R.id.number)
        cvv = view.findViewById(R.id.cvv)
        datePicker = view.findViewById(R.id.date_picker)
        saveBtn = view.findViewById(R.id.save_btn)
        cancelBtn = view.findViewById(R.id.cancelBtn)

        this.cardInitial = Helpers.getCard()

        if(cardInitial.cardNumber != ""){
            this.headline.setText(cardInitial.titular)
            this.number.setText(cardInitial.cardNumber)
            this.cvv.setText(cardInitial.cvv)
            this.datePicker.setSelectedMonth(cardInitial.expDate.substring(0, 2).toInt())
            this.datePicker.setSelectedYear(2000 + cardInitial.expDate.substring(cardInitial.expDate.length - 2).toInt())
        }

        this.saveBtn.setOnClickListener(this)
        this.cancelBtn.setOnClickListener(this)
    }

    fun validate() : Boolean {
        val editTexts = listOf(headline, number, cvv)
        var isValid = true

        for (editText in editTexts) {
            if (editText.text.toString().trim().isEmpty()) {
                editText.error = "${editText.hint} no puede estar vacío"
                editText.requestFocus()
                isValid = false
                break
            }
        }

        if (number.text.toString().trim().length != 12) {
            number.error = "Deben ser 12 números de la tarjeta"
            number.requestFocus()
            isValid = false
        }

        if (cvv.text.toString().trim().length != 3) {
            cvv.error = "El CVV deben ser 3 números"
            cvv.requestFocus()
            isValid = false
        }

        return isValid
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == this.cancelBtn.id){
            if(Helpers.getSourceEdit() == "profile"){
                findNavController().navigate(R.id.init )
            }
            else {
                findNavController().navigate(R.id.payment )
            }
        }
        if(p0.id == this.saveBtn.id){
            if(validate()){
                this.loadingServices.show()
                var card = Card("", this.headline.text.toString(), this.number.text.toString(), this.datePicker.getSelectedMonth()+"/"+this.datePicker.getSelectedYear(), this.cvv.text.toString())

                if(this.cardInitial.cardNumber == ""){
                    this.cardPresenter.AddCard(card)
                }
                else {
                    card.cardId = cardInitial.cardId
                    this.cardPresenter.EditCard(card)
                }
            }
        }
    }

    override fun OnSuccessAddingCard() {
        this.loadingServices.dismiss()
        this.alertDialog.setImage(R.drawable.ic_star_smile)
        this.alertDialog.setMessage("Se agregó tu tarjeta")
        this.alertDialog.show()
    }

    override fun OnErrorAddingCard(message: String) {
        this.loadingServices.dismiss()
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
        Log.d("ERROR EDIT CARD", message)
    }

    override fun OnSuccessEditCard() {
        this.loadingServices.dismiss()
        this.alertDialog.setImage(R.drawable.ic_star_smile)
        this.alertDialog.setMessage("Se actualizó tu tarjeta")
        this.alertDialog.show()
    }

    override fun OnErrorEditCard(message: String) {
        this.loadingServices.dismiss()
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
        Log.d("ERROR EDIT CARD", message)
    }
}