package com.example.matchparfait.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.matchparfait.R
import com.example.matchparfait.UserProfile

class Main : AppCompatActivity(), View.OnClickListener {

    private lateinit var search : ImageView
    private lateinit var shop_bag : ImageView
    private lateinit var user : ImageView
    private lateinit var wishList : ImageView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.navController = findNavController(R.id.nav_host_fragment)

        this.search = findViewById(R.id.search)
        this.shop_bag = findViewById(R.id.shop_bag)
        this.user = findViewById(R.id.user)
        this.wishList = findViewById(R.id.wish_list)

        this.search.setOnClickListener(this)
        this.shop_bag.setOnClickListener(this)
        this.user.setOnClickListener(this)
        this.wishList.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0!!.id == this.search.id) {
            navController.navigate(R.id.searchProduct)
        }
        if (p0!!.id == this.shop_bag.id) {
            navController.navigate(R.id.shop_bag)
        }
        if (p0!!.id == this.wishList.id) {
            navController.navigate(R.id.wishList)
        }
        if (p0!!.id == this.user.id) {
            startActivity(Intent(this, UserProfile::class.java))
        }
    }
}