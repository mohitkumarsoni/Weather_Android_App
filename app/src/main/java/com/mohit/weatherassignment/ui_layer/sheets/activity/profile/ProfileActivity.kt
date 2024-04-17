package com.mohit.weatherassignment.ui_layer.sheets.activity.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mohit.weatherassignment.R
import com.mohit.weatherassignment.data.firebase.F_AUTH
import com.mohit.weatherassignment.data.firebase.model.User
import com.mohit.weatherassignment.databinding.ActivityProfileBinding
import com.mohit.weatherassignment.shortToast
import com.mohit.weatherassignment.ui_layer.sheets.activity.main.MainActivity
import com.mohit.weatherassignment.ui_layer.sheets.activity.splash.SplashActivity

class ProfileActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    private lateinit var email: String
    private lateinit var selectedCity: String
    private val cityList: List<String> = getCityList()
    private lateinit var viewModel: ProfileVM
    private var submitBrnFlag = false

    private var userInfo = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ProfileVM::class.java]

        editBtnVisible()
        viewModel.getSharedPrefData(this, binding, getCityList())
        loadFirebaseData()
        email = F_AUTH.currentUser?.email.toString()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cityList)
        binding.citySpinner.adapter = adapter
        binding.citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCity = cityList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        binding.apply {

            editBtn.setOnClickListener {
                submitBtnVisible()

            }

            submitBtn.setOnClickListener {
                val location = selectedCity
                val username = usernameEt.text.toString().trim()
                val phone = phoneEt.text.toString().trim()

                viewModel.setSharedPrefData(this@ProfileActivity, location, username, phone)
                userInfo.username = username
                userInfo.phone = phone
                userInfo.location = location

                viewModel.saveUserInfoToFirebase(this@ProfileActivity,F_AUTH.currentUser?.email.toString(),userInfo) {
                    if (it) {
                        finishAffinity()
                        startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
                    }
                }

                editBtnVisible()
            }

            profilePicIv.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            logoutBtn.setOnClickListener {

                val builder = MaterialAlertDialogBuilder(this@ProfileActivity)
                    .setTitle("Warning !")
                    .setMessage("Do you want to logout ?")
                    .setPositiveButton("Logout") { dialog, which ->
                        F_AUTH.signOut()
                        finishAffinity()
                        startActivity(Intent(this@ProfileActivity, SplashActivity::class.java))
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                        dialog.dismiss()
                    }
                    .setCancelable(false)

                val dialog = builder.create()
                dialog.show()

            }

        }

    }

    private fun editBtnVisible(): Boolean {
        binding.apply {
            editBtn.isVisible = true
            logoutBtn.isVisible = true
            submitBtn.isVisible = false

            usernameEt.isEnabled = false
            phoneEt.isEnabled = false
            citySpinner.isEnabled = false
            profilePicIv.isEnabled = false

            submitBrnFlag = false
        }
        return true
    }

    private fun submitBtnVisible(): Boolean {
        binding.apply {
            editBtn.isVisible = false
            logoutBtn.isVisible = false
            submitBtn.isVisible = true

            usernameEt.isEnabled = true
            phoneEt.isEnabled = true
            citySpinner.isEnabled = true
            profilePicIv.isEnabled = true

            submitBrnFlag = true
        }
        return true
    }

    // Registers a photo picker activity launcher in single-select mode.
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.saveImageToFirebase(this, uri) {
                    userInfo.image = it
                    binding.profilePicIv.load(it) {
                        placeholder(R.drawable.loading)
                    }
                }
            } else {
                shortToast(this, "no image selected")
            }
        }

    private fun getCityList(): List<String> {
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

    private fun loadFirebaseData() {
        viewModel.getUserInfoFromFirebase(F_AUTH.currentUser?.email.toString()){
            binding.apply {
                if (it.image == null){
                    profilePicIv.load(R.drawable.no_img)
                }else{
                    profilePicIv.load(it.image)
                    userInfo.image = it.image
                }
                usernameEt.setText(it.username)
                phoneEt.setText(it.phone)
                displayNameTv.text = it.username
            }
        }
    }

    private fun backPressed() {
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (submitBrnFlag) {
                    editBtnVisible()
                }

                if (submitBrnFlag.not()) {
                    finishAffinity()
                    startActivity(Intent(this@ProfileActivity, MainActivity::class.java))

                }

            }
        })
    }

}