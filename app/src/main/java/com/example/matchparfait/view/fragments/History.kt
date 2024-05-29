package com.example.matchparfait.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matchparfait.R
import com.example.matchparfait.model.entitys.HistoryUser
import com.example.matchparfait.presenter.UserPresenterImpl
import com.example.matchparfait.presenter.interfaces.UserPresenter
import com.example.matchparfait.view.adapters.HistoryAdapter
import com.example.matchparfait.view.adapters.OnProductClickListener
import com.example.matchparfait.view.components.AlertDialog
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.matchparfait.utils.Helpers
import com.example.matchparfait.view.interfaces.UserView

class History : Fragment(), UserView, OnProductClickListener {

    private lateinit var title : TextView
    private lateinit var searchView : CardView
    private lateinit var progress : ProgressBar
    private lateinit var message : TextView
    private lateinit var recycler : RecyclerView
    private lateinit var adapter: HistoryAdapter
    private lateinit var userPresenter : UserPresenter
    private lateinit var alertDialog : AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler_basic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.userPresenter = UserPresenterImpl(this, this.requireContext())
        this.alertDialog = AlertDialog(this.requireContext())
        this.title = view.findViewById(R.id.title)
        this.progress = view.findViewById(R.id.progressBar)
        this.message = view.findViewById(R.id.message)
        this.recycler = view.findViewById(R.id.recycler_search)
        searchView = view.findViewById(R.id.search_card)

        this.title.text = "Historial"
        searchView.visibility = View.GONE
        this.message.visibility = View.GONE

        this.userPresenter.GetHistory()
    }

    override fun OnSuccessGetingHistory(history : List<HistoryUser>) {
        if(history.isNotEmpty()){
            progress.visibility = View.GONE
            message.visibility = View.GONE
            adapter = HistoryAdapter(history.toMutableList(), this)
            recycler.layoutManager = LinearLayoutManager(this.requireContext())
            recycler.adapter = adapter
        }
        else{
            progress.visibility = View.GONE
            message.visibility = View.VISIBLE
            message.text = "Aún no tienes historial de compras"
        }
    }

    override fun OnErrorGettingHistory(message: String) {
        progress.visibility = View.GONE
        this.message.visibility = View.GONE
        this.alertDialog.setImage(R.drawable.ic_star_worry)
        this.alertDialog.setMessage(message)
        this.alertDialog.show()
        Log.d("ERROR HISTORY", message)
    }

    override fun onClickHistory(item: HistoryUser) {
        Helpers.saveOrderSelected(item)
        findNavController().navigate(R.id.historyDetail)
    }
}