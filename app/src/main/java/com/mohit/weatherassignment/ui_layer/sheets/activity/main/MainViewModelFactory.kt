package com.mohit.weatherassignment.ui_layer.sheets.activity.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohit.weatherassignment.data.remote.repository.WeatherRepo

class MainViewModelFactory(var app: Application, var repo: WeatherRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainVM(app, repo) as T
    }
}