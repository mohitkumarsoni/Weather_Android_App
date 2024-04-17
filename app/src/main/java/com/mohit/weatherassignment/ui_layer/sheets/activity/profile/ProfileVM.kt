package com.mohit.weatherassignment.ui_layer.sheets.activity.profile

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage
import com.mohit.weatherassignment.data.firebase.F_AUTH
import com.mohit.weatherassignment.data.firebase.model.User
import com.mohit.weatherassignment.databinding.ActivityProfileBinding
import com.mohit.weatherassignment.shortToast
import com.mohit.weatherassignment.ui_layer.sheets.activity.main.MainActivity
import java.util.UUID

class ProfileVM(application:Application) : AndroidViewModel(application) {

    fun getSharedPrefData(context: Context, binding:ActivityProfileBinding,cityList: List<String>){
        val sh = context.getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)
        val location = sh.getString("locationSP", "")
        val username = sh.getString("usernameSP", "")
        val phone = sh.getString("phoneNumSP", "")
        var count = -1
        var index = 0

        // Setting the fetched data in the EditTexts
        for (i in cityList){
            count++
            if (i == location){
                index = count
            }
        }
        binding.citySpinner.setSelection(index)
        shortToast(context, index.toString())
        binding.usernameEt.setText(username.toString())
        binding.phoneEt.setText(phone.toString())
        binding.emailEt.setText(F_AUTH.currentUser?.email)
    }

    fun setSharedPrefData(context: Context, location:String, username:String, phone:String){
        val sharedPreferences = context.getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putString("locationSP", location)
        myEdit.putString("usernameSP", username)
        myEdit.putString("phoneNumSP", phone)
        myEdit.apply()
    }

    fun saveImageToFirebase(context: Context ,uri: Uri, function:(imageUrl:String)->Unit){
        Firebase.storage.reference.child(UUID.randomUUID().toString()).putFile(uri).addOnCompleteListener{
            if (it.isSuccessful){
                it.result.storage.downloadUrl.addOnSuccessListener{url ->
                    function(url.toString())
                }
                shortToast(context, "profile pic uploaded")
            }else{
                shortToast(context, "failed to upload image")
            }
        }
    }

    fun saveUserInfoToFirebase(context: Context ,userEmail:String, user: User, function:(Boolean)->Unit){
        FirebaseFirestore.getInstance().collection("users").document(userEmail).set(user).addOnCompleteListener {
            if (it.isSuccessful){
                shortToast(context, "details updated")
                function(true)
            }else{
                shortToast(context, "something went wrong")
            }
        }
    }

    fun getUserInfoFromFirebase(email: String, function:(User)->Unit) {
        FirebaseFirestore.getInstance().collection("users").document(email).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val user: User? = it.toObject(User::class.java)
                    user?.let {data-> function(data) }
                }
            }
    }

}