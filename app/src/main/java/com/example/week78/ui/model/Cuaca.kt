package com.example.week78.ui.model

data class Cuaca (
    val kota: String = "",
    val suhu: Int = 0,
    val kelembapan: Int = 0,
    val angin: Int = 0,
    val suhuPerasaan: Int = 0,
    val hujanTurun: Double = 0.0,
    val tekananUdara: Int = 0,
    val awan: Int = 0,
    val weatherDescription: String = "",
    val weatherIcon: String = "",
    val weatherIconUrl: String = "",
    val isError: Boolean = false,
    val errorMessage: String = ""
)

