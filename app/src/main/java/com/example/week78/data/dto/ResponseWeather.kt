package com.example.week78.data.dto

data class ResponseWeather(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val rain: Rain?,  // Make this nullable
    val sys: Sys,
    val timezone: Int,
    val weather: List<Weather>,
    val wind: Wind
)
