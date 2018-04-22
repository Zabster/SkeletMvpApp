package com.example.zabst.myapplication.presener

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.zabst.myapplication.app.App
import com.example.zabst.myapplication.presenterinterface.LoginActivityInterface
import com.example.zabst.myapplication.reciver.ConnectivityReceiver

@InjectViewState
class LoginPresenter: MvpPresenter<LoginActivityInterface>(), ConnectivityReceiver.ConnectivityRecieverListener {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        App.getInstance()!!.getAppComponent()!!.inject(this)
    }

    override fun onNetworkConnectionChange(isConnected: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}