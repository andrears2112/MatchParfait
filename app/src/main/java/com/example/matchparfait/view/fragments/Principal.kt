package com.example.matchparfait.view.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matchparfait.BasicRecycler
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.model.entitys.User
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.interfaces.LoginView
import com.example.matchparfait.view.interfaces.ProductsView

class Principal : Fragment(), View.OnClickListener, LoginView, ProductsView {

    private lateinit var wish_btn : ImageView
    private lateinit var nails_btn : ImageView
    private lateinit var makeup_btn : ImageView
    private lateinit var skincare_btn : ImageView
    private lateinit var hair_btn : ImageView
    private lateinit var sharedPref : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_principal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.wish_btn = view.findViewById(R.id.wishlist_btn)
        this.nails_btn = view.findViewById(R.id.nails_btn)
        this.makeup_btn = view.findViewById(R.id.makeup_btn)
        this.skincare_btn = view.findViewById(R.id.skincare_btn)
        this.hair_btn = view.findViewById(R.id.hair_btn)

        this.wish_btn.setOnClickListener(this)
        this.nails_btn.setOnClickListener(this)
        this.makeup_btn.setOnClickListener(this)
        this.skincare_btn.setOnClickListener(this)
        this.hair_btn.setOnClickListener(this)

        //this.sharedPref = getSharedPreferences("InfoApp", Context.MODE_PRIVATE)
        //editor = sharedPref.edit()
        //this.presenter.Login("manuelvillegas820@gmail.com", "Contra123")
    }

    override fun OnLoginSuccces(user: User) {
        Toast.makeText(this.requireContext(), user.name, Toast.LENGTH_SHORT).show()
        Log.d("LOGIN", "todo ok ;P")
        //this.preserP.GetProducts()
    }

    override fun OnProductsGetted(products: List<Product>) {
        Log.d("PROD", "todo ok ;P")
        Helpers.saveProducts(products)
    }

    override fun OnErrorGettingProducts(message: String) {
        Log.d("ERROR PROF", message)
    }

    override fun OnLoginError(message: String) {
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d("ERROR LOGIN", message)
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == this.wish_btn.id){
            findNavController().navigate(R.id.wishList)
        }
        if(p0!!.id == this.nails_btn.id){
            findNavController().navigate(R.id.searchProduct)
        }
        if(p0!!.id == this.makeup_btn.id){
            findNavController().navigate(R.id.searchProduct)
        }
        if(p0!!.id == this.skincare_btn.id){
            findNavController().navigate(R.id.searchProduct)
        }
        if(p0!!.id == this.hair_btn.id){
            findNavController().navigate(R.id.searchProduct)
        }
    }

}