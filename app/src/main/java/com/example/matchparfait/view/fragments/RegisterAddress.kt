package com.example.matchparfait.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matchparfait.R
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.activities.Login

class RegisterAddress : Fragment(), View.OnClickListener{

    private lateinit var state : EditText
    private lateinit var municipality : EditText
    private lateinit var suburb : EditText
    private lateinit var street : EditText
    private lateinit var num_ext : EditText
    private lateinit var num_int : EditText
    private lateinit var postal_code : EditText
    private lateinit var checkBtn : CheckBox
    private lateinit var continueBtn : Button
    private lateinit var registerBtn : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.state = view.findViewById(R.id.state)
        this.municipality = view.findViewById(R.id.municipality)
        this.suburb = view.findViewById(R.id.suburb)
        this.street = view.findViewById(R.id.street)
        this.num_ext = view.findViewById(R.id.num_ext)
        this.num_int = view.findViewById(R.id.num_int)
        this.postal_code = view.findViewById(R.id.postalCode)
        this.checkBtn = view.findViewById(R.id.checkTerms)
        this.continueBtn = view.findViewById(R.id.continue_btn)
        this.registerBtn = view.findViewById(R.id.btn_register)

        this.checkBtn.setOnClickListener(this)
        this.continueBtn.setOnClickListener(this)
        this.registerBtn.setOnClickListener(this)
    }

    fun validateAddressInputs(): Boolean {
        val editTexts = listOf(state, municipality, suburb, street, num_ext, postal_code)
        var isValid = true

        for (editText in editTexts) {
            if (editText.text.toString().trim().isEmpty()) {
                editText.error = "${editText.hint} no puede estar vacío"
                editText.requestFocus()
                isValid = false
                break
            }
        }

        if (postal_code.text.toString().trim().length != 5) {
            postal_code.error = "El código postal debe tener 5 caracteres"
            postal_code.requestFocus()
            isValid = false
        }

        if (!checkBtn.isChecked) {
            Toast.makeText(this.requireContext(), "Debe aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }


    override fun onClick(p0: View?) {
        if(p0!!.id == this.continueBtn.id){
            if(validateAddressInputs()){
                val user = Helpers.getUserToRegister()
                user.state = this.state.text.toString()
                user.municipality = this.municipality.text.toString()
                user.suburb = this.suburb.text.toString()
                user.street = this.street.text.toString()
                user.ext_num = this.num_ext.text.toString()
                user.int_num = this.num_int.text.toString()
                user.postal_code = this.postal_code.text.toString()

                Helpers.saveUserToRegister(user)

                findNavController().navigate(R.id.skin_info)
            }
        }
        if(p0.id == this.registerBtn.id){
            startActivity(Intent(this.requireContext(), Login::class.java))
        }
    }
}