package com.mohit.weatherassignment.ui_layer.sheets.activity.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mohit.weatherassignment.data.remote.repository.WeatherRepo
import com.mohit.weatherassignment.ui_layer.model.Weather
import kotlinx.coroutines.launch
import retrofit2.Response

class MainVM(application: Application, private var repository:WeatherRepo) : AndroidViewModel(application) {
    var weather = MutableLiveData<Weather>()

    init {
        getWeatherInfo()
    }

    fun getWeatherInfo(location:String?="mumbai") = viewModelScope.launch {
        handleWeatherInfo(repository.getWeatherInfo(location))
    }

    private fun handleWeatherInfo(response:Response<Weather>){
        if (response.isSuccessful){
            weather.postValue(response.body())
        }
    }

}