package com.example.matchparfait.view.fragments

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matchparfait.DisableMessage
import com.example.matchparfait.MyApp
import com.example.matchparfait.R
import com.example.matchparfait.UserProfile
import com.example.matchparfait.model.entitys.ProductColor
import com.example.matchparfait.model.entitys.ProductItem
import com.example.matchparfait.view.adapters.ShopBagAdapter

class ShopBag : Fragment(), View.OnClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var user : ImageView
    private lateinit var message : TextView
    lateinit var adapter: ShopBagAdapter
    private lateinit var button : Button
    private lateinit var listaProductItem: List<ProductItem>
    private lateinit var total : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_shop_bag)

        message = findViewById(R.id.message)
        button = findViewById(R.id.shop_btn)
        total = findViewById(R.id.amount)
        user = findViewById(R.id.user)

        user.setOnClickListener(this)
        button.setOnClickListener(this)

        if(!MyApp.shopBag.isEmpty()){
            this.message.visibility = View.GONE
            listaProductItem = MyApp.shopBag.filterIsInstance<Pair<ProductItem, ProductColor?>>()
                .map { it.first }
            val sumaDePrecios: Double = listaProductItem.sumByDouble { it.price!!.toDouble() ?: 0.0 }
            val precioCalculado = sumaDePrecios*20
            val precioFormateado = String.format("%.2f", precioCalculado)
            this.total.text = "Total: $"+precioFormateado
        }
        else{
            this.button.isEnabled = false
            this.button.backgroundTintList = ColorStateList.valueOf((Color.parseColor("#A2A2A2")))
        }

        adapter = ShopBagAdapter(MyApp.shopBag, this)
        recyclerView = findViewById(R.id.recycler_search)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun actualizarTotal() {
        val sumaDePrecios: Double = MyApp.shopBag.filterIsInstance<Pair<ProductItem, ProductColor?>>()
            .map { it.first.price!!.toDouble() ?: 0.0 }
            .sum()
        val precioCalculado = sumaDePrecios*20
        val precioFormateado = String.format("%.2f", precioCalculado)
        this.total.text = "Total: $"+precioFormateado
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == this.button.id){
            startActivity(Intent(this, DisableMessage::class.java))
        }
        if(p0!!.id == this.user.id){
            startActivity(Intent(this, UserProfile::class.java))
        }
    }
}