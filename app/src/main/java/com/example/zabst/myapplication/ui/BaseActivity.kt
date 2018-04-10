package com.example.zabst.myapplication.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.zabst.myapplication.R
import com.example.zabst.myapplication.adapter.Adapter
import com.example.zabst.myapplication.app.App
import com.example.zabst.myapplication.helper.SharedPreferencesHelper
import com.example.zabst.myapplication.presener.BasePresenter
import com.example.zabst.myapplication.presenterinterface.BaseActivityInterfase
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import javax.inject.Inject

class BaseActivity : MvpAppCompatActivity(), BaseActivityInterfase, View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @InjectPresenter
    lateinit var basePresenter: BasePresenter

    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    @BindView(R.id.progress)
    lateinit var progress: ProgressBar

    @BindView(R.id.recycler)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.appCompatButton)
    lateinit var appCompatButton: AppCompatButton

    private var adapter: Adapter ?= null

    lateinit var googleApiClient: GoogleApiClient
    private var mLocation : Location ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        App.getInstance()!!.getAppComponent()!!.inject(this)
        ButterKnife.bind(this)

        val linerManager = LinearLayoutManager(this)
        linerManager.orientation = LinearLayoutManager.VERTICAL

        sharedPreferencesHelper.saveStringValueByKey("key", "value")

        adapter = Adapter(basePresenter.getList(), this)
        recyclerView.layoutManager = linerManager
        recyclerView.adapter = adapter

        appCompatButton.setOnClickListener(this)

        googleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        googleApiClient.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("Google connect", "cod: ${p0.errorCode}, message: ${p0.errorMessage}")
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        mLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)

        if (mLocation != null) {
            Log.d("Location", "latitude: ${mLocation?.latitude} and longitude: ${mLocation?.longitude}")
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        if (!googleApiClient.isConnected)
            googleApiClient.connect()
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            appCompatButton.id -> {
                val str = "latitude: ${mLocation?.latitude} and longitude: ${mLocation?.longitude}"
                Toast.makeText(this@BaseActivity, str, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            0 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // done
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun showNetworkMessage(res: Int) {
        Toast.makeText(this, resources.getString(res), Toast.LENGTH_SHORT).show()
    }

    override fun updateAdapter() {
        adapter!!.list = basePresenter.getList()
        adapter!!.notifyDataSetChanged()

        Log.d("Shared value", "Shared value: ${sharedPreferencesHelper.getStringValueByKey("key")}")
    }

    override fun updateLoading(load: Boolean) {
        if (load)
            progress.visibility = View.VISIBLE
        else
            progress.visibility = View.GONE
    }


}
