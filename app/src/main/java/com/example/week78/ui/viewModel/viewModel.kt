package com.example.week78.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week78.data.container.WeatherContainer
import com.example.week78.ui.model.Cuaca
import kotlinx.coroutines.launch
import kotlin.text.toInt
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.TimeoutCancellationException


sealed class WeatherUIState {
    object Searching : WeatherUIState()
    object Loading : WeatherUIState()
    data class Success(val data: Cuaca) : WeatherUIState()
    data class Error(val message: String) : WeatherUIState()
}

class WeatherViewModel : ViewModel() {
    private val repository = WeatherContainer().repository
    private val apiKey = "e690cdb462d5bc69b474854e2afd7a87"

    var uiState by mutableStateOf<WeatherUIState>(WeatherUIState.Searching)
        private set

    fun searchWeather(city: String) {
        if (city.isBlank()) {
            uiState = WeatherUIState.Searching
            return
        }

        viewModelScope.launch {
            uiState = WeatherUIState.Loading
            try {
                println("Starting search for: $city")
                val response = withTimeout(10000) { // 10 second timeout
                    repository.getCurrentWeather(city, apiKey)
                }
                println("Response received: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    val iconCode = data.weather.firstOrNull()?.icon ?: ""
                    println("Icon code: $iconCode")
                    println("Icon URL: https://openweathermap.org/img/wn/$iconCode@2x.png")


                    val weatherData = Cuaca(
                        kota = data.name,
                        suhu = data.main.temp.toInt(),
                        kelembapan = data.main.humidity,
                        angin = data.wind.speed.toInt(),
                        suhuPerasaan = data.main.feels_like.toInt(),
                        hujanTurun = data.rain?.`1h` ?: 0.0,
                        tekananUdara = data.main.pressure,
                        awan = data.clouds.all,
                        weatherDescription = data.weather.firstOrNull()?.description?.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase() else it.toString()
                        } ?: "Unknown",
                        weatherIcon = iconCode,
                        weatherIconUrl = "https://openweathermap.org/img/wn/$iconCode@2x.png"
                    )

                    uiState = WeatherUIState.Success(weatherData)
                } else {
                    uiState = WeatherUIState.Error("City not found (${response.code()})")
                }
            } catch (e: TimeoutCancellationException) {
                uiState = WeatherUIState.Error("Request timed out. Please try again.")
            } catch (e: Exception) {
                println("Error: ${e.message}")
                e.printStackTrace()
                uiState = WeatherUIState.Error(e.message ?: "Network error")
            }
        }
    }






    fun resetToSearching() {
        uiState = WeatherUIState.Searching
    }
}
