package com.example.zabst.myapplication.presener

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.zabst.myapplication.R
import com.example.zabst.myapplication.app.App
import com.example.zabst.myapplication.model.responsemodel.ResponseModel
import com.example.zabst.myapplication.net.RestApi
import com.example.zabst.myapplication.presenterinterface.BaseActivityInterfase
import com.example.zabst.myapplication.reciver.ConnectivityReceiver
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

@InjectViewState
class BasePresenter: MvpPresenter<BaseActivityInterfase>(), ConnectivityReceiver.ConnectivityRecieverListener {

    @Inject
    lateinit var retrofit: Retrofit

    private var list: List<ResponseModel> = arrayListOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        App.getInstance()!!.getAppComponent()!!.inject(this)
        checkConnection()
    }

    override fun onNetworkConnectionChange(isConnected: Boolean) {
        if (isConnected)
            viewState.showNetworkMessage(R.string.network_message_success)
    }

    private fun checkConnection() {
        val isConnected: Boolean = ConnectivityReceiver.isConnected()
        if (!isConnected) {
            viewState.showNetworkMessage(R.string.network_message_error)
        } else {
            success()
        }
    }

    fun getList(): List<ResponseModel> {
        return list as ArrayList<ResponseModel>
    }

    private fun success() {
        retrofit.create(RestApi::class.java).getData("bash", 50)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object: Observer<List<ResponseModel>> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: List<ResponseModel>) {
                        (list as ArrayList<ResponseModel>).addAll(t)
                        viewState.updateLoading(false)
                        viewState.updateAdapter()
                    }

                    override fun onError(e: Throwable) {
                        Log.wtf("Something Wrong", "error: ${e.printStackTrace()}, info: ${e.message}")
                    }
                })
    }
}
