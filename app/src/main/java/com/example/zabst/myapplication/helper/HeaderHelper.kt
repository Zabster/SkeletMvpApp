package com.example.zabst.myapplication.helper

import android.util.Log

// simple
class HeaderHelper(string: String) {

    private var map: HashMap<String, String> = hashMapOf()

    init {
        Log.d("HeaderHelper", "Header string")

        map["Content-Type"] = "application/json"
        map["Accept-Language"] = "en"
        map["Accept"] = "application/json"
    }

    fun getMap(): HashMap<String, String> {
        return map
    }
}