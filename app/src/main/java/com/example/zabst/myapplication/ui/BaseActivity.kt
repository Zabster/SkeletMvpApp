package com.example.zabst.myapplication.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.zabst.myapplication.R
import com.example.zabst.myapplication.app.App
import com.example.zabst.myapplication.helper.SharedPreferencesHelper
import com.example.zabst.myapplication.presener.BasePresenter
import com.example.zabst.myapplication.presenterinterface.BaseActivityInterfase
import javax.inject.Inject

class BaseActivity : MvpAppCompatActivity(), BaseActivityInterfase,
        BottomNavigationView.OnNavigationItemSelectedListener {

    //внедрение презентора
    @InjectPresenter
    lateinit var basePresenter: BasePresenter

    //внедрение хранилища
    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    //привязка ui элементов
    @BindView(R.id.bottom_navigation)
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        App.getInstance()!!.getAppComponent()!!.inject(this)
        ButterKnife.bind(this)

        //слушатель для нижнего меню
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        //тест
        sharedPreferencesHelper.saveStringValueByKey("key", "value")

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        basePresenter.showFragment(item.itemId, this)
        return true
    }

    // отображение фрагментов во frame layout
    override fun showFragment(fragment: MvpAppCompatFragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)

        //todo add back

        transaction.commit()
    }
}
