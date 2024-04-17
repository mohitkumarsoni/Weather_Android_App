package com.mohit.weatherassignment.data.remote.db

import com.mohit.weatherassignment.data.remote.WEATHER_API_KEY
import com.mohit.weatherassignment.ui_layer.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherInfo(
        @Query("q") location: String? = "mumbai",
        @Query("appid") apiKey: String = WEATHER_API_KEY
    ): Response<Weather>

}