package com.example.matchparfait

import android.app.Application
import com.example.matchparfait.model.entitys.ProductColor
import com.example.matchparfait.model.entitys.ProductItem

class MyApp : Application() {

    companion object {
        lateinit var wishList: MutableList<ProductItem>
            private set

        lateinit var shopBag: MutableList<Pair<ProductItem, ProductColor?>>
            private set
    }

    override fun onCreate() {
        super.onCreate()
        wishList = mutableListOf()
        shopBag = mutableListOf()
    }

}