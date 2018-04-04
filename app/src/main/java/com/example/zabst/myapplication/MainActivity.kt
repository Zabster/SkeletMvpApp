package com.example.zabst.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.zabst.myapplication.app.App
import com.example.zabst.myapplication.presener.SplashPresenter
import com.example.zabst.myapplication.presenterinterface.SplashPresenterInterface
import com.example.zabst.myapplication.ui.BaseActivity

class MainActivity : MvpAppCompatActivity(), SplashPresenterInterface {

    @InjectPresenter
    lateinit var splashPresenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        App.getInstance()!!.getAppComponent()!!.inject(this)
    }

    override fun showNetworkMessage(res: Int) {
        Toast.makeText(this@MainActivity, resources.getString(res), Toast.LENGTH_SHORT).show()
    }

    override fun startBaseActivity() {
        val intent = Intent(this@MainActivity, BaseActivity::class.java)
        startActivity(intent)
    }
}
