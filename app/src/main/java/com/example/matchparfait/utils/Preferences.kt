package com.example.matchparfait.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences<T>(context: Context) {
    private lateinit var preferences:SharedPreferences
    var context = context

    fun SavePreference(name:String, value:T){
        this.preferences = context.getSharedPreferences(name,0)
        this.preferences.edit().putString(name, value as String).apply()
    }

    fun GetPreference(name:String){
        this.preferences = context.getSharedPreferences(name,0)
    }

    fun GetPreferenceSync(name:String):String{
        this.preferences = context.getSharedPreferences(name,0)
        if(this.preferences.getString(name, "").toString()!= ""){
            return this.preferences.getString(name, "")!!
        }
        return "NOT_FOUND"
    }
}