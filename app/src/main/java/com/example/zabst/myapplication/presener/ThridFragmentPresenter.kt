package com.example.zabst.myapplication.presener

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.zabst.myapplication.presenterinterface.ThridFragmentInterface
import com.example.zabst.myapplication.reciver.ConnectivityReceiver

@InjectViewState
class ThridFragmentPresenter: MvpPresenter<ThridFragmentInterface>(), ConnectivityReceiver.ConnectivityRecieverListener {

    override fun onNetworkConnectionChange(isConnected: Boolean) {
        // if not connected to do nothing
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun checkConnection() {
        val isConnected: Boolean = ConnectivityReceiver.isConnected()
        if (!isConnected) {

        } else {
            success()
        }
    }

    fun success() {

    }
}