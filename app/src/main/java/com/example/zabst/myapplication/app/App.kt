package com.example.zabst.myapplication.app

import android.support.multidex.MultiDexApplication
import com.example.zabst.myapplication.component.AppComponent
import com.example.zabst.myapplication.component.DaggerAppComponent
import com.example.zabst.myapplication.module.AppModule
import com.example.zabst.myapplication.module.NetModule
import com.example.zabst.myapplication.reciver.ConnectivityReceiver

class App : MultiDexApplication() {

    private var appComponent: AppComponent ?= null

    override fun onCreate() {
        super.onCreate()

        Companion.app = this

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule("https://umorili.herokuapp.com"))
                .build()
    }

    fun getAppComponent(): AppComponent? {
        return appComponent
    }

    fun setConnectivityListener(listener: ConnectivityReceiver.ConnectivityRecieverListener) {
        ConnectivityReceiver.connectivityReceiverListener = listener
    }

    // static
    companion object {
        private var app: App ?= null

        @Synchronized fun getInstance(): App? {
            return Companion.app
        }
    }

}