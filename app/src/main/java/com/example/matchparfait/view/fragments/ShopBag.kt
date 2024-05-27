package com.example.matchparfait.view.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.ProductShopBag
import com.example.matchparfait.model.entitys.ShoppingCartUpdateRequest
import com.example.matchparfait.presenter.ProductsPresenterImpl
import com.example.matchparfait.presenter.interfaces.ProductsPresenter
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.adapters.OnProductClickListener
import com.example.matchparfait.view.adapters.ShopBagAdapter
import com.example.matchparfait.view.components.AlertDialog
import com.example.matchparfait.view.components.Loading
import com.example.matchparfait.view.components.QuantityControllerDelegate
import com.example.matchparfait.view.interfaces.ProductsView

class ShopBag : Fragment(), ProductsView, OnProductClickListener, QuantityControllerDelegate,
View.OnClickListener{

    private lateinit var loading : ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var message : TextView
    private lateinit var adapter: ShopBagAdapter
    private lateinit var button : Button
    private lateinit var total : TextView
    private lateinit var prodPresenter : ProductsPresenter
    private lateinit var listProducts : MutableList<ProductShopBag>
    private lateinit var loadingServices : Loading
    private lateinit var alertDialog : AlertDialog

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
        this.alertDialog = AlertDialog(this.requireContext())
        this.loadingServices = Loading(this.requireContext())
        message = view.findViewById(R.id.message)
        button = view.findViewById(R.id.shop_btn)
        total = view.findViewById(R.id.amount)
        recyclerView = view.findViewById(R.id.recycler_search)
        loading = view.findViewById(R.id.progressBar)

        this.button.setOnClickListener(this)

        this.prodPresenter.GetShoppingCart()
    }

    override fun OnSuccesGettingCart(products: List<ProductShopBag>) {
        this.loading.visibility = View.GONE
        if(products.isNotEmpty()){
            this.listProducts = products.toMutableList()
            this.message.visibility = View.GONE

            val total: Int = products.sumOf { it.price }
            this.total.text = "Total: $${total}.00"
            this.button.isEnabled = true
            this.button.backgroundTintList = ColorStateList.valueOf((Color.parseColor("#9B0E28")))
        }
        else{
            this.recyclerView.visibility = View.GONE
            this.message.visibility = View.VISIBLE
            this.button.isEnabled = false
            this.button.backgroundTintList = ColorStateList.valueOf((Color.parseColor("#A2A2A2")))
        }

        adapter = ShopBagAdapter(products.toMutableList(), this)
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.adapter = adapter
    }

    override fun OnErrorGettingCart(message: String) {
        loading.visibility = View.GONE
        this.message.visibility = View.GONE
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
        Log.d("ERROR CART", message)
    }

    override fun OnChangeAmount(product: ProductShopBag, newQuantity: Int) {
        var prod = Helpers.getProducts().find { it.productId == product.productId }
        adapter.updateProductPrice(product.productId, prod!!.price * newQuantity)
        val index = listProducts.indexOfFirst { it.productId == product.productId }
        this.listProducts[index].cantidad = newQuantity
        val total: Int = listProducts.sumOf { it.price }
        this.total.text = "Total: $${total}.00"
        this.loadingServices.show()
        this.prodPresenter.EditQuantityShoppingCart(ShoppingCartUpdateRequest(product.cartId, product.productId, newQuantity, product.color))
    }

    override fun onProductClickShopBag(product: ProductShopBag) {
        var prod = Helpers.getProducts().find { it.productId == product.productId }
        Helpers.saveSelectedProduct(prod!!)
        findNavController().navigate(R.id.detailProduct)
    }

    override fun onRemoveShopBag(product: ProductShopBag) {
        val index = listProducts.indexOfFirst { it.productId == product.productId }
        this.listProducts.removeAt(index)
        val total: Int = listProducts.sumOf { it.price }
        this.total.text = "Total: $${total}.00"
        this.loadingServices.show()
        this.prodPresenter.DeleteShoppingCart(ShoppingCartUpdateRequest(product.cartId))
    }

    override fun OnDeleteOnCartSucces() {
        if(adapter.itemCount == 0){
            message.visibility = View.VISIBLE
            this.recyclerView.visibility = View.GONE
            message.text = "Tu bolsa de compras está vacía"
            this.button.isEnabled = false
            this.button.backgroundTintList = ColorStateList.valueOf((Color.parseColor("#A2A2A2")))
        }
        this.loadingServices.dismiss()
    }

    override fun OnErrorDeleteCart(message: String) {
        this.loadingServices.dismiss()
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
    }

    override fun OnSuccesEditQuantity() {
        this.loadingServices.dismiss()
    }

    override fun OnErrorEditQuantity(message: String) {
        this.loadingServices.dismiss()
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
    }

    override fun onClick(p0: View?) {
        if (p0!!.id == this.button.id){
            Helpers.saveTotal(this.total.text.toString())
            findNavController().navigate(R.id.payment)
        }
    }
}