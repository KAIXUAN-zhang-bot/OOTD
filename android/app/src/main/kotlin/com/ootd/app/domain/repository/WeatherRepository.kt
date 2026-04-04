package com.ootd.app.domain.repository

import com.ootd.app.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(location: String): Result<Weather>
    fun getCachedWeather(userId: String, location: String): Flow<Weather?>
}
