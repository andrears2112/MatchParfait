package com.example.matchparfait.view.components

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.matchparfait.R
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

class AlertDialog(context: Context) {

    private val alertDialog: AlertDialog
    private val dialogView = LayoutInflater.from(context).inflate(R.layout.item_alert_dialog, null)
    private val dialogMessage: TextView = dialogView.findViewById(R.id.dialogMessage)
    private val dialogImage: ImageView = dialogView.findViewById(R.id.dialogImage)
    private val closeButton: Button = dialogView.findViewById(R.id.closeButton)

    init {
        val builder = AlertDialog.Builder(context)
        builder.setView(dialogView)
        builder.setCancelable(false)

        alertDialog = builder.create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        closeButton.setOnClickListener {
            alertDialog.dismiss()
        }

    }

    fun setMessage(message: String) {
        dialogMessage.text = message
    }

    fun setImage(resourceId: Int) {
        dialogImage.setImageResource(resourceId)
    }

    fun show() {
        alertDialog.show()
    }

    fun dismiss() {
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
        }
    }
}