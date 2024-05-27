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
import com.example.matchparfait.model.entitys.ProductWishList

class WishListAdapter(private val productList: MutableList<ProductWishList>, private val listener: OnProductClickListener) : RecyclerView.Adapter<WishListAdapter.ProductViewHolder>(){

    private var filteredProductList: List<ProductWishList> = productList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.wish_list_card, parent, false)
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

        if (product.classification != "0" && product.classification != null && product.classification != "") {
            holder.poductRecommended.visibility = View.VISIBLE
        } else {
            holder.poductRecommended.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            listener.onProductClickWish(product)
        }

        holder.btnShop.setOnClickListener {
            listener.onClickShop(product)
        }

        holder.btnRemoveWishList.setOnClickListener {
            listener.onRemoveWishList(product)
            removeProductFromWishList(product)
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
        val btnShop: Button = itemView.findViewById(R.id.btn_shop)
        val btnRemoveWishList: ImageView = itemView.findViewById(R.id.delete)
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

    fun removeProductFromWishList(product: ProductWishList) {
        val index = productList.indexOf(product)
        if (index != -1) {
            productList.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}