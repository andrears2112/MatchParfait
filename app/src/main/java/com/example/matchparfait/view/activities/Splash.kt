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
import com.example.matchparfait.view.interfaces.LoginView
import com.example.matchparfait.view.interfaces.ProductsView

class Splash : AppCompatActivity(), LoginView, ProductsView{

    private var presenter : LoginPresenter = LoginPresenterImpl(this, this)
    private var preserP : ProductsPresenter = ProductsPresenterImpl(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        this.presenter.Login("manuelvillegas820@gmail.com", "Contra123")
    }

    override fun OnLoginSuccces(user: User) {
        Toast.makeText(this, user.name, Toast.LENGTH_SHORT).show()
        Log.d("LOGIN", "todo ok ;P")
        this.preserP.GetProducts()
    }

    override fun OnProductsGetted(products: List<Product>) {
        Log.d("PROD", "todo ok ;P")
        Helpers.saveProducts(products)
        var intent = Intent(this, Main::class.java)
        startActivity(intent)
    }

    override fun OnErrorGettingProducts(message: String) {
        Log.d("ERROR PROF", message)
    }

    override fun OnLoginError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Log.d("ERROR LOGIN", message)
    }
}