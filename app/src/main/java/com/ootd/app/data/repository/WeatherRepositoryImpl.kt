package com.ootd.app.data.repository

import com.ootd.app.data.remote.WeatherApiService
import com.ootd.app.domain.model.WeatherInfo
import com.ootd.app.domain.repository.WeatherRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiService
) : WeatherRepository {

    private var cachedWeather: WeatherInfo? = null
    private val CACHE_EXPIRATION_MS = 30 * 60 * 1000 // 30 minutes

    override suspend fun getWeatherData(lat: Double, lon: Double): Result<WeatherInfo> {
        val currentTime = System.currentTimeMillis()
        
        cachedWeather?.let {
            if (currentTime - it.timestamp < CACHE_EXPIRATION_MS) {
                return Result.success(it)
            }
        }

        return try {
            val response = api.getCurrentWeather(
                lat = lat,
                lon = lon,
                apiKey = "YOUR_OPENWEATHERMAP_API_KEY" // Placeholder
            )
            val info = WeatherInfo(
                temperature = response.main.temp,
                description = response.weather.firstOrNull()?.description ?: "No description",
                icon = response.weather.firstOrNull()?.icon ?: "",
                timestamp = currentTime
            )
            cachedWeather = info
            Result.success(info)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
