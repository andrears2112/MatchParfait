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
        Log.d("WISH LIST", "todo ok ;P")

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
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d("ERROR wish", message)
    }

    override fun onProductClickWish(product: ProductWishList) {
        var prod = Helpers.getProducts().find { it.productId == product.productId }
        Helpers.saveSelectedProduct(prod!!)
        findNavController().navigate(R.id.detailProduct)
    }

    override fun onClickShop(product: ProductWishList) {
        this.prodToDelete = product
        var prod = ShoppingCartRequest(product.productId, product.color, 1)
        this.prodPresenter.AddShoppingCart(prod)
    }

    override fun onRemoveWishList(product: ProductWishList) {
        this.prodToDelete = product
        this.prodPresenter.DeleteWishList(this.prodToDelete)
    }

    override fun OnSuccessAddingCart() {
        Log.d("OK SHOP", ":))")
        this.prodPresenter.DeleteWishList(this.prodToDelete)
    }

    override fun OnErrorAddindgCart(message: String) {
        Log.d("ERROR SHOP", message)
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun OnSuccessDeleteWishList() {
        adapter.removeProductFromWishList(this.prodToDelete)
        this.prodToDelete = ProductWishList()
        Toast.makeText(this.requireContext(), "Listo", Toast.LENGTH_SHORT).show()
    }

    override fun OnErrorDeleteWishList(message: String) {
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}