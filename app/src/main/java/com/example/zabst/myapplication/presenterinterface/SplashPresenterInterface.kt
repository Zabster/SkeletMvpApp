package com.example.zabst.myapplication.presenterinterface

import com.arellomobile.mvp.MvpView

interface SplashPresenterInterface: MvpView {
    fun showNetworkMessage(res: Int)
    fun startBaseActivity()
}