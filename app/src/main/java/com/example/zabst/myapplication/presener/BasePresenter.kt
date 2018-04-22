package com.example.zabst.myapplication.presener

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.MvpPresenter
import com.example.zabst.myapplication.R
import com.example.zabst.myapplication.app.App
import com.example.zabst.myapplication.presenterinterface.BaseActivityInterfase
import com.example.zabst.myapplication.reciver.ConnectivityReceiver
import com.example.zabst.myapplication.ui.fragments.FirstFragment
import com.example.zabst.myapplication.ui.fragments.SecondFragment
import com.example.zabst.myapplication.ui.fragments.ThirdFragment

@InjectViewState
class BasePresenter: MvpPresenter<BaseActivityInterfase>(), ConnectivityReceiver.ConnectivityRecieverListener {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        App.getInstance()!!.getAppComponent()!!.inject(this)
        showFragment(R.id.action_one, null)
        checkConnection()
    }

    override fun onNetworkConnectionChange(isConnected: Boolean) {
        if (isConnected)
            Log.d("Connect", "is connection? - $isConnected")
    }

    private fun checkConnection() {
        val isConnected: Boolean = ConnectivityReceiver.isConnected()
        if (!isConnected) {
            // todo
        } else {
            // todo
        }
    }

    fun showFragment(id: Int, activity: MvpAppCompatActivity?) {
        var fragment: MvpAppCompatFragment ?= null

        when(id) {
            R.id.action_one ->
                fragment = FirstFragment.newInstance()
            R.id.action_two ->
                fragment = SecondFragment.newInstance(activity!!)
            R.id.action_three ->
                fragment = ThirdFragment.newInstance()
        }
        viewState.showFragment(fragment!!)
    }
}