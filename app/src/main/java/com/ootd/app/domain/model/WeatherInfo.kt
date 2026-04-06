package com.ootd.app.domain.model

data class WeatherInfo(
    val temperature: Double,
    val description: String,
    val icon: String,
    val timestamp: Long = System.currentTimeMillis()
)
