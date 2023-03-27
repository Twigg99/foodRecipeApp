package com.example.foodrecipeapp

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //To create a fullscreen for the splash screen the status bar at the top is hidden
        @Suppress("DEPRECATION")
        //setflags is a deprecated method
        //Checks the version of the android operating system on the device
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        //using a handler to post a delayed message on the main thread, which is to use an
        //intent to launch a new activity
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            Thread.sleep(3000)
            //implicit intent
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}