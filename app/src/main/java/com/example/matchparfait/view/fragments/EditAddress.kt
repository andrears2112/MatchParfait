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
import com.example.matchparfait.presenter.UserPresenterImpl
import com.example.matchparfait.presenter.interfaces.UserPresenter
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.components.AlertDialog
import com.example.matchparfait.view.components.Loading
import com.example.matchparfait.view.interfaces.UserView

class EditAddress : Fragment(), View.OnClickListener, UserView {

    private lateinit var state : EditText
    private lateinit var municipality : EditText
    private lateinit var suburb : EditText
    private lateinit var street : EditText
    private lateinit var num_ext : EditText
    private lateinit var num_int : EditText
    private lateinit var postal_code : EditText
    private lateinit var saveBtn : Button
    private lateinit var cancelBtn : TextView
    private lateinit var loadingServices : Loading
    private lateinit var alertDialog : AlertDialog
    private lateinit var presenterUser : UserPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.alertDialog = AlertDialog(this.requireContext()){
            findNavController().navigate(R.id.payment)
        }
        this.loadingServices = Loading(this.requireContext())
        this.presenterUser = UserPresenterImpl(this, this.requireContext())
        this.state = view.findViewById(R.id.state)
        this.municipality = view.findViewById(R.id.municipality)
        this.suburb = view.findViewById(R.id.suburb)
        this.street = view.findViewById(R.id.street)
        this.num_ext = view.findViewById(R.id.num_ext)
        this.num_int = view.findViewById(R.id.num_int)
        this.postal_code = view.findViewById(R.id.postalCode)
        this.saveBtn = view.findViewById(R.id.save_btn)
        this.cancelBtn = view.findViewById(R.id.cancelBtn)

        this.saveBtn.setOnClickListener(this)
        this.cancelBtn.setOnClickListener(this)

        val address = Helpers.getAddressToEdit()

        this.state.setText(address.state)
        this.municipality.setText(address.municipality)
        this.suburb.setText(address.suburb)
        this.street.setText(address.street)
        this.num_ext.setText(address.ext_num)
        this.num_int.setText(address.int_num)
        this.postal_code.setText(address.postal_code)
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == this.cancelBtn.id){
            findNavController().navigate(R.id.payment )
        }
        if(p0.id == this.saveBtn.id){
            this.loadingServices.show()
            val address = AddressUser("México", this.state.text.toString(), this.municipality.text.toString(), this.postal_code.text.toString(), this.suburb.text.toString(), this.street.text.toString(), this.num_ext.text.toString(), this.num_int.text.toString())
            this.presenterUser.EditAddress(address)
        }
    }

    override fun OnSuccessEditAddress() {
        this.loadingServices.dismiss()
        this.alertDialog.setImage(R.drawable.ic_star_smile)
        this.alertDialog.setMessage("Se actualizó tu dirección de envío")
        this.alertDialog.show()
    }

    override fun OnErrorEditingAddress(message: String) {
        this.loadingServices.dismiss()
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
        Log.d("ERROR EDIT ADDRESS", message)
    }

}