package com.example.matchparfait.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

class Splash : AppCompatActivity(), LoginView, ProductsView{

    private var presenter : LoginPresenter = LoginPresenterImpl(this, this)
    private var preserP : ProductsPresenter = ProductsPresenterImpl(this, this)
    private var preferences = Preferences<String>(this)
    private lateinit var loading : Loading
    private lateinit var alertDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        this.alertDialog = AlertDialog(this)
        this.loading = Loading(this)

        if(preferences.GetPreferenceSync("mail") == "NOT_FOUND"){
            var intent = Intent(this, Login::class.java)
            startActivity(intent)
        } else {
            var mail = preferences.GetPreferenceSync("mail")
            var passwod = preferences.GetPreferenceSync("password")
            this.loading.show()
            this.presenter.Login(mail, passwod)
        }
    }

    override fun OnLoginSuccces(user: User) {
        this.preserP.GetProducts()
    }

    override fun OnProductsGetted(products: List<Product>) {
        this.loading.dismiss()
        Helpers.saveProducts(products)
        var intent = Intent(this, Main::class.java)
        startActivity(intent)
    }

    override fun OnErrorGettingProducts(message: String) {
        this.loading.show()
        this.alertDialog.setMessage(message)
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.show()
        Log.d("ERROR GET PRODUCTS", message)
    }

    override fun OnLoginError(message: String) {
        this.loading.dismiss()
        this.alertDialog.setMessage(message)
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.show()
        Log.d("ERROR LOGIN", message)
    }
}