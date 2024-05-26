package com.example.matchparfait.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.matchparfait.model.entitys.ProductItem
import com.google.gson.Gson

class RecyclerAdapter {

    /*var originalList = ArrayList<ProductItem>()
    private var filteredList = ArrayList<ProductItem>()
    var onItemclick : ((ProductItem) -> Unit)? = null

    init {
        originalList.addAll(currentList)
        filteredList.addAll(currentList)
        filterByProductType("")
        filterMakeUp()
    }

    class ProductViewHolder(var binding: ProductSearchCardBinding): RecyclerView.ViewHolder(binding.root)

    companion object {

        var comparator = object : DiffUtil.ItemCallback<ProductItem>() {
            override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ProductSearchCardBinding.inflate(
                LayoutInflater.from(parent.context),parent
                ,false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position).let {

            val sharedPreferences = holder.itemView.context.getSharedPreferences("InfoApp", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val isTagListValid = it.tagList?.isNotEmpty() == true

            holder.binding.nameProduct.text=it.name?.split(" ")?.joinToString(" ") { it.capitalize() }
            holder.binding.imgProduct.load(it.imageLink)
            holder.binding.brandProduct.text=it.brand?.toUpperCase()

            // Verifica si "oil free" está presente en la lista de tags
            val isOk = isTagListValid && it.tagList!!.contains("Natural") || it.tagList!!.contains("alcohol free") || it.tagList!!.contains("Hypoallergenic") || it.tagList!!.contains("oil free")
            if (isOk) {
                editor.putInt("recomend", 1)
                holder.binding..visibility = View.VISIBLE
            } else {
                editor.putInt("recomend", 0)
                holder.binding.recomend.visibility = View.GONE
            }

            holder.itemView.setOnClickListener{
                val miObjeto = getItem(position)
                val objetoComoJson = Gson().toJson(miObjeto)

                editor.putString("selected_product", objetoComoJson)
                editor.apply()
                onItemclick?.invoke(getItem(position))
            }
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredResults = if (constraint.isNullOrBlank()) {
                    currentList.toList()
                } else {
                    currentList.filter {
                        it.name?.contains(constraint.toString(), ignoreCase = true) == true ||
                                it.brand?.contains(constraint.toString(), ignoreCase = true) == true
                    }
                }

                return FilterResults().apply {
                    values = filteredResults
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList.clear()
                filteredList.addAll(results?.values as? List<ProductItem> ?: emptyList())
                submitList(filteredList)
            }
        }
    }

    fun filterByProductType(productType: String) {
        val filteredList = originalList.filter { it.productType == productType }
        submitList(filteredList)
    }

    fun filterMakeUp() {
        val filteredList = originalList.filter { it.productType != "nail_polish" }
        val newList = filteredList?.sortedByDescending { it.tagList?.contains("Natural") == true || it.tagList?.contains("alcohol free") == true || it.tagList?.contains("hypoallergenic") == true || it.tagList!!.contains("oil free") == true}
        submitList(newList)
    }


     */
}