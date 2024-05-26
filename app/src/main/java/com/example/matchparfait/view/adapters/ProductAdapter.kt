package com.example.matchparfait.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.model.entitys.ProductShopBag
import com.example.matchparfait.model.entitys.ProductWishList

interface OnProductClickListener {
    fun onProductClick(product: Product){}
    fun onProductClickWish(product: ProductWishList){}
    fun onClickWishList(product: Product){}
    fun onClickShop(product: ProductWishList){}
    fun onRemoveWishList(product: ProductWishList){}
    fun onProductClickShopBag(product: ProductShopBag){}
    fun onRemoveShopBag(product: ProductShopBag){}
}

class ProductAdapter(private val productList: List<Product>, private val listener: OnProductClickListener) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var filteredProductList: List<Product> = productList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.productName
        holder.productBrand.text = product.productBrand
        holder.productPrice.text = "$${product.price}.00"

        Glide.with(holder.itemView.context)
            .load(product.photo)
            .into(holder.productImage)

        if(product.classification != "0" &&   product.classification != null  && product.classification != ""){
            holder.poductRecommended.visibility = View.VISIBLE
        }
        else{
            holder.poductRecommended.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            listener.onProductClick(product)
        }

        holder.btnWishList.setOnClickListener {
            listener.onClickWishList(product)
        }
    }

    override fun getItemCount() = productList.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.img_product)
        val productName: TextView = itemView.findViewById(R.id.name_product)
        val productBrand: TextView = itemView.findViewById(R.id.brand_product)
        val productPrice: TextView = itemView.findViewById(R.id.price_product)
        val poductRecommended: ImageView = itemView.findViewById(R.id.recommended)
        val btnWishList: Button = itemView.findViewById(R.id.wish_list_btn)
    }

    fun filter(query: String) {
        filteredProductList = if (query.isEmpty()) {
            productList
        } else {
            productList.filter {
                it.productName.contains(query, ignoreCase = true) ||
                        it.productBrand?.contains(query, ignoreCase = true) == true
            }
        }
        notifyDataSetChanged()
    }
}