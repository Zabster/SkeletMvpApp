package com.example.zabst.myapplication.presenterinterface

import com.arellomobile.mvp.MvpView

interface FirstFragmentInterface: MvpView {
    fun showNetworkMessage(message: Int)
    fun updateAdapter()
    fun updateLoading(load: Boolean)
    fun refreshOut()
}