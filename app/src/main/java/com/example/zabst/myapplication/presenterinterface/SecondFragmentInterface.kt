package com.example.zabst.myapplication.presenterinterface

import android.location.Location
import com.arellomobile.mvp.MvpView

interface SecondFragmentInterface: MvpView {

    fun progressButtonAndGetLocation(mLocation: Location?, isLocation: Boolean)
}