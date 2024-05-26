package com.example.matchparfait.view.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.matchparfait.MyApp
import com.example.matchparfait.R
import com.example.matchparfait.view.fragments.ShopBag
import com.example.matchparfait.model.entitys.ProductColor
import com.example.matchparfait.model.entitys.ProductItem

class ShopBagAdapter (private val dataList: List<Pair<ProductItem, ProductColor?>>, private val activity: ShopBag) :
    RecyclerView.Adapter<ShopBagAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.name_product)
        val productBrand: TextView = itemView.findViewById(R.id.brand_product)
        val image : ImageView = itemView.findViewById(R.id.img_product)
        //val colorName: TextView = itemView.findViewById(R.id.color_name)
        val productColor: ImageView = itemView.findViewById(R.id.color)
        val recomended : ImageView = itemView.findViewById(R.id.recommended)
        val delete : ImageView = itemView.findViewById(R.id.delete)
        val price : TextView = itemView.findViewById(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_bag_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (productItem, productColor) = dataList[position]
        val sharedPreferences = holder.itemView.context.getSharedPreferences("InfoApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        holder.productName.text = productItem.name?.split(" ")?.joinToString(" ") { it.capitalize() }
        holder.productBrand.text = productItem.brand?.toUpperCase()
        holder.image.load(productItem.imageLink)
        val precioCalculado = productItem.price!!.toDouble() * 20
        val precioFormateado = String.format("%.2f", precioCalculado)
        holder.price.text = "$$precioFormateado"
        if(productColor != null){
            //holder.colorName.visibility = View.VISIBLE
            holder.productColor.visibility = View.VISIBLE
            //holder.colorName.text = productColor?.colourName
            holder.productColor.backgroundTintList = ColorStateList.valueOf((Color.parseColor(productColor?.hexValue)))
        }
        else{
            //holder.colorName.visibility = View.GONE
            holder.productColor.visibility = View.GONE
        }

        val isTagListValid = productItem.tagList?.isNotEmpty() == true
        val isOilFree = isTagListValid && productItem.tagList!!.contains("oil free")
        if (isOilFree) {
            editor.putInt("recomend", 1)
            holder.recomended.visibility = View.VISIBLE
        } else {
            editor.putInt("recomend", 0)
            holder.recomended.visibility = View.GONE
        }

        holder.delete.setOnClickListener{
            eliminarProducto(position, activity = activity as ShopBag)
        }
    }

    fun eliminarProducto(position: Int, activity : ShopBag) {
        val productoAEliminar = getItem(position)
        MyApp.shopBag.remove(productoAEliminar)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
        activity.actualizarTotal()
    }

    fun getItem(position: Int): Pair<ProductItem, ProductColor?> {
        return dataList[position]
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}