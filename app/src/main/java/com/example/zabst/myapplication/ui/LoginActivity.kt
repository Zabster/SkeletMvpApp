package com.example.zabst.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.zabst.myapplication.R
import com.example.zabst.myapplication.app.App
import com.example.zabst.myapplication.presener.LoginPresenter
import com.example.zabst.myapplication.presenterinterface.LoginActivityInterface
import com.gc.materialdesign.views.Button

class LoginActivity : MvpAppCompatActivity(), LoginActivityInterface, View.OnClickListener {

    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    @BindView(R.id.login_btn)
    lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)

        App.getInstance()!!.getAppComponent()!!.inject(this)

        loginBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id){
            loginBtn.id -> {
                val intent = Intent(this@LoginActivity, BaseActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}
