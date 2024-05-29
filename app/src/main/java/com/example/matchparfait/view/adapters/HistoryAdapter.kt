package com.example.matchparfait.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.HistoryUser
import com.example.matchparfait.view.fragments.History
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class HistoryAdapter(private val historyList : MutableList<HistoryUser>, private val listener : OnProductClickListener) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var filteredHistory : List<HistoryUser> = historyList
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.history_card, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        val order = historyList[position]
        holder.historyOrder.text = "Pedido: "+order.orderSale.toString()

        if(order.status == "EP"){
            holder.historyStatus.text = "Estatus: En espera de pago"
        }
        if(order.status == "C"){
            holder.historyStatus.text = "Estatus: En camino"
        }
        if(order.status == "P"){
            holder.historyStatus.text = "Estatus: Procesando"
        }
        if(order.status == "E"){
            holder.historyStatus.text = "Estatus: Entregado"
        }

        if(order.status == "E"){
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
            val zonedDateTime = ZonedDateTime.parse(order.deadline, formatter)
            val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formattedDate = zonedDateTime.format(dateFormatter)
            holder.historyDate.text = "Fecha de entrega: "+formattedDate
        }else {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
            val zonedDateTime = ZonedDateTime.parse(order.estimatedDate, formatter)
            val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formattedDate = zonedDateTime.format(dateFormatter)
            holder.historyDate.text = "Fecha estimada de entrega: "+formattedDate
        }

        holder.historyAmount.text = "Total: $${order.totalAmount}.00"

        holder.itemView.setOnClickListener {
            listener.onClickHistory(order)
        }
    }

    override fun getItemCount() = historyList.size

    class HistoryViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val historyOrder : TextView = itemView.findViewById(R.id.order)
        val historyStatus : TextView = itemView.findViewById(R.id.status)
        val historyDate : TextView = itemView.findViewById(R.id.date)
        val historyAmount : TextView = itemView.findViewById(R.id.total)
    }

}