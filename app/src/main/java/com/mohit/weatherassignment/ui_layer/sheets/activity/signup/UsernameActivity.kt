package com.mohit.weatherassignment.ui_layer.sheets.activity.signup

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mohit.weatherassignment.R
import com.mohit.weatherassignment.databinding.ActivityUsernameBinding


class UsernameActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityUsernameBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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