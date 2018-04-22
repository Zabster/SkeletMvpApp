package com.example.zabst.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.zabst.myapplication.R
import com.example.zabst.myapplication.app.App
import com.example.zabst.myapplication.presener.LoginPresenter
import com.example.zabst.myapplication.presenterinterface.LoginActivityInterface
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import net.steamcrafted.materialiconlib.MaterialIconView
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class LoginActivity : MvpAppCompatActivity(), LoginActivityInterface, View.OnClickListener {

    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    @BindView(R.id.login_btn)
    lateinit var loginBtn: Button

    // facebook
    @BindView(R.id.buttonFacebook)
    lateinit var facebookLogin: LoginButton
    @BindView(R.id.iconFacebook)
    lateinit var facebookButton: MaterialIconView

    //google
    @BindView(R.id.iconGooglePlus)
    lateinit var googleButton: MaterialIconView

    lateinit var callbackManager: CallbackManager
    lateinit var mGoogleSignInClient: GoogleSignInClient

    lateinit var accountGoogle: GoogleSignInAccount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)

        App.getInstance()!!.getAppComponent()!!.inject(this)

        loginBtn.setOnClickListener(this)
        facebookButton.setOnClickListener(this)
        googleButton.setOnClickListener(this)

        //facebook
        facebookLogin.setReadPermissions("email")
        callbackManager = CallbackManager.Factory.create()

        //google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken("server") todo need token from server
                .requestId()
                .requestProfile()
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            loginBtn.id -> {
                val intent = Intent(this@LoginActivity, BaseActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            facebookButton.id -> {
                if (AccessToken.getCurrentAccessToken() == null) {
                    facebookLogin.performClick()
                    LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                        override fun onSuccess(result: LoginResult?) {
                            Log.d("LOGINMANAGER succes", "result:\n token: ${result?.accessToken}")
                            val token: AccessToken? = result?.accessToken
                            if (token != null) {
                                Log.d("USER ID", "user id is ${token.userId}, and ${token.source.name}")
                                val intent = Intent(this@LoginActivity, BaseActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                        }

                        override fun onCancel() {

                        }

                        override fun onError(error: FacebookException?) {
                            Log.d("LOGINMANAGER succes", "result:\n token: ${error?.message}")
                        }

                    })
                } else {
                    LoginManager.getInstance().logInWithReadPermissions(this, arrayListOf("email"))
                    val intent = Intent(this@LoginActivity, BaseActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            googleButton.id -> {
                signGoogleIn()
            }
        }
    }

    private fun signGoogleIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1200) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                accountGoogle = task.getResult(ApiException::class.java)
                Log.d("GOOGLE", "account is ${accountGoogle.email} and token: ${accountGoogle.idToken}, id: ${accountGoogle.id}")
                val s = "account is ${accountGoogle.email} and token: ${accountGoogle.idToken}, id: ${accountGoogle.id}"
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
            } catch (e: ApiException) {
                Log.w("GoogleException", "signInResult:failed code= ${e.statusCode}" )
            }
        }
    }
}
