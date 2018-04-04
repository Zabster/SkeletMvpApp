package com.example.zabst.myapplication.component

import com.example.zabst.myapplication.MainActivity
import com.example.zabst.myapplication.module.AppModule
import com.example.zabst.myapplication.module.NetModule
import com.example.zabst.myapplication.presener.BasePresenter
import com.example.zabst.myapplication.presener.SplashPresenter
import com.example.zabst.myapplication.ui.BaseActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (NetModule::class)])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(baseActivity: BaseActivity)

    fun inject(basePresenter: BasePresenter)
    fun inject(splashPresenter: SplashPresenter)

}