package com.example.zabst.myapplication.presener

import android.os.CountDownTimer
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.zabst.myapplication.R
import com.example.zabst.myapplication.app.App
import com.example.zabst.myapplication.presenterinterface.SplashPresenterInterface
import com.example.zabst.myapplication.reciver.ConnectivityReceiver

@InjectViewState
class SplashPresenter : MvpPresenter<SplashPresenterInterface>(), ConnectivityReceiver.ConnectivityRecieverListener {

    init {
        App.getInstance()!!.setConnectivityListener(this)
        App.getInstance()!!.getAppComponent()!!.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        checkConnection()
    }

    override fun onNetworkConnectionChange(isConnected: Boolean) {
        if (isConnected)
            viewState.showNetworkMessage(R.string.network_message_success)
    }

    private fun checkConnection() {
        val isConnected: Boolean = ConnectivityReceiver.isConnected()
        if (!isConnected) {
            viewState.showNetworkMessage(R.string.network_message_error)
        } else {
            success()
        }
    }

    private fun success() {
        object : CountDownTimer(1000, 500) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                viewState.startLoginActivity()
            }
        }.start()
    }

}
