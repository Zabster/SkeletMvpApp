package com.example.zabst.myapplication.ui.fragments

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.zabst.myapplication.R
import com.example.zabst.myapplication.adapter.Adapter
import com.example.zabst.myapplication.helper.dbhelper.UserDBHelper
import com.example.zabst.myapplication.model.dbmodel.UserDBModel
import com.example.zabst.myapplication.presener.FirstFragmentPresenter
import com.example.zabst.myapplication.presenterinterface.FirstFragmentInterface
import java.util.*

class FirstFragment: MvpAppCompatFragment(), FirstFragmentInterface, SwipeRefreshLayout.OnRefreshListener{

    @InjectPresenter
    lateinit var fragmentPresenter: FirstFragmentPresenter

    @BindView(R.id.progress)
    lateinit var progress: ProgressBar

    @BindView(R.id.recycler_container)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @BindView(R.id.recycler)
    lateinit var recyclerView: RecyclerView

    private var adapter: Adapter?= null

    companion object {
        fun newInstance(): FirstFragment {
            return FirstFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.first_layaout, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linerManager = LinearLayoutManager(context)
        linerManager.orientation = LinearLayoutManager.VERTICAL

        swipeRefreshLayout.setOnRefreshListener(this)

        adapter = Adapter(fragmentPresenter.getList(), context!!)
        recyclerView.layoutManager = linerManager
        recyclerView.adapter = adapter

    }

    override fun onRefresh() {
        // region test BD
        val res = UserDBHelper.setUser("John1"+ Random().nextInt())
        if (res == (-1).toLong()) {
            Log.d("Insert to BD", "Error Insert")
        }

        val user: UserDBModel? = UserDBHelper.getByName(5)
        Log.d("GET USER", "user name: ${user?.name}, " +
                "user_id = ${user?.id}, user_insert_date is ${user?.insertIs?.time}")
        // endregion
        fragmentPresenter.checkConnection()
    }

    override fun refreshOut() {
        adapter!!.list = fragmentPresenter.getList()
        adapter!!.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showNetworkMessage(message: Int) {
        Toast.makeText(context, resources.getString(message), Toast.LENGTH_SHORT).show()
    }

    override fun updateAdapter() {
        adapter!!.list = fragmentPresenter.getList()
        adapter!!.notifyDataSetChanged()
    }

    override fun updateLoading(load: Boolean) {
        if (load)
            progress.visibility = View.VISIBLE
        else
            progress.visibility = View.GONE
    }
}