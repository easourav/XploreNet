package com.sourav.rxnet.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sourav.rxnet.R
import com.sourav.rxnet.utils.Common
import kotlinx.android.synthetic.main.activity_no_internet.*

class NoInternetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet)

        exitAppBtn.setOnClickListener {
            finish()
        }

        tryAgainButton.setOnClickListener {
            if(Common.isNetworkAvailable(this)){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                Common.onSNACK(
                    main_view,
                    "Please connect internet with your device"
                )
            }
        }
    }
}