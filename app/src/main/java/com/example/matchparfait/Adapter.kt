package com.example.matchparfait

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(var list : List<ProductData>) : RecyclerView.Adapter<Adapter.ProductViewHolder>(),
    Filterable {

    inner class ProductViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        val img : ImageView = item.findViewById(R.id.img_product)
        val name : TextView = item.findViewById(R.id.name_product)
        val brand : TextView = item.findViewById(R.id.brand_product)
    }

    fun setFilteredList(list : List<ProductData>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.img.setImageResource(list[position].img)
        holder.name.text = list[position].name
        holder.brand.text = list[position].brand
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}