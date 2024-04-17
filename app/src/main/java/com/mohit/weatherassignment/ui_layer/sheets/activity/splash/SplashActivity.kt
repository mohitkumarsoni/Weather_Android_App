package com.mohit.weatherassignment.ui_layer.sheets.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mohit.weatherassignment.data.firebase.F_AUTH
import com.mohit.weatherassignment.databinding.ActivitySplashBinding
import com.mohit.weatherassignment.ui_layer.sheets.activity.login.LoginActivity
import com.mohit.weatherassignment.ui_layer.sheets.activity.main.MainActivity

class SplashActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(Runnable {
            if (F_AUTH.currentUser != null){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        },800)

    }
}