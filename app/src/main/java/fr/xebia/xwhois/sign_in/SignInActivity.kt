package fr.xebia.xwhois.sign_in

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import fr.xebia.xwhois.R
import fr.xebia.xwhois.tool.SharePreferenceTool
import kotlinx.android.synthetic.main.activity_sign_in.*
import timber.log.Timber

class SignInActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private val RC_SIGN_IN: Int = 9001
    private val XEBIA_EMAIL_SUFFIX: String = "@xebia.fr"

    lateinit private var googleApiClient: GoogleApiClient
    lateinit private var preferenceTool: SharePreferenceTool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()

        googleApiClient = GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build()

        preferenceTool = SharePreferenceTool(this)

        signInButton.setOnClickListener {
            signIn()
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult?) {
        Timber.e("Cannot sign in $p0")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }

    override fun onBackPressed() {
        // Back press disabled sign in required
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        Timber.i("Sign in ${result.isSuccess}")
        if (result.isSuccess) {
            val account = result.signInAccount
            Timber.i("${account.displayName} ${account.email}")
            if (account.email.endsWith(XEBIA_EMAIL_SUFFIX)) {
                Toast.makeText(this, account.displayName, Toast.LENGTH_SHORT).show()
                preferenceTool.signIn(account.displayName, account.email)
                finish()
            } else {
                signOut()
                onLoginError()
            }
        } else {
            onLoginError()
        }
    }

    private fun signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback {
            Timber.i("Result ${it.statusMessage}")
        }
    }

    private fun onLoginError() {
        Timber.e("Cannot sign in")
        cannotSignInText.visibility = View.VISIBLE
    }

    private fun signIn() {
        val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(intent, RC_SIGN_IN)
    }

}
