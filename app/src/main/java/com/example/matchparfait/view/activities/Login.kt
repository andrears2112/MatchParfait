package com.example.matchparfait.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.model.entitys.User
import com.example.matchparfait.presenter.LoginPresenterImpl
import com.example.matchparfait.presenter.ProductsPresenterImpl
import com.example.matchparfait.presenter.interfaces.LoginPresenter
import com.example.matchparfait.presenter.interfaces.ProductsPresenter
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.utils.Preferences
import com.example.matchparfait.view.components.AlertDialog
import com.example.matchparfait.view.components.Loading
import com.example.matchparfait.view.interfaces.LoginView
import com.example.matchparfait.view.interfaces.ProductsView

class Login : AppCompatActivity(), View.OnClickListener, LoginView, ProductsView{

    private lateinit var mail : EditText
    private lateinit var password : EditText
    private lateinit var btn_login : Button
    private lateinit var btn_register : TextView
    private var presenter : LoginPresenter = LoginPresenterImpl(this, this)
    private var presenterP : ProductsPresenter = ProductsPresenterImpl(this, this)
    private var preferences = Preferences<String>(this)
    private lateinit var loading : Loading
    private lateinit var alertDialog : AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.alertDialog = AlertDialog(this)
        this.loading = Loading(this)
        this.mail = findViewById(R.id.mail)
        this.password = findViewById(R.id.password)
        this.btn_login = findViewById(R.id.btn_login)
        this.btn_register = findViewById(R.id.btn_register)

        this.btn_login.setOnClickListener(this)
        this.btn_register.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == this.btn_login.id){
            if(this.mail.text.isNotEmpty() && this.password.text.isNotEmpty() ){
                this.loading.show()
                this.presenter.Login(this.mail.text.toString(), this.password.text.toString())
            }
            else {
                this.alertDialog.setMessage("Ingresa todos los campos")
                this.alertDialog.setImage(R.drawable.ic_star_worry)
                this.alertDialog.show()
            }
        }
        if(p0.id == this.btn_register.id){

        }
    }

    override fun OnLoginSuccces(user: User) {
        this.preferences.SavePreference("mail", this.mail.text.toString())
        this.preferences.SavePreference("password", this.password.text.toString())
        this.presenterP.GetProducts()
    }

    override fun OnLoginError(message: String) {
        this.loading.dismiss()
        this.alertDialog.setMessage(message)
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.show()
        Log.d("ERROR LOGIN", message)
    }

    override fun OnProductsGetted(products: List<Product>) {
        this.loading.dismiss()
        Helpers.saveProducts(products)
        var intent = Intent(this, Main::class.java)
        startActivity(intent)
    }

    override fun OnErrorGettingProducts(message: String) {
        this.alertDialog.setMessage(message)
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.show()
        Log.d("ERROR GET PRODUCTS", message)
    }
}