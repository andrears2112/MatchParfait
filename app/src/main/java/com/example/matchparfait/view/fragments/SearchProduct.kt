package com.example.matchparfait.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.adapters.OnProductClickListener
import com.example.matchparfait.view.adapters.ProductAdapter

class SearchProduct : Fragment(), OnProductClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var search: SearchView
    lateinit var adapter: ProductAdapter
    private lateinit var loading : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProductAdapter(Helpers.getProducts(), this)
        recyclerView = view.findViewById(R.id.recycler_search)
        search = view.findViewById(R.id.searchView)
        loading = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.adapter = adapter

        setupSearchView()

        loading.visibility = View.GONE
    }

    private fun setupSearchView() {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText?: "")
                return false
            }
        })
    }

    override fun onProductClick(product: Product) {
        Helpers.saveSelectedProduct(product)
        findNavController().navigate(R.id.detailProduct)
    }

    override fun onClickWishList(product: Product) {
        Toast.makeText(this.requireContext(), "wish click", Toast.LENGTH_SHORT).show()
    }

}