package com.example.week78.data.repository

import com.example.week78.data.dto.ResponseWeather
import com.example.week78.data.service.WeatherApiService
import retrofit2.Response

class WeatherRepository(private val apiService: WeatherApiService) {
    suspend fun getCurrentWeather(city: String, apiKey: String): Response<ResponseWeather> {
        return apiService.getWeather(city, apiKey)
    }
}
