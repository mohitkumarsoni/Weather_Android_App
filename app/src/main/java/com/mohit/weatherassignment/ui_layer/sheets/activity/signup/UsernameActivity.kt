package com.mohit.weatherassignment.ui_layer.sheets.activity.signup

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.mohit.weatherassignment.databinding.ActivityUsernameBinding


class UsernameActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityUsernameBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.nextBtn.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            if(checkEmailValidation(email)){
                startActivity(Intent(this, PasswordActivity::class.java).putExtra("email",email))
            }else binding.emailEt.error = "enter valid input"
        }

    }

    private fun checkEmailValidation(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}