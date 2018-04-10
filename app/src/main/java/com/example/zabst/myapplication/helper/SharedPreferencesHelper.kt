package com.example.zabst.myapplication.helper

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(var context: Context?) {

    companion object {
        const val PREFERENCES_TAG: String = ""
    }

    private var sharedPreferences: SharedPreferences ?= null

    init {
        sharedPreferences = context!!.getSharedPreferences(Companion.PREFERENCES_TAG, Context.MODE_PRIVATE)
    }

    fun saveStringValueByKey(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringValueByKey(key: String): String? {
        return sharedPreferences?.getString(key, null)
    }

}