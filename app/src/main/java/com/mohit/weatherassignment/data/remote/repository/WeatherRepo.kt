package com.mohit.weatherassignment.data.remote.repository

import com.mohit.weatherassignment.data.remote.db.RetrofitInstance
import com.mohit.weatherassignment.ui_layer.model.Weather
import retrofit2.Response

class WeatherRepo {

    suspend fun getWeatherInfo(location: String?): Response<Weather> = RetrofitInstance.api.getWeatherInfo(location)

}