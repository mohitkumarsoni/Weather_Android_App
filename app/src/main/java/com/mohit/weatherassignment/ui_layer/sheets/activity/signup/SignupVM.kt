package com.mohit.weatherassignment.ui_layer.sheets.activity.signup

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.AndroidViewModel
import com.mohit.weatherassignment.data.firebase.F_AUTH
import com.mohit.weatherassignment.databinding.ActivitySignUpBinding
import com.mohit.weatherassignment.shortToast
import com.mohit.weatherassignment.ui_layer.sheets.activity.main.MainActivity

class SignupVM(private var application: Application) : AndroidViewModel(application) {

    fun validateCredentialsAndSignup( context: Context ,email:String, password:String, location:String, binding: ActivitySignUpBinding, function:(Boolean)->Unit){
        if (location.isNotEmpty()){
            F_AUTH.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    binding.progressbar.isVisible = false
//                    context.startActivity(Intent(context, MainActivity::class.java))
                    shortToast(context, "Login Successful")
                    function(true)
                }
                if (it.isSuccessful.not()){
                    binding.progressbar.isVisible = false
                    shortToast(context, "something went trong, try again !")
                    function(false)
                }
            }

        }else shortToast(context, "select location")
    }

    fun saveLocationSharedPref(context: Context,location:String){
        val sharedPreferences = context.getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()

        myEdit.putString("locationSP", location)
        myEdit.apply()
    }

}