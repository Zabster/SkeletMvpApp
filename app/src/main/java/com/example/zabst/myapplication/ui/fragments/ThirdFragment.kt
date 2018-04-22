package com.example.zabst.myapplication.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.zabst.myapplication.R
import com.example.zabst.myapplication.presener.ThridFragmentPresenter
import com.example.zabst.myapplication.presenterinterface.ThridFragmentInterface
import com.example.zabst.myapplication.ui.LoginActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.widget.ProfilePictureView
import net.steamcrafted.materialiconlib.MaterialIconView

class ThirdFragment: MvpAppCompatFragment(), ThridFragmentInterface {

    @InjectPresenter
    lateinit var presenter: ThridFragmentPresenter

    @BindView(R.id.profilePictureView)
    lateinit var picture: ProfilePictureView

    @BindView(R.id.FName)
    lateinit var fName: AppCompatTextView

    @BindView(R.id.FSurname)
    lateinit var fSurname: AppCompatTextView

    @BindView(R.id.FAge)
    lateinit var fAge: AppCompatTextView

    @BindView(R.id.logoutFacebook)
    lateinit var fLogout: MaterialIconView

    private lateinit var callbackManager: CallbackManager
    private lateinit var profile: Profile

    companion object {
        fun newInstance(): ThirdFragment {
            return ThirdFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.third_layout, container, false)
        ButterKnife.bind(this, view)
        callbackManager = CallbackManager.Factory.create()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = AccessToken.getCurrentAccessToken()
        if (token != null) {
            setInfo(token)
        }

        fLogout.setOnClickListener {
            if (token != null) {
                LoginManager.getInstance().logOut()
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setInfo(token: AccessToken) {
        profile = Profile.getCurrentProfile()
        picture.profileId = token.userId

        fName.text = "Name: ${profile.firstName}"
        fSurname.text = "Surname: ${profile.lastName}"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}