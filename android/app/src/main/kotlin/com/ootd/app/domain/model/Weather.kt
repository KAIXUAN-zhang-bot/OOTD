package com.ootd.app.domain.model

data class Weather(
    val location: String,
    val temperature: Int,
    val condition: WeatherCondition,
    val humidity: Int,
    val windLevel: Int,
    val feelsLike: Int? = null,
    val uvIndex: Int? = null
)
