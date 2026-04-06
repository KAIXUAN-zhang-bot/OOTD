package com.ootd.app.domain.repository

import com.ootd.app.domain.model.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, lon: Double): Result<WeatherInfo>
}
