package com.example.matchparfait.view.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.model.entitys.ShoppingCartRequest
import com.example.matchparfait.model.entitys.WishListRequest
import com.example.matchparfait.presenter.ProductsPresenterImpl
import com.example.matchparfait.presenter.interfaces.ProductsPresenter
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.adapters.ColorAdapter
import com.example.matchparfait.view.adapters.ImagesAdapter
import com.example.matchparfait.view.components.QuantityController
import com.example.matchparfait.view.components.QuantityControllerDelegate
import com.example.matchparfait.view.components.ScoreStars
import com.example.matchparfait.view.interfaces.ProductsView
import com.google.android.material.button.MaterialButton

class ProductInfo : Fragment(), View.OnClickListener, QuantityControllerDelegate, ProductsView {

    private lateinit var stars : ScoreStars
    private lateinit var recomended : ImageView
    private lateinit var name : TextView
    private lateinit var brand : TextView
    private lateinit var img_product : ViewPager2
    private lateinit var dotsLayout : LinearLayout
    private lateinit var prevButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var adapterImg: ImagesAdapter
    private lateinit var price : TextView
    private lateinit var quantityController : QuantityController
    private lateinit var definition : TextView
    private lateinit var ingredients : TextView
    private lateinit var gridColors : RecyclerView
    private lateinit var btn_shop : MaterialButton
    private lateinit var btn_wishList : MaterialButton
    private lateinit var prodPresenter : ProductsPresenter
    private var selectedProduct : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.prodPresenter = ProductsPresenterImpl(this, this.requireContext())
        this.stars = view.findViewById(R.id.stars)
        this.name = view.findViewById(R.id.name_product)
        this.price = view.findViewById(R.id.price)
        this.img_product = view.findViewById(R.id.viewPager)
        this.definition = view.findViewById(R.id.description)
        this.ingredients = view.findViewById(R.id.ingredients)
        this.quantityController = view.findViewById(R.id.quantity_controller)
        this.brand = view.findViewById(R.id.brand)
        this.recomended = view.findViewById(R.id.recommended)
        this.btn_shop = view.findViewById(R.id.shop)
        this.btn_wishList = view.findViewById(R.id.wish_list)
        this.gridColors = view.findViewById(R.id.grid)
        this.dotsLayout = view.findViewById(R.id.dotsLayout)
        this.prevButton = view.findViewById(R.id.prevButton)
        this.nextButton = view.findViewById(R.id.nextButton)

        this.btn_wishList.setOnClickListener(this)
        this.btn_shop.setOnClickListener(this)
        this.quantityController.setDelegate(this)
        this.prevButton.setOnClickListener(this)
        this.nextButton.setOnClickListener(this)

        this.setData(Helpers.getSelectedProduct())
    }

    fun setData(product : Product){
        this.stars.score(product.stars)
        this.name.text = product.productName
        this.brand.text = product.productBrand
        this.price.text = "$${product.price}.00"
        this.quantityController.setMaxQuantity(product.cantidad)
        this.definition.text = product.description
        this.ingredients.text = product.ingredients

        val imageUrls : List<String> = product.photos.split(",").toList()

        adapterImg = ImagesAdapter(imageUrls)
        img_product.adapter = adapterImg

        val listColors : List<String> = product.colors.split(",").toList()
        val adapter = ColorAdapter(listColors) { position ->
            this.selectedProduct = listColors[position]
        }

        gridColors.adapter = adapter
        gridColors.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)

        img_product.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                createDots(position, product.photos.split(",").toList())
            }
        })


        if(product.classification != "0" && product.classification != null){
            this.recomended.visibility = View.VISIBLE
        } else {
            this.recomended.visibility = View.GONE
        }
    }

    override fun onClick(p0: View?) {
        if(p0!!.id == this.btn_shop.id){
            if(this.selectedProduct != ""){
                var prod : ShoppingCartRequest = ShoppingCartRequest(Helpers.getSelectedProduct().productId, this.selectedProduct, this.quantityController.getQuantity())
                this.prodPresenter.AddShoppingCart(prod)
            }
            else {

            }
        }
        if(p0.id == this.btn_wishList.id){
            if(this.selectedProduct != "") {
                var prod = WishListRequest(Helpers.getSelectedProduct().productId, this.selectedProduct)
                this.prodPresenter.AddWishList(prod)
            }
            else {

            }
        }
        if(p0.id == this.prevButton.id){
            val currentItem = img_product.currentItem
            if (currentItem > 0) {
                img_product.currentItem = currentItem - 1
            }
        }
        if(p0.id == this.nextButton.id){
            val currentItem = img_product.currentItem
            if (currentItem < adapterImg.itemCount - 1) {
                img_product.currentItem = currentItem + 1
            }
        }
    }

    override fun OnSuccessAddingCart() {
        Log.d("OK SHOP", ":))")
        Toast.makeText(this.requireContext(), "Al carrito", Toast.LENGTH_SHORT).show()
    }

    override fun OnErrorAddindgCart(message: String) {
        Log.d("ERROR SHOP", message)
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun OnSuccesAddingWishList() {
        Log.d("OK WISH", ":))")
        Toast.makeText(this.requireContext(), "A wish", Toast.LENGTH_SHORT).show()
    }

    override fun OnErrorAddingWishList(message: String) {
        Log.d("ERROR Wish", message)
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun dialog(context: Context, message: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.popup_message)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val text : TextView = dialog.findViewById(R.id.message)
        text.text = message

        val handler = Handler()
        handler.postDelayed({
            dialog.dismiss()
        }, 1000)

        dialog.show()
    }

    private fun createDots(position: Int, images : List<String>) {
        dotsLayout.removeAllViews()
        val dots = arrayOfNulls<ImageView>(images.size)
        for (i in images.indices) {
            dots[i] = ImageView(this.requireContext())
            val widthHeight = 22
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(widthHeight, widthHeight))
            params.setMargins(10, 0, 10, 0)
            dots[i]?.layoutParams = params
            dots[i]?.setImageResource(if (i == position) R.drawable.ic_dot_active else R.drawable.ic_dot_inactive)
            dotsLayout.addView(dots[i])
        }
    }

}