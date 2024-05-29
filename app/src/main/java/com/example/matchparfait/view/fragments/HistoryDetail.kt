package com.example.matchparfait.view.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.CommentRequest
import com.example.matchparfait.model.entitys.Product
import com.example.matchparfait.presenter.ProductsPresenterImpl
import com.example.matchparfait.presenter.interfaces.ProductsPresenter
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.adapters.HistoryProdAdapter
import com.example.matchparfait.view.adapters.OnProductClickListener
import com.example.matchparfait.view.components.Loading
import com.example.matchparfait.view.interfaces.ProductsView
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class HistoryDetail : Fragment(), OnProductClickListener, ProductsView{

    private lateinit var order : TextView
    private lateinit var status : TextView
    private lateinit var date : TextView
    private lateinit var recycler : RecyclerView
    private lateinit var adapter: HistoryProdAdapter
    private lateinit var total : TextView
    private lateinit var prodPresenter : ProductsPresenter
    private lateinit var loadingServices : Loading
    private lateinit var alertDialog : com.example.matchparfait.view.components.AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        this.prodPresenter = ProductsPresenterImpl(this, this.requireContext())
        this.alertDialog = com.example.matchparfait.view.components.AlertDialog(this.requireContext())
        this.loadingServices = Loading(this.requireContext())
        this.order = view.findViewById(R.id.order)
        this.status = view.findViewById(R.id.status)
        this.date = view.findViewById(R.id.date)
        this.recycler = view.findViewById(R.id.recycler_search)
        this.total = view.findViewById(R.id.total)

        this.setData()
    }

    fun setData(){
        val order = Helpers.getOrderSelected()

        this.order.text = "Pedido: "+order.orderSale.toString()

        if(order.status == "EP"){
            this.status.text = "Estatus: En espera de pago"
        }
        if(order.status == "C"){
            this.status.text = "Estatus: En camino"
        }
        if(order.status == "P"){
            this.status.text = "Estatus: Procesando"
        }
        if(order.status == "E"){
            this.status.text = "Estatus: Entregado"
        }

        if(order.status == "E"){
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
            val zonedDateTime = ZonedDateTime.parse(order.deadline, formatter)
            val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formattedDate = zonedDateTime.format(dateFormatter)
            this.date.text = "Fecha de entrega: "+formattedDate
        }else {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
            val zonedDateTime = ZonedDateTime.parse(order.estimatedDate, formatter)
            val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formattedDate = zonedDateTime.format(dateFormatter)
            this.date.text = "Fecha estimada de entrega: "+formattedDate
        }

        this.total.text = "Total: $${order.totalAmount}.00"

        adapter = HistoryProdAdapter(order.products.toMutableList(), this)
        recycler.layoutManager = LinearLayoutManager(this.requireContext())
        recycler.adapter = adapter
    }

    override fun onClickComment(product: Product) {
        this.showCommentRatingDialog(this.requireContext(), product.productId)
    }

    fun showCommentRatingDialog(context: Context, idProd : String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.item_comment, null)

        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = builder.create()
        alertDialog.show()

        val ratingBarProd = dialogView.findViewById<RatingBar>(R.id.ratingBarProd)
        val ratingBarMatch = dialogView.findViewById<RatingBar>(R.id.ratingBarMatch)
        val editTextComment = dialogView.findViewById<EditText>(R.id.editTextComment)
        val continueButton = dialogView.findViewById<Button>(R.id.continue_btn)
        val cancelButton = dialogView.findViewById<TextView>(R.id.btn_register)

        // Personalizar el fondo del diálogo si es necesario
        alertDialog.window?.setBackgroundDrawableResource(R.drawable.bg_comment)

        continueButton.setOnClickListener {
            val comment = editTextComment.text.toString()
            val ratingProd = ratingBarProd.rating.toInt()
            val ratingMatch = ratingBarMatch.rating.toInt()

            var commentR = CommentRequest(idProd, ratingProd, comment, ratingMatch)

            this.loadingServices.show()
            this.prodPresenter.CommentProduct(commentR)

            alertDialog.dismiss()
        }

        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    override fun OnSuccessComment() {
        this.loadingServices.dismiss()
        this.alertDialog.setImage(R.drawable.ic_star_smile)
        this.alertDialog.setMessage("¡Gracias por tu opinión!")
        this.alertDialog.show()
    }

    override fun OnErrorComment(message: String) {
        this.loadingServices.dismiss()
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
        Log.d("ERROR COMMENT", message)
    }
}