package com.example.matchparfait

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matchparfait.model.entitys.ProductItem
import com.example.matchparfait.view.adapters.RecyclerAdapter
import com.example.matchparfait.view.fragments.SearchProduct
import com.example.matchparfait.view.fragments.ShopBag

class BasicRecycler : AppCompatActivity(), View.OnClickListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var search : ImageView
    private lateinit var shop_bag : ImageView
    private lateinit var user : ImageView
    private lateinit var title : TextView
    private lateinit var message : TextView
    private lateinit var loading : ProgressBar
    private var products = ArrayList<ProductItem>()
    lateinit var adapter: RecyclerAdapter
    private lateinit var sharedPref : SharedPreferences
    private lateinit var type : String
    private lateinit var text_title : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_recycler_basic)

        //adapter = RecyclerAdapter(this)
        this.searchView = findViewById(R.id.searchView)
        this.search = findViewById(R.id.search)
        this.shop_bag = findViewById(R.id.shop_bag)
        this.user = findViewById(R.id.user)
        recyclerView = findViewById(R.id.recycler_search)
        title = findViewById(R.id.title)
        loading = findViewById(R.id.progressBar)
        message = findViewById(R.id.message)
        this.message.visibility = View.GONE

        this.search.setOnClickListener(this)
        this.shop_bag.setOnClickListener(this)
        this.user.setOnClickListener(this)

        this.sharedPref = getSharedPreferences("InfoApp", Context.MODE_PRIVATE)
        text_title = sharedPref.getString("source", "")!!
        title.text = text_title

        recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.adapter = adapter

        setupSearchView()

        if (text_title == "Uñas"){
            this.type = "nail_polish"
            //.filterByProductType(this.type)
            this.services()
        }
        if (text_title == "Maquillaje") {
            //adapter.filterMakeUp()
            this.services()
        }
        if (text_title == "Cuidado de la Piel" || text_title == "Cabello") {
            this.loading.visibility = View.GONE
            this.message.visibility = View.VISIBLE
        }

        /*adapter.onItemclick = {
            startActivity(Intent(this, ProductInfo::class.java))
        }

         */
    }

    fun services(){
        //val callapiService = RetrofitClient.service.getAllProducts()

        /*callapiService.enqueue(object : Callback<List<ProductItem>> {
            override fun onFailure(call: Call<List<ProductItem>>?, t: Throwable?) {
                // Manejar la falla de la llamada al servicio si es necesario
            }

            override fun onResponse(call: Call<List<ProductItem>>?, response: Response<List<ProductItem>>?) {
                if (response != null) {
                    if (response.code() == 200) {
                        loading.visibility = View.GONE
                        products = ArrayList(response.body() ?: emptyList())
                        adapter.originalList = response.body() as ArrayList<ProductItem>
                        // Si es necesario, filtra nuevamente por tipo "mascara"
                        if (text_title == "Uñas"){
                            adapter.filterByProductType("nail_polish")
                        }
                        if (text_title == "Maquillaje") {
                            adapter.filterMakeUp()
                        }
                    } else {
                        // Manejar otros códigos de respuesta si es necesario
                    }
                }
            }
        })

         */
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //adapter.filter.filter(newText)
                return false
            }
        })
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