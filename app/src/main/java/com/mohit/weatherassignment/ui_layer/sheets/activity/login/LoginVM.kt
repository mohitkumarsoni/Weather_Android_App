package com.mohit.weatherassignment.ui_layer.sheets.activity.login

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.core.view.isVisible
import androidx.lifecycle.AndroidViewModel
import com.mohit.weatherassignment.data.firebase.F_AUTH
import com.mohit.weatherassignment.databinding.ActivityLoginBinding
import com.mohit.weatherassignment.shortToast
import com.mohit.weatherassignment.ui_layer.sheets.activity.main.MainActivity
import com.mohit.weatherassignment.ui_layer.sheets.activity.signup.UsernameActivity

class LoginVM(private var application: Application) : AndroidViewModel(application) {

    fun userLogin(context: Context ,email: String, password: String, binding: ActivityLoginBinding, function:(Boolean)->Unit) {
        F_AUTH.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                shortToast(context, "Login Successful")
                binding.progressbar.isVisible = false
                function(true)
//                context.startActivity(Intent(context, MainActivity::class.java))
//                finish()
            }
            if (it.isSuccessful.not()) {
                binding.progressbar.isVisible = false
                shortToast(application, "Login Failed, recheck login credentials")
                function(false)
            }
        }
    }

    fun gotoSignupPage(context: Context) {
        context.startActivity(Intent(context, UsernameActivity::class.java))
    }

}