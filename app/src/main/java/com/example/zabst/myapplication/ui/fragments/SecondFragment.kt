package com.example.zabst.myapplication.ui.fragments

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.dd.processbutton.iml.ActionProcessButton
import com.example.zabst.myapplication.R
import com.example.zabst.myapplication.presener.SecondFragmentPresenter
import com.example.zabst.myapplication.presenterinterface.SecondFragmentInterface
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class SecondFragment: MvpAppCompatFragment(), SecondFragmentInterface, View.OnClickListener {

    @InjectPresenter
    lateinit var secondFragmentPresenter: SecondFragmentPresenter

    @BindView(R.id.appCompatButton)
    lateinit var progressButton: ActionProcessButton

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mLocationManager: LocationManager

    private var baseActivity: MvpAppCompatActivity? = null

    companion object {
        fun newInstance(activity: MvpAppCompatActivity): SecondFragment {
            val fragment = SecondFragment()
            fragment.baseActivity = activity
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.second_layout, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context!!)

        mLocationManager = baseActivity!!.getSystemService(LOCATION_SERVICE) as LocationManager

        progressButton.setOnClickListener(this)
        progressButton.setMode(ActionProcessButton.Mode.ENDLESS)
    }

    private fun isGeoDisable(): Boolean {
        val isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return !isGPSEnabled && !isNetEnabled
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            progressButton.id -> {
//                val mailIntent = Intent(Intent.ACTION_SENDTO)
//                mailIntent.data = Uri.parse("mailto:nevskiievgeny@gmail.com")
//                startActivity(mailIntent)
                if (isGeoDisable()) {
                    val alertDialog: AlertDialog = AlertDialog.Builder(context!!)
                            .setTitle(R.string.title_location_dialog)
                            .setMessage(R.string.text_location_dialog)
                            .setPositiveButton("OK", { _, _ ->
                                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                            })
                            .setNegativeButton("Cancel", { dialog, _ ->
                                dialog.dismiss()
                            })
                            .create()
                    alertDialog.show()
                } else {
                    progressButton.progress = 1
                    secondFragmentPresenter.getLocations(fusedLocationProviderClient, baseActivity!!, context!!,
                            mLocationManager)
                }
            }
        }
    }


    override fun progressButtonAndGetLocation(mLocation: Location?, isLocation: Boolean) {
        if (!isLocation) {
            progressButton.progress = 0
            return
        }

        progressButton.progress = 0
        val str = "latitude: ${mLocation?.latitude} and longitude: ${mLocation?.longitude}"
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(baseActivity!!,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }
        if (!isGeoDisable()) {
            secondFragmentPresenter.updateLocation(context!!, mLocationManager)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            0 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // done
                } else {
                    Toast.makeText(context, "Need your location!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}