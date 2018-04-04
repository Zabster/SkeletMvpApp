package com.example.zabst.myapplication.presenterinterface

import com.arellomobile.mvp.MvpView

interface BaseActivityInterfase: MvpView {
    fun showNetworkMessage(res: Int)
    fun updateAdapter()
    fun updateLoading(load: Boolean)
}