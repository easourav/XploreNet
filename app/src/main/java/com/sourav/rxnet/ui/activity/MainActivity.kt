package com.sourav.rxnet.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sourav.rxnet.R
import com.sourav.rxnet.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var fragment: Fragment? = null
    var fm = supportFragmentManager
    private var homeFragment = HomeFragment()
    private var doubleBackToExitPressedOnce = true


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_frame, HomeFragment(), HomeFragment::class.java.simpleName)
                .commit()
            fragment = HomeFragment()
        }

        bottom_navigation.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.action_home -> {
                    setFragment(homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_support -> {
                    /*fragment = SupportFragment()
                    setFragment(fragment!!)*/

                    val intent = Intent(applicationContext, WebViewActivity::class.java)
                    intent.putExtra("url", "https://rsnetbd.com/")
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setFragment(setFragment: Fragment) = try {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_frame, setFragment)
        fragmentTransaction.commit()
        fragmentTransaction.addToBackStack(setFragment.tag)
        fragmentTransaction.setCustomAnimations(
            android.R.animator.fade_in,
            android.R.animator.fade_out
        )
        fragment = setFragment
    }catch (e: Exception){
        Log.e("Bottom nav", fragment.toString() + " " + e.message.toString())
    }


    override fun onBackPressed() {
        //super.onBackPressed()
        setDoubleTapExit()
        /*if (fragment is HomeFragment) {

        } else {
            if (fm.backStackEntryCount > 0) {
                if (fragment is HomeFragment){
                    setDoubleTapExit()
                }
                else{
                    setFragment(homeFragment)
                }
            }
        }*/
    } // onBackPressed

    private fun setDoubleTapExit() {
        if (doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = false

            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ doubleBackToExitPressedOnce = true }, 2000)
        } else {
            moveTaskToBack(true)
        }
    }
}