package com.example.matchparfait.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.matchparfait.R
import com.example.matchparfait.presenter.UserPresenterImpl
import com.example.matchparfait.presenter.interfaces.UserPresenter
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.activities.Login
import com.example.matchparfait.view.components.AlertDialog
import com.example.matchparfait.view.components.Loading
import com.example.matchparfait.view.interfaces.UserView

class RegisterSkinTone : Fragment(), View.OnClickListener, SeekBar.OnSeekBarChangeListener, UserView {

    private lateinit var img1 : ImageView
    private lateinit var img2 : ImageView
    private lateinit var img3 : ImageView
    private lateinit var img4 : ImageView
    private lateinit var img5 : ImageView
    private lateinit var img6 : ImageView
    private lateinit var seekBar : SeekBar
    private lateinit var tone : TextView
    private lateinit var finishBtn : Button
    private lateinit var registerBtn : TextView
    private lateinit var presenterUser : UserPresenter
    private lateinit var loadingServices : Loading
    private lateinit var alertDialog : AlertDialog
    private lateinit var alertDialogResult : AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_skin_tone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.presenterUser = UserPresenterImpl(this, this.requireContext())
        this.alertDialog = AlertDialog(this.requireContext())
        this.alertDialogResult = AlertDialog(this.requireContext()){
            startActivity(Intent(this.requireContext(), Login::class.java))
        }
        this.loadingServices = Loading(this.requireContext())
        this.img1 = view.findViewById(R.id.img_1)
        this.img2 = view.findViewById(R.id.img_2)
        this.img3 = view.findViewById(R.id.img_3)
        this.img4 = view.findViewById(R.id.img_4)
        this.img5 = view.findViewById(R.id.img_5)
        this.img6 = view.findViewById(R.id.img_6)
        this.tone = view.findViewById(R.id.tone)
        this.seekBar = view.findViewById(R.id.seekBar)
        this.finishBtn = view.findViewById(R.id.finish_btn)
        this.registerBtn = view.findViewById(R.id.btn_register)

        this.seekBar.max = 5
        this.seekBar.setOnSeekBarChangeListener(this)

        this.finishBtn.setOnClickListener(this)
        this.registerBtn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == this.finishBtn.id){
            var user = Helpers.getUserToRegister()
            user.tone = this.seekBar.progress + 1

            this.loadingServices.show()
            this.presenterUser.RegisterUser(user)
        }
        if(p0.id == this.registerBtn.id){
            startActivity(Intent(this.requireContext(), Login::class.java))
        }
    }

    override fun OnRegisterSuccess() {
        this.loadingServices.dismiss()
        this.alertDialogResult.setImage(R.drawable.ic_star_happy)
        this.alertDialogResult.setMessage("¡Listo! Completaste tu registro, incia sesión para continuar")
        this.alertDialogResult.show()
    }

    override fun OnErrorRegisterSuccess(message: String) {
        this.loadingServices.dismiss()
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        if (p0!!.progress == 0){
            this.img1.setImageResource(R.drawable.l_01)
            this.img2.setImageResource(R.drawable.l_02)
            this.img3.setImageResource(R.drawable.l_03)
            this.img4.setImageResource(R.drawable.l_04)
            this.img5.setImageResource(R.drawable.l_05)
            this.img6.setImageResource(R.drawable.l_06)
            this.tone.text = "Light"
        }
        if (p0!!.progress == 1){
            this.img1.setImageResource(R.drawable.lm_01)
            this.img2.setImageResource(R.drawable.lm_02)
            this.img3.setImageResource(R.drawable.lm_03)
            this.img4.setImageResource(R.drawable.lm_04)
            this.img5.setImageResource(R.drawable.lm_05)
            this.img6.setImageResource(R.drawable.lm_06)
            this.tone.text = "Light Medium"
        }
        if (p0!!.progress == 2){
            this.img1.setImageResource(R.drawable.m_01)
            this.img2.setImageResource(R.drawable.m_02)
            this.img3.setImageResource(R.drawable.m_03)
            this.img4.setImageResource(R.drawable.m_04)
            this.img5.setImageResource(R.drawable.m_05)
            this.img6.setImageResource(R.drawable.m_06)
            this.tone.text = "Medium"
        }
        if (p0!!.progress == 3){
            this.img1.setImageResource(R.drawable.mt_01)
            this.img2.setImageResource(R.drawable.mt_02)
            this.img3.setImageResource(R.drawable.mt_03)
            this.img4.setImageResource(R.drawable.mt_04)
            this.img5.setImageResource(R.drawable.mt_05)
            this.img6.setImageResource(R.drawable.mt_06)
            this.tone.text = "Medium Tan"
        }
        if (p0!!.progress == 4){
            this.img1.setImageResource(R.drawable.md_01)
            this.img2.setImageResource(R.drawable.md_02)
            this.img3.setImageResource(R.drawable.md_03)
            this.img4.setImageResource(R.drawable.md_04)
            this.img5.setImageResource(R.drawable.md_05)
            this.img6.setImageResource(R.drawable.md_06)
            this.tone.text = "Medium Deep"
        }
        if (p0!!.progress == 5){
            this.img1.setImageResource(R.drawable.d_01)
            this.img2.setImageResource(R.drawable.d_02)
            this.img3.setImageResource(R.drawable.d_03)
            this.img4.setImageResource(R.drawable.d_04)
            this.img5.setImageResource(R.drawable.d_05)
            this.img6.setImageResource(R.drawable.d_06)
            this.tone.text = "Deep"
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {}

    override fun onStopTrackingTouch(p0: SeekBar?) {}

}