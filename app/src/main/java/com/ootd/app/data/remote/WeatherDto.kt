package com.ootd.app.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    @SerialName("main") val main: Main,
    @SerialName("weather") val weather: List<Weather>
)

@Serializable
data class Main(
    @SerialName("temp") val temp: Double
)

@Serializable
data class Weather(
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String
)
