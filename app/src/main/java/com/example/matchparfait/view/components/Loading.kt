package com.example.matchparfait.view.components

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.matchparfait.R

class Loading(context: Context) {

    private val loadingDialog: AlertDialog

    init {
        val builder = AlertDialog.Builder(context)
        val inflater = (context as? AppCompatActivity)?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.item_loading, null)
        builder.setView(dialogView)
        builder.setCancelable(false)
        loadingDialog = builder.create()
    }

    fun show() {
        loadingDialog.show()
    }

    fun dismiss() {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }
}