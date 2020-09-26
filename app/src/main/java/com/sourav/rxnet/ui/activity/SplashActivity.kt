package com.sourav.rxnet.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sourav.rxnet.R
import com.sourav.rxnet.utils.Common

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if(Common.isNetworkAvailable(this)){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, NoInternetActivity::class.java))
                finish()
            }
        }, 1500)
    }
}