package com.g2.santurtziapp.activitidades

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.g2.santurtziapp.R


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {

            startActivity(Intent(this, MainActivity::class.java))

        }, SPLASH_TIME_OUT)

        return

    }//onCreate(savedInstanceState: Bundle?)

    override fun onStop() {
        super.onStop()
        this.finish()

    }//onStop()

}///SplashActivity()