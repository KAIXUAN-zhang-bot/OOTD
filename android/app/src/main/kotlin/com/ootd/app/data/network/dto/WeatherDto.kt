package com.ootd.app.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val code: String,
    val msg: String,
    val now: WeatherNowDto?
)

@Serializable
data class WeatherNowDto(
    @SerialName("temp")
    val temperature: String,
    @SerialName("feelsLike")
    val feelsLike: String,
    val icon: String,
    val text: String,
    val humidity: String,
    @SerialName("windLevel")
    val windLevel: String,
    @SerialName("windDir")
    val windDirection: String
)
