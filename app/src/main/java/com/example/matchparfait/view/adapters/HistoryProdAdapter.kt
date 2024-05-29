package com.example.matchparfait.view.adapters

import android.content.res.ColorStateList
import android.graphics.Color
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
import com.example.matchparfait.model.entitys.ProductWishList
import com.example.matchparfait.utils.Helpers

class HistoryProdAdapter(private val productList: MutableList<Product>, private val listener: OnProductClickListener) : RecyclerView.Adapter<HistoryProdAdapter.ProductViewHolder>(){

    private var filteredProductList: List<Product> = productList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_history_card, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = filteredProductList[position]
        holder.productName.text = product.productName
        holder.productBrand.text = product.productBrand
        holder.productPrice.text = "$${product.price}.00"

        Glide.with(holder.itemView.context)
            .load(product.photo)
            .into(holder.productImage)

        if(product.color != ""){
            val color = Color.parseColor("#"+product.color)
            holder.productColor.backgroundTintList = ColorStateList.valueOf(color)
        }
        else {
            holder.productColor.visibility = View.GONE
        }

        if (Helpers.getUser().classification == product.classification) {
            holder.poductRecommended.visibility = View.VISIBLE
        } else {
            holder.poductRecommended.visibility = View.GONE
        }

        holder.btnComment.setOnClickListener {
            listener.onClickComment(product)
        }

    }

    override fun getItemCount() = filteredProductList.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.img_product)
        val productName: TextView = itemView.findViewById(R.id.name_product)
        val productBrand: TextView = itemView.findViewById(R.id.brand_product)
        val productPrice: TextView = itemView.findViewById(R.id.price_product)
        val productColor: ImageView = itemView.findViewById(R.id.color)
        val poductRecommended: ImageView = itemView.findViewById(R.id.recommended)
        val btnComment: Button = itemView.findViewById(R.id.btn_comment)
    }
}