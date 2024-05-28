package com.example.matchparfait.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.User
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.activities.Login
import com.example.matchparfait.view.components.AlertDialog
import com.google.android.material.chip.Chip

class RegisterSkinType : Fragment(), View.OnClickListener {

    private lateinit var chipNormal: Chip
    private lateinit var chipSensible: Chip
    private lateinit var chipDry: Chip
    private lateinit var chipOild: Chip
    private lateinit var chipMix: Chip
    private lateinit var chipShineEndDay: Chip
    private lateinit var chipShineMidle: Chip
    private lateinit var chipWithoutShine: Chip
    private lateinit var chipShineT: Chip
    private lateinit var chipAtopic: Chip
    private lateinit var chipContact: Chip
    private lateinit var chipSeborreic: Chip
    private lateinit var chipTighness: Chip
    private lateinit var chipRosacea: Chip
    private lateinit var chipPeeling: Chip
    private lateinit var chipHives: Chip
    private lateinit var chipAcne: Chip
    private lateinit var chipEnlargedPores: Chip
    private lateinit var chipSunSpots: Chip
    private lateinit var chipCloth: Chip
    private lateinit var chipBlackheads: Chip
    private lateinit var chipRoughness: Chip
    private lateinit var continueBtn : Button
    private lateinit var registerBtn : TextView
    private lateinit var alertDialog : AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_skin_type, container, false)
    }

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)

        this.alertDialog = AlertDialog(this.requireContext())
        chipNormal = rootView.findViewById(R.id.chip_normal)
        chipSensible = rootView.findViewById(R.id.chip_sensible)
        chipDry = rootView.findViewById(R.id.chip_dry)
        chipOild = rootView.findViewById(R.id.chip_oild)
        chipMix = rootView.findViewById(R.id.chip_mix)
        chipShineEndDay = rootView.findViewById(R.id.shine_end_day)
        chipShineMidle = rootView.findViewById(R.id.shine_midle_day)
        chipWithoutShine = rootView.findViewById(R.id.without_shine)
        chipShineT = rootView.findViewById(R.id.shine_t)
        chipAtopic = rootView.findViewById(R.id.atopic)
        chipContact = rootView.findViewById(R.id.contact)
        chipSeborreic = rootView.findViewById(R.id.seborreic)
        chipTighness = rootView.findViewById(R.id.tighness)
        chipRosacea = rootView.findViewById(R.id.rosacea)
        chipPeeling = rootView.findViewById(R.id.peeling)
        chipHives = rootView.findViewById(R.id.hives)
        chipAcne = rootView.findViewById(R.id.acne)
        chipEnlargedPores = rootView.findViewById(R.id.enlarged_pores)
        chipSunSpots = rootView.findViewById(R.id.sun_spots)
        chipCloth = rootView.findViewById(R.id.cloth)
        chipBlackheads = rootView.findViewById(R.id.blackheads)
        chipRoughness = rootView.findViewById(R.id.roughness)
        continueBtn = rootView.findViewById(R.id.continue_btn)
        registerBtn = rootView.findViewById(R.id.btn_register)

        this.continueBtn.setOnClickListener(this)
        this.registerBtn.setOnClickListener(this)
    }

    fun validate() : Boolean{
        val texture = chipNormal.isChecked ||
                chipSensible.isChecked ||
                chipDry.isChecked ||
                chipOild.isChecked ||
                chipMix.isChecked

        val shine = chipShineEndDay.isChecked ||
                chipShineMidle.isChecked ||
                chipWithoutShine.isChecked ||
                chipShineT.isChecked

        return texture && shine
    }

    fun setUserSkinType(user : User) : User {
        var userSkin = user
        val shineChipValue: Int = when {
                chipShineEndDay.isChecked -> 1
                chipShineMidle.isChecked -> 2
                chipWithoutShine.isChecked -> 3
                chipShineT.isChecked -> 4
                else -> 0
            }

        val textureChipValue : Int = when {
            chipNormal.isChecked -> 1
            chipSensible.isChecked -> 2
            chipDry.isChecked -> 3
            chipOild.isChecked -> 4
            chipMix.isChecked -> 5
            else -> 0
        }

        val dermatitisChipValue : Int = when {
            chipAtopic.isChecked -> 1
            chipContact.isChecked -> 2
            chipSeborreic.isChecked -> 3
            else -> 0
        }

        userSkin.texture = textureChipValue
        userSkin.dermatitis = dermatitisChipValue
        userSkin.shine = shineChipValue
        userSkin.tightness = if(this.chipTighness.isChecked) 1 else 0
        userSkin.rosacea = if(this.chipRosacea.isChecked) 1 else 0
        userSkin.peeling = if(this.chipPeeling.isChecked) 1 else 0
        userSkin.hives = if(this.chipHives.isChecked) 1 else 0
        userSkin.acne = if(this.chipAcne.isChecked) 1 else 0
        userSkin.enlarged_pores = if(this.chipEnlargedPores.isChecked) 1 else 0
        userSkin.sun_spots = if(this.chipSunSpots.isChecked) 1 else 0
        userSkin.cloth = if(this.chipCloth.isChecked) 1 else 0
        userSkin.blackheads = if(this.chipBlackheads.isChecked) 1 else 0
        userSkin.roughness = if(this.chipRoughness.isChecked) 1 else 0

        return userSkin
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == this.continueBtn.id){
            if(validate()){
                var user = Helpers.getUserToRegister()
                user = setUserSkinType(user)

                Helpers.saveUserToRegister(user)
                findNavController().navigate(R.id.skin_tone)
            }
            else{
                this.alertDialog.setImage(R.drawable.ic_star_worry)
                this.alertDialog.setMessage("Debes seleccionar una opcion de los apartados obligatorios")
                this.alertDialog.show()
            }
        }
        if(p0.id == this.registerBtn.id){
            startActivity(Intent(this.requireContext(), Login::class.java))
        }
    }
}