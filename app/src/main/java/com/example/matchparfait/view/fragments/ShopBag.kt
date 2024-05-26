package com.example.matchparfait.view.fragments

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matchparfait.DisableMessage
import com.example.matchparfait.MyApp
import com.example.matchparfait.R
import com.example.matchparfait.UserProfile
import com.example.matchparfait.model.entitys.ProductColor
import com.example.matchparfait.model.entitys.ProductItem
import com.example.matchparfait.model.entitys.ProductShopBag
import com.example.matchparfait.presenter.ProductsPresenterImpl
import com.example.matchparfait.presenter.interfaces.ProductsPresenter
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.adapters.OnProductClickListener
import com.example.matchparfait.view.adapters.ShopBagAdapter
import com.example.matchparfait.view.components.QuantityControllerDelegate
import com.example.matchparfait.view.interfaces.ProductsView

class ShopBag : Fragment(), ProductsView, OnProductClickListener, QuantityControllerDelegate {

    private lateinit var loading : ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var message : TextView
    private lateinit var adapter: ShopBagAdapter
    private lateinit var button : Button
    private lateinit var total : TextView
    private lateinit var prodPresenter : ProductsPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_bag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.prodPresenter = ProductsPresenterImpl(this, this.requireContext())
        message = view.findViewById(R.id.message)
        button = view.findViewById(R.id.shop_btn)
        total = view.findViewById(R.id.amount)
        recyclerView = view.findViewById(R.id.recycler_search)
        loading = view.findViewById(R.id.progressBar)

        this.prodPresenter.GetShoppingCart()
    }

    override fun OnSuccesGettingCart(products: List<ProductShopBag>) {
        this.loading.visibility = View.GONE
        if(products.isNotEmpty()){
            this.message.visibility = View.GONE

            val total: Int = products.sumOf { it.price }
            this.total.text = "Total: $${total}.00"
        }
        else{
            this.message.visibility = View.VISIBLE
            this.button.isEnabled = false
            this.button.backgroundTintList = ColorStateList.valueOf((Color.parseColor("#A2A2A2")))
        }

        adapter = ShopBagAdapter(products.toMutableList(), this)
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.adapter = adapter
    }

    override fun OnErrorGettingCart(message: String) {
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d("ERROR CART", message)
    }

    override fun OnChangeAmount(product: ProductShopBag, newQuantity: Int) {

    }

    override fun onProductClickShopBag(product: ProductShopBag) {
        var prod = Helpers.getProducts().find { it.productId == product.productId }
        Helpers.saveSelectedProduct(prod!!)
        findNavController().navigate(R.id.detailProduct)
    }

    override fun onRemoveShopBag(product: ProductShopBag) {
        Toast.makeText(context, "CLICK ELIMINA", Toast.LENGTH_SHORT).show()
    }
}