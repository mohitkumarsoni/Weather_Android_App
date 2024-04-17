package com.mohit.weatherassignment.ui_layer.sheets.activity.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.mohit.weatherassignment.R
import com.mohit.weatherassignment.databinding.ActivityLoginBinding
import com.mohit.weatherassignment.ui_layer.sheets.activity.forgot_password.ForgotPasswordActivity
import com.mohit.weatherassignment.ui_layer.sheets.activity.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel :LoginVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[LoginVM::class.java]

        binding.apply {
            loginBtn.setOnClickListener {
                val email = binding.usernameEt.text.toString().trim()
                val password = binding.passwordEt.text.toString().trim()

                if (usernamePasswordValidation(email, password)){
                    progressbar.isVisible = true
                    viewModel.userLogin(this@LoginActivity, email, password, binding){
                        if (it){
                            finishAffinity()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//                            finish()
                        }
                    }
                }

            }

            signupBtn.setOnClickListener {
                viewModel.gotoSignupPage(this@LoginActivity)
            }

            forgotPasswordBtn.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
            }

        }

    }


    private fun usernamePasswordValidation(username:String, password:String):Boolean{
        if (username.isNotEmpty() || username != ""){
            if (password.isNotEmpty() || password != "" ){
                return true
            }else binding.passwordEt.error = "need valid input"
        }else binding.usernameEt.error = "need valid input"

        return false
    }

}