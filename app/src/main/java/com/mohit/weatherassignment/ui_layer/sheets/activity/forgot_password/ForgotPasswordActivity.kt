package com.mohit.weatherassignment.ui_layer.sheets.activity.forgot_password

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
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
        viewModel = ViewModelProvider(this)[ForgotPasswordVM::class.java]

        binding.passResetBtn.setOnClickListener {
            binding.progressbar.isVisible = true
            viewModel.sendPasswordResendLink(this, binding.emailEt.text.toString().trim(), binding)
        }
    }
}