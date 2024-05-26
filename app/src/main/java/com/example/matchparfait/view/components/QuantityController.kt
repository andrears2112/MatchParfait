package com.example.matchparfait.view.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.ProductShopBag

interface QuantityControllerDelegate {
    fun OnChangeAmount(product : ProductShopBag, newQuantity: Int){}
}

class QuantityController @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) ,
View.OnClickListener{

    private lateinit var delegate : QuantityControllerDelegate
    private var plus : ImageView
    private var minus : ImageView
    private var quantity : TextView
    private var maxQuantity : Int = 0
    private var actualQuantity : Int = 0
    private var prod = ProductShopBag()

    init {
        val view : View = LayoutInflater.from(context).inflate(R.layout.controller_quantity, this, true)
        this.plus = view.findViewById(R.id.plus)
        this.minus = view.findViewById(R.id.minus)
        this.quantity = view.findViewById(R.id.quantity)

        this.minus.setOnClickListener(this)
        this.plus.setOnClickListener(this)
    }

    fun setDelegate(dele : QuantityControllerDelegate){
        this.delegate = dele
    }

    fun setMaxQuantity(amount : Int){
        this.actualQuantity = 1
        this.maxQuantity = amount
    }

    fun setQuantity(amount: Int) {
        this.actualQuantity = amount
        this.quantity.text = this.actualQuantity.toString()
    }

    fun setProductShopBag(product : ProductShopBag){
        prod = product
    }

    fun getQuantity() : Int{
        return this.actualQuantity
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == this.plus.id){
            if(this.actualQuantity != this.maxQuantity){
                this.actualQuantity = this.actualQuantity + 1
                this.quantity.text = this.actualQuantity.toString()
                this.delegate.OnChangeAmount(prod, this.actualQuantity)
            }
        }
        if(p0!!.id == this.minus.id){
            if(this.actualQuantity != 1){
                this.actualQuantity = this.actualQuantity - 1
                this.quantity.text = this.actualQuantity.toString()
                this.delegate.OnChangeAmount(prod, this.actualQuantity)
            }
        }
    }
}