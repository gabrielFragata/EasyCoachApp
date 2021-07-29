package br.com.fiap.easycoachapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.fiap.easycoachapp.R
import br.com.fiap.easycoachapp.databinding.ActivityLoginBinding
import br.com.fiap.easycoachapp.ui.schedule.ScheduleActivity
import br.com.fiap.easycoachapp.viewModel.login.LoginContract
import br.com.fiap.easycoachapp.viewModel.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LoginActivity: AppCompatActivity(), LoginContract {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        viewModel.onCreate()
        setupView()
    }

    private fun setupView() {
        btSignIn.setOnClickListener { viewModel.onLoginPressed() }
        tvSignUp.setOnClickListener { viewModel.onSignUpPressed() }
        tvForgotPassword.setOnClickListener { viewModel.onForgotPasswordPressed() }
        ivFacebookLogin.setOnClickListener { soonMessage() }
        ivGoogleLogin.setOnClickListener { soonMessage() }
        ivLinkedinLogin.setOnClickListener { soonMessage() }
    }

    override fun goToHomeActivity() {
        startActivity(Intent(this, ScheduleActivity::class.java))
        finish()
    }

    override fun goToSignUpActivity() {
        soonMessage()
    }

    override fun showErrorMessage() {
        Toast.makeText(this, "E-mail/senha incorretos.", Toast.LENGTH_SHORT).show()
    }

    override fun goToForgotPassword() {
        soonMessage()
    }

    private fun soonMessage() {
        Toast.makeText(this, "Em breve...", Toast.LENGTH_SHORT).show()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }


    // Initialize Facebook Login button
    callbackManager = CallbackManager.Factory.create()

    buttonFacebookLogin.setReadPermissions("email", "public_profile")
    buttonFacebookLogin.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {
            Log.d(TAG, "facebook:onSuccess:$loginResult")
            handleFacebookAccessToken(loginResult.accessToken)
        }

        override fun onCancel() {
            Log.d(TAG, "facebook:onCancel")
        }

        override fun onError(error: FacebookException) {
            Log.d(TAG, "facebook:onError", error)
        }
    })
    // ...
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}

