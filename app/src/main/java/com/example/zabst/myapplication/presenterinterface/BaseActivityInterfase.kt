package com.example.zabst.myapplication.presenterinterface

import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.MvpView

interface BaseActivityInterfase: MvpView {
    fun showFragment(fragment: MvpAppCompatFragment)
}