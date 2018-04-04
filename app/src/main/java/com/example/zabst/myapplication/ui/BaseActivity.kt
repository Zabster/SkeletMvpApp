package com.example.zabst.myapplication.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.zabst.myapplication.R
import com.example.zabst.myapplication.adapter.Adapter
import com.example.zabst.myapplication.app.App
import com.example.zabst.myapplication.presener.BasePresenter
import com.example.zabst.myapplication.presenterinterface.BaseActivityInterfase

class BaseActivity : MvpAppCompatActivity(), BaseActivityInterfase {

    @InjectPresenter
    lateinit var basePresenter: BasePresenter


    var progress: ProgressBar ?= null

    private var recyclerView: RecyclerView ?= null

    private var adapter: Adapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        App.getInstance()!!.getAppComponent()!!.inject(this)
        ButterKnife.bind(this)

        val linerManager = LinearLayoutManager(this)
        linerManager.orientation = LinearLayoutManager.VERTICAL

        progress = findViewById(R.id.progress)

        recyclerView = findViewById(R.id.recycler)
        adapter = Adapter(basePresenter.getList(), this)
        recyclerView!!.layoutManager = linerManager
        recyclerView!!.adapter = adapter
    }

    override fun showNetworkMessage(res: Int) {
        Toast.makeText(this, resources.getString(res), Toast.LENGTH_SHORT).show()
    }

    override fun updateAdapter() {
        adapter!!.list = basePresenter.getList()
        adapter!!.notifyDataSetChanged()
    }

    override fun updateLoading(load: Boolean) {
        if (load)
            progress!!.visibility = View.VISIBLE
        else
            progress!!.visibility = View.GONE
    }
}
