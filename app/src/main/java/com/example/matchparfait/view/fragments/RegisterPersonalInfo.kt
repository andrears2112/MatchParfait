package com.example.matchparfait.view.fragments

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.User
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.activities.Login
import com.example.matchparfait.view.components.DatePickerBirthday

class RegisterPersonalInfo : Fragment(), View.OnClickListener {

    private lateinit var btnRegister: TextView
    private lateinit var mail: EditText
    private lateinit var name: EditText
    private lateinit var lastName: EditText
    private lateinit var lastMotherName: EditText
    private lateinit var password: EditText
    private lateinit var confirm: EditText
    private lateinit var num: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButtonM: RadioButton
    private lateinit var radioButtonH: RadioButton
    private lateinit var radioButtonNE: RadioButton
    private lateinit var continueBtn: Button
    private lateinit var birthDate: DatePickerBirthday
    private lateinit var genderSelected : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_personal_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mail = view.findViewById(R.id.mail)
        name = view.findViewById(R.id.name)
        lastName = view.findViewById(R.id.last_name)
        lastMotherName = view.findViewById(R.id.last_mothername)
        password = view.findViewById(R.id.password)
        confirm = view.findViewById(R.id.confirm)
        num = view.findViewById(R.id.num)
        birthDate = view.findViewById(R.id.birth_date)
        radioGroup = view.findViewById(R.id.radioGroup)
        radioButtonM = view.findViewById(R.id.radioButtonYes)
        radioButtonH = view.findViewById(R.id.radioButtonNo)
        radioButtonNE = view.findViewById(R.id.radioButtonNE)
        continueBtn = view.findViewById(R.id.continue_btn)
        btnRegister = view.findViewById(R.id.btn_register)

        this.radioButtonM.setOnClickListener(this)
        this.radioButtonH.setOnClickListener(this)
        this.radioButtonNE.setOnClickListener(this)
        this.continueBtn.setOnClickListener(this)
        this.btnRegister.setOnClickListener(this)
    }

    fun validateInputs(): Boolean {
        val editTexts = listOf(mail, name, lastName, num)
        var isValid = true

        for (editText in editTexts) {
            if (editText.text.toString().trim().isEmpty()) {
                editText.error = "${editText.hint} no puede estar vacío"
                editText.requestFocus()
                isValid = false
                break
            }
        }

        if (num.text.toString().trim().length != 10) {
            num.error = "El número debe tener 10 dígitos"
            num.requestFocus()
            isValid = false
        }

        if (password.text.toString().trim().isEmpty()) {
            password.error = "La contraseña no puede estar vacía"
            password.requestFocus()
            isValid = false
        } else if (password.text.toString().length < 8) {
            password.error = "La contraseña debe tener al menos 8 caracteres"
            password.requestFocus()
            isValid = false
        } else if (confirm.text.toString().trim().isEmpty()) {
            confirm.error = "La confirmación de la contraseña no puede estar vacía"
            confirm.requestFocus()
            isValid = false
        } else if (password.text.toString() != confirm.text.toString()) {
            confirm.error = "Las contraseñas no coinciden"
            confirm.requestFocus()
            isValid = false
        }

        if (radioGroup.checkedRadioButtonId == -1) {
            Toast.makeText(this.requireContext(), "Por favor selecciona una opción", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == this.continueBtn.id){
            if(validateInputs()){
                val user = User()
                user.email = this.mail.text.toString()
                user.name = this.name.text.toString()
                user.last_name1 = this.lastName.text.toString()
                user.last_name2 = this.lastMotherName.text.toString()
                user.password = this.password.text.toString()
                user.phone_number = this.num.text.toString()
                user.gender = this.genderSelected
                user.date_of_birth = this.birthDate.getSelectedYear().toString()+"-"+this.birthDate.getSelectedMonth().toString()+"-"+this.birthDate.getSelectedDay().toString()

                Helpers.saveUserToRegister(user)
                findNavController().navigate(R.id.address_info)
            }
        }
        if(p0.id == this.btnRegister.id){
            startActivity(Intent(this.requireContext(), Login::class.java))
        }
        if(p0.id == this.radioButtonM.id){
            this.genderSelected = "M"
        }
        if(p0.id == this.radioButtonH.id){
            this.genderSelected = "H"
        }
        if(p0.id == this.radioButtonNE.id){
            this.genderSelected = "NE"
        }
    }
}