package com.mohit.weatherassignment.ui_layer.sheets.activity.forgot_password

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.mohit.weatherassignment.R
import com.mohit.weatherassignment.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityForgotPasswordBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel : ForgotPasswordVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[ForgotPasswordVM::class.java]

        binding.passResetBtn.setOnClickListener {
            binding.progressbar.isVisible = true
            viewModel.sendPasswordResendLink(this, binding.emailEt.text.toString().trim(), binding)
        }
    }
}