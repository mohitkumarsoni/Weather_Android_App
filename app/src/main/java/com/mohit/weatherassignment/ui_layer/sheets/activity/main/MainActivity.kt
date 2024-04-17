package com.mohit.weatherassignment.ui_layer.sheets.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mohit.weatherassignment.data.remote.repository.WeatherRepo
import com.mohit.weatherassignment.databinding.ActivityMainBinding
import com.mohit.weatherassignment.ui_layer.model.Weather
import com.mohit.weatherassignment.ui_layer.sheets.activity.profile.ProfileActivity
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: MainVM
    private lateinit var weatherLocation: String
    val currentDayOfWeek: String = LocalDate.now().dayOfWeek.toString()
    private val upcomingDays = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val factory = MainViewModelFactory(application, WeatherRepo())
        viewModel = ViewModelProvider(this@MainActivity, factory)[MainVM::class.java]
        getWeatherLocation()
        getUpcomingDays()
        viewModel.getWeatherInfo(weatherLocation)

        binding.apply {
            menuBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
            }

            viewModel.weather.observe(this@MainActivity) {
                it?.let {
                    assignValueToViews(it)
                }
            }
        }
    }

    private fun getWeatherLocation() {
        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        weatherLocation = sh.getString("locationSP", "").toString()
    }

    private fun getUpcomingDays() {
        for (i in 0..6) {
            val x = LocalDate.now().plusDays(i.toLong()).dayOfWeek.toString()
            upcomingDays.add(x)
        }
    }

    private fun assignValueToViews(it: Weather) {
        binding.apply {
            temperatureTv.text = kelvinToCelsius(it.main.temp)
            weatherTypeTv.text = "${it.weather[0].main}"
            minTempTv.text = kelvinToCelsius(it.main.temp_min)
            maxTempTv.text = kelvinToCelsius(it.main.temp_max)

//            timeSeekbar.min = getHourAndMinuteAsInt(it.main.temp_min.toLong())
//            timeSeekbar.max = getHourAndMinuteAsInt(it.main.temp_max.toLong())
//            timeSeekbar.progress = getHourAndMinuteAsInt(System.currentTimeMillis())
//
//            Log.d("TAG","min "+ getHourAndMinuteAsInt(it.main.temp_min.toLong()).toString())
//            Log.d("TAG", "max "+getHourAndMinuteAsInt(it.main.temp_max.toLong()).toString() )
//            Log.d("TAG","current "+getHourAndMinuteAsInt(System.currentTimeMillis()).toString())

//            timeSeekbar.min = getHourAndMinute(it.main.temp_min.toLong()).first
//            timeSeekbar.max = getHourAndMinute(it.main.temp_max.toLong()).first
//            timeSeekbar.progress = getHourAndMinute(System.currentTimeMillis()).first

//            Log.d("TAG","min "+ getHourAndMinute(it.main.temp_min.toLong()).first)
//            Log.d("TAG", "max "+getHourAndMinute(it.main.temp_max.toLong()).first )
//            Log.d("TAG","current "+getHourAndMinute(System.currentTimeMillis()).first)

//            timeSeekbar.isEnabled = false

            temp1.text = kelvinToCelsius(it.main.temp)
            temp2.text = kelvinToCelsius(it.main.temp_min)
            temp3.text = kelvinToCelsius(it.main.temp_min)
            temp4.text = kelvinToCelsius(it.main.temp)
            temp5.text = kelvinToCelsius(it.main.temp_min)
            temp6.text = kelvinToCelsius(it.main.temp_max)

            day2.text = upcomingDays[2]
            day3.text = upcomingDays[3]
            day4.text = upcomingDays[4]
            day5.text = upcomingDays[5]
            day6.text = upcomingDays[6]

            feelsLike1.text = "${it.main.feels_like}°"
            feelsLike2.text = "${it.main.feels_like}°"

            humidity1.text = "${it.main.humidity}%"
            humidity2.text = "${it.main.humidity}%"

            sunriseTimeTv.text = formatTime(it.sys.sunrise.toLong())
            sunsetTimeTv.text = formatTime(it.sys.sunset.toLong())

            locationTv.text = weatherLocation
            dateAndTimeTv.text = getCurrentDateTime()

            min1.text = kelvinToCelsius(it.main.temp_min)
            max1.text = kelvinToCelsius(it.main.temp_max)
            min2.text = kelvinToCelsius(it.main.temp_min)
            max2.text = kelvinToCelsius(it.main.temp_max)
            min3.text = kelvinToCelsius(it.main.temp_min)
            max3.text = kelvinToCelsius(it.main.temp_max)
            min4.text = kelvinToCelsius(it.main.temp_min)
            max4.text = kelvinToCelsius(it.main.temp_max)
            min5.text = kelvinToCelsius(it.main.temp_min)
            max5.text = kelvinToCelsius(it.main.temp_max)
            min6.text = kelvinToCelsius(it.main.temp_min)
            max6.text = kelvinToCelsius(it.main.temp_max)

        }
    }

    private fun formatTime(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val sdf = SimpleDateFormat("hh:mm a", Locale.US)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    private fun kelvinToCelsius(kelvin: Double): String {
        val x = kelvin - 273.15
        val y = "%.1f".format(x)
        return y.plus("°")
    }

    private fun getCurrentDateTime(): String {
        return currentDay() + ", " + getCurrentDate() + " " + getCurrentMonth() + " | " + getCurrentTime()
    }

    private fun currentDay(): String {
        return LocalDate.now().dayOfWeek.toString()
    }

    private fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(calendar.time)
    }

    private fun getCurrentMonth(): String {
        val currentDate = LocalDate.now()
        return currentDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    private fun getCurrentDate(): Int {
        val currentDate = LocalDate.now()
        return currentDate.dayOfMonth
    }

    fun getHourAndMinute(timestamp: Long): Pair<Int, Int> {
        val localDateTime =
            LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
        val hour = localDateTime.hour
        val minute = localDateTime.minute
        return Pair(hour, minute)
    }

    fun getHourAndMinuteAsInt(timestamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return hour * 100 + minute
    }


}