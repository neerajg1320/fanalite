package com.fanalite.rulesapp.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.fanalite.rulesapp.R
import com.fanalite.rulesapp.viewmodels.AuthenticationViewModel
import com.fanalite.rulesapp.viewmodels.SessionManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {
    private val mAuthViewModel: AuthenticationViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sessionManager = SessionManager(this)

        val backgroundImage: View = findViewById(R.id.splashBox)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        backgroundImage.startAnimation(slideAnimation)

        // Right now we will login everytime
        sessionManager.clearAuthToken()

        mAuthViewModel.token.observe(this, Observer { token ->
            sessionManager.saveAuthToken(token)
        })

        if (sessionManager.fetchAuthToken() == null) {
            mAuthViewModel.loginUser("system@abc.com", "System123")
        }

        GlobalScope.launch {
            delay(2000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}

