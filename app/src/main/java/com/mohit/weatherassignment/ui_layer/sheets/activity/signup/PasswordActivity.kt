package com.mohit.weatherassignment.ui_layer.sheets.activity.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mohit.weatherassignment.R
import com.mohit.weatherassignment.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityPasswordBinding.inflate(layoutInflater)
    }
    private lateinit var email:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email = intent.getStringExtra("email").toString()

        binding.apply {

            setPassBtn.setOnClickListener {
                val pass = passwordEt.text.toString().trim()
                val confirmPass = confirmPassEt.text.toString().trim()

                validateAndSetPassword(email, pass, confirmPass)

            }
        }
    }

    private fun validateAndSetPassword(email:String, pas:String, confPas:String){
        if (pas.isNotEmpty()){
            if (confPas.isNotEmpty()){
                if (pas == confPas){
                    startActivity(Intent(this, SignUpActivity::class.java).putExtra("email",email).putExtra("password",pas))
                }else binding.confirmPassEt.error = "password doesn't match"
            }else binding.confirmPassEt.error = "confirm password"
        }else binding.passwordEt.error = "enter password"


    }

}