package com.sourav.rxnet.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.sourav.rxnet.R

object Common {
    /** Checking Network State */
    fun isNetworkAvailable(context: Context) : Boolean {
        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // after Q version
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    result = when{
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
            } else {  // VERSION.SDK_INT < M"
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                return activeNetworkInfo?.isConnected ?: result
            }
        }
        return result
    }

    fun View.showSnackBar(msg : String){
        Snackbar.make(this, msg, Snackbar.LENGTH_LONG).also { snackbar ->
            snackbar.setAction("OK"){
                snackbar.dismiss()
            }
        }.show()
    }

    @SuppressLint("WrongConstant", "ResourceAsColor")
    @JvmStatic
    fun onSNACK(view: View, msg: String){
        //Snackbar(view)
        val snackbar = Snackbar.make(view, msg,
            Snackbar.ANIMATION_MODE_SLIDE).setAction("Action", null)
        snackbar.setActionTextColor(R.color.colorWhite)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundResource(R.color.colorPrimary)
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.RED)
        textView.textSize = 24f
        snackbar.show()
    }

}