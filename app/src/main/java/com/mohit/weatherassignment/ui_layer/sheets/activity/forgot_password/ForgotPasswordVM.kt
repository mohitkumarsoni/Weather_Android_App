package com.mohit.weatherassignment.ui_layer.sheets.activity.forgot_password

import android.app.Application
import android.content.Context
import androidx.core.view.isVisible
import androidx.lifecycle.AndroidViewModel
import com.mohit.weatherassignment.data.firebase.F_AUTH
import com.mohit.weatherassignment.databinding.ActivityForgotPasswordBinding
import com.mohit.weatherassignment.shortToast

class ForgotPasswordVM(application: Application) : AndroidViewModel(application) {

    fun sendPasswordResendLink(context:Context ,email:String, binding: ActivityForgotPasswordBinding){
        F_AUTH.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful){
                shortToast(context, "check email to reset password")
                binding.progressbar.isVisible = false
            }else{
                shortToast(context, "something went wrong")
                binding.progressbar.isVisible = false
            }
        }
    }

}