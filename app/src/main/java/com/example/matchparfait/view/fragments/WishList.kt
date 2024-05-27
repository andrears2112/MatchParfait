package com.example.matchparfait.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.ProductWishList
import com.example.matchparfait.model.entitys.ShoppingCartRequest
import com.example.matchparfait.presenter.ProductsPresenterImpl
import com.example.matchparfait.presenter.interfaces.ProductsPresenter
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.adapters.OnProductClickListener
import com.example.matchparfait.view.adapters.WishListAdapter
import com.example.matchparfait.view.components.AlertDialog
import com.example.matchparfait.view.components.Loading
import com.example.matchparfait.view.interfaces.ProductsView

class WishList : Fragment(), ProductsView, OnProductClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView : CardView
    private lateinit var title : TextView
    private lateinit var message: TextView
    private lateinit var loading : ProgressBar
    private lateinit var adapter: WishListAdapter
    private lateinit var prodPresenter: ProductsPresenter
    private lateinit var prodToDelete : ProductWishList
    private lateinit var loadingServices : Loading
    private lateinit var alertDialog : AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler_basic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.prodPresenter = ProductsPresenterImpl(this, this.requireContext())
        this.alertDialog = AlertDialog(this.requireContext())
        this.loadingServices = Loading(this.requireContext())
        recyclerView = view.findViewById(R.id.recycler_search)
        loading = view.findViewById(R.id.progressBar)
        message = view.findViewById(R.id.message)
        title = view.findViewById(R.id.title)
        searchView = view.findViewById(R.id.search_card)
        searchView.visibility = View.GONE
        message.visibility = View.GONE

        title.text = "Wish List"

        this.prodPresenter.GetWishList()
    }

    override fun OnWishListGetted(products: List<ProductWishList>) {
        if(products.isNotEmpty()){
            loading.visibility = View.GONE
            message.visibility = View.GONE
            adapter = WishListAdapter(products.toMutableList(), this)
            recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
            recyclerView.adapter = adapter
        }
        else{
            loading.visibility = View.GONE
            message.visibility = View.VISIBLE
            message.text = "Tu Wish List está vacia"
        }
    }

    override fun OnErrorGettingWishList(message: String) {
        loading.visibility = View.GONE
        this.message.visibility = View.GONE
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
        Log.d("ERROR WISHLIST", message)
    }

    override fun onProductClickWish(product: ProductWishList) {
        var prod = Helpers.getProducts().find { it.productId == product.productId }
        Helpers.saveSelectedProduct(prod!!)
        findNavController().navigate(R.id.detailProduct)
    }

    override fun onClickShop(product: ProductWishList) {
        this.prodToDelete = product
        var prod = ShoppingCartRequest(product.productId, product.color, 1)
        this.loadingServices.show()
        this.prodPresenter.AddShoppingCart(prod)
    }

    override fun onRemoveWishList(product: ProductWishList) {
        this.prodToDelete = product
        this.loadingServices.show()
        this.prodPresenter.DeleteWishList(this.prodToDelete)
    }

    override fun OnSuccessAddingCart() {
        this.prodPresenter.DeleteWishList(this.prodToDelete)
    }

    override fun OnErrorAddindgCart(message: String) {
        this.loadingServices.dismiss()
        this.message.visibility = View.GONE
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
        Log.d("ERROR SHOP", message)
    }

    override fun OnSuccessDeleteWishList() {
        this.loadingServices.dismiss()
        adapter.removeProductFromWishList(this.prodToDelete)
        this.prodToDelete = ProductWishList()
        if(adapter.itemCount == 0){
            message.visibility = View.VISIBLE
            message.text = "Tu Wish List está vacia"
        }
    }

    override fun OnErrorDeleteWishList(message: String) {
        this.loadingServices.dismiss()
        this.message.visibility = View.GONE
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
        Log.d("ERROR DELETE WISHLIST", message)
    }
}