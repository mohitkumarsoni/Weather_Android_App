package com.mohit.weatherassignment.ui_layer.sheets.activity.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mohit.weatherassignment.databinding.ActivitySignUpBinding
import com.mohit.weatherassignment.ui_layer.sheets.activity.main.MainActivity

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var selectedCity:String
    private lateinit var viewModel: SignupVM
    private val cityList:List<String> = getCityList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SignupVM::class.java]
        email = intent.getStringExtra("email").toString()
        password = intent.getStringExtra("password").toString()
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, cityList)
        binding.citySpinner.adapter = adapter

        binding.citySpinner.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
                selectedCity = cityList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        binding.apply {

            emailEt.setText(email)
            passwordEt.setText(password)
            confirmPassEt.setText(password)

            signupBtn.setOnClickListener {
                viewModel.validateCredentialsAndSignup(this@SignUpActivity,email, password, selectedCity, binding){
                    if (it){
                        viewModel.saveLocationSharedPref(this@SignUpActivity, selectedCity)
                        finishAffinity()
                        startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                    }
                }
            }
        }
    }

    private fun getCityList():List<String>{
        return listOf(
            "Mumbai ",
            "Ahmedabad ",
            "Hyderabad ",
            "Kolkata ",
            "Chennai ",
            "Bangalore ",
            "Pune ",
            "Agra ",
            "Patna ",
            "Indore ",
            "Bhubaneswar ",
            "Chandigarh ",
            "Jaipur ",
            "Kochi ",
            "Surat "
        )
    }

}