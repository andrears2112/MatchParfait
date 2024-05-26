package com.example.matchparfait

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DisableMessage : AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.disable_message)

        button = findViewById(R.id.button)

        button.setOnClickListener {
            this.finish()
        }
    }

}