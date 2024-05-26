package com.example.matchparfait.view.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.matchparfait.MyApp
import com.example.matchparfait.R
import com.example.matchparfait.view.fragments.ShopBag
import com.example.matchparfait.model.entitys.ProductColor
import com.example.matchparfait.model.entitys.ProductItem
import com.example.matchparfait.model.entitys.ProductShopBag
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.components.QuantityController
import com.example.matchparfait.view.components.QuantityControllerDelegate

class ShopBagAdapter(private val productList: MutableList<ProductShopBag>, private val listener: OnProductClickListener) :
    RecyclerView.Adapter<ShopBagAdapter.ProductViewHolder>(), QuantityControllerDelegate{

    private var filteredProductList: List<ProductShopBag> = productList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.shop_bag_card, parent, false)
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
            listener.onProductClickShopBag(product)
        }

        holder.btnQuantity.setOnClickListener {

        }

        holder.btnRemove.setOnClickListener {
            listener.onRemoveShopBag(product)
            removeProductFromWishList(product)
        }

        holder.btnQuantity.setProductShopBag(product)
        holder.btnQuantity.setDelegate(object : QuantityControllerDelegate {
            override fun OnChangeAmount(product: ProductShopBag, newQuantity: Int) {
                (listener as? QuantityControllerDelegate)?.OnChangeAmount(product, newQuantity)
            }
        })
        var prod = Helpers.getProducts().find { it.productId == product.productId }
        holder.btnQuantity.setMaxQuantity(prod!!.cantidad)
        holder.btnQuantity.setQuantity(product.cantidad)
    }

    override fun getItemCount() = filteredProductList.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.img_product)
        val productName: TextView = itemView.findViewById(R.id.name_product)
        val productBrand: TextView = itemView.findViewById(R.id.brand_product)
        val productPrice: TextView = itemView.findViewById(R.id.price_product)
        val productColor: ImageView = itemView.findViewById(R.id.color)
        val poductRecommended: ImageView = itemView.findViewById(R.id.recommended)
        val btnQuantity: QuantityController = itemView.findViewById(R.id.quantity_controller)
        val btnRemove: ImageView = itemView.findViewById(R.id.delete)
    }

    fun removeProductFromWishList(product: ProductShopBag) {
        val index = productList.indexOf(product)
        if (index != -1) {
            productList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun updateProductPrice(productId: String, newPrice: Int) {
        val index = productList.indexOfFirst { it.productId == productId }
        if (index != -1) {
            productList[index].price = newPrice
            notifyItemChanged(index)
        }
    }
}