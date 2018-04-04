package com.example.zabst.myapplication.reciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.zabst.myapplication.app.App

open class ConnectivityReceiver: BroadcastReceiver() {

    companion object {
        var connectivityReceiverListener: ConnectivityRecieverListener ?= null

        fun isConnected(): Boolean {
            val connectivityManager: ConnectivityManager = App.getInstance()!!.applicationContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo: NetworkInfo = connectivityManager.activeNetworkInfo
            return networkInfo.isConnectedOrConnecting
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val connectiveManager: ConnectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo = connectiveManager.activeNetworkInfo
        val isConnected: Boolean = networkInfo.isConnectedOrConnecting

        Companion.connectivityReceiverListener!!.onNetworkConnectionChange(isConnected)
    }


    interface ConnectivityRecieverListener{
        fun onNetworkConnectionChange(isConnected: Boolean)
    }
}
