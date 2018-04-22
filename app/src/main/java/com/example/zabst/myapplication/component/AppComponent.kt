package com.example.zabst.myapplication.component

import com.example.zabst.myapplication.MainActivity
import com.example.zabst.myapplication.module.AppModule
import com.example.zabst.myapplication.module.DataStoreModule
import com.example.zabst.myapplication.module.NetModule
import com.example.zabst.myapplication.presener.*
import com.example.zabst.myapplication.ui.BaseActivity
import com.example.zabst.myapplication.ui.fragments.FirstFragment
import com.example.zabst.myapplication.ui.LoginActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (NetModule::class), (DataStoreModule::class)])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(baseActivity: BaseActivity)
    fun inject(loginActivity: LoginActivity)

    fun inject(basePresenter: BasePresenter)
    fun inject(splashPresenter: SplashPresenter)
    fun inject(loginPresenter: LoginPresenter)
    fun inject(firstFragmentPresenter: FirstFragmentPresenter)
    fun inject(secondFragmentPresenter: SecondFragmentPresenter)
    fun inject(thridFragmentPresenter: ThridFragmentPresenter)

    fun inject(firstFragment: FirstFragment)

}
