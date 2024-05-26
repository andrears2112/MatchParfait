package com.example.matchparfait

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.matchparfait.model.entitys.ProductItem
import com.example.matchparfait.view.fragments.SearchProduct
import com.example.matchparfait.view.fragments.ShopBag
import com.google.gson.Gson

class OtherSites : AppCompatActivity(), View.OnClickListener {

    private lateinit var search : ImageView
    private lateinit var shop_bag : ImageView
    private lateinit var user : ImageView
    private lateinit var price1 : TextView
    private lateinit var price2 : TextView
    private lateinit var price3 : TextView
    private lateinit var price4 : TextView
    private lateinit var price5 : TextView
    private lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.other_stores)

        this.search = findViewById(R.id.search)
        this.shop_bag = findViewById(R.id.shop_bag)
        this.user = findViewById(R.id.user)
        this.search.setOnClickListener(this)
        this.shop_bag.setOnClickListener(this)
        this.user.setOnClickListener(this)
        this.price1 = findViewById(R.id.price1)
        this.price2 = findViewById(R.id.price2)
        this.price3 = findViewById(R.id.price3)
        this.price4 = findViewById(R.id.price4)
        this.price5 = findViewById(R.id.price5)

        this.sharedPref = getSharedPreferences("InfoApp", Context.MODE_PRIVATE)
        val objetoComoJson = sharedPref.getString("selected_product", "")

        if (!objetoComoJson.isNullOrEmpty()) {
            val gson = Gson()
            val miObjetoRecuperado = gson.fromJson(objetoComoJson, ProductItem::class.java)
            val pr1 = miObjetoRecuperado.price!!.toDouble() * 21
            val pr1F = String.format("%.2f", pr1)
            this.price1.text = "$"+pr1F
            val pr2 = miObjetoRecuperado.price!!.toDouble() * 21.5
            val pr2F = String.format("%.2f", pr2)
            this.price2.text = "$"+pr2F
            val pr3 = miObjetoRecuperado.price!!.toDouble() * 22
            val pr3F = String.format("%.2f", pr3)
            this.price3.text = "$"+pr3F
            val pr4 = miObjetoRecuperado.price!!.toDouble() * 20.7
            val pr4F = String.format("%.2f", pr4)
            this.price4.text = "$"+pr4F
            val pr5 = miObjetoRecuperado.price!!.toDouble() * 21.3
            val pr5F = String.format("%.2f", pr5)
            this.price5.text = "$"+pr5F
        } else {
            // El valor no existe o está vacío
            Log.d("TAG", "No se pudo recuperar el objeto")
        }
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == this.search.id){
            startActivity(Intent(this, SearchProduct::class.java))
        }
        if(p0!!.id == this.shop_bag.id){
            startActivity(Intent(this, ShopBag::class.java))
        }
        if(p0!!.id == this.user.id){
            startActivity(Intent(this, UserProfile::class.java))
        }
    }

}