package com.ootd.app.data.repository

import com.ootd.app.data.dao.WeatherCacheDao
import com.ootd.app.data.entity.WeatherCacheEntity
import com.ootd.app.data.network.api.WeatherApi
import com.ootd.app.domain.model.Weather
import com.ootd.app.domain.model.WeatherCondition
import com.ootd.app.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val weatherCacheDao: WeatherCacheDao
) : WeatherRepository {
    override suspend fun getWeather(location: String): Result<Weather> {
        return try {
            val response = weatherApi.getWeather(location, API_KEY)
            if (response.code == "200" && response.now != null) {
                val now = response.now
                val weather = Weather(
                    location = location,
                    temperature = now.temperature.toIntOrNull() ?: 0,
                    condition = parseWeatherCondition(now.text),
                    humidity = now.humidity.toIntOrNull() ?: 0,
                    windLevel = now.windLevel.toIntOrNull() ?: 0,
                    feelsLike = now.feelsLike.toIntOrNull()
                )
                Result.success(weather)
            } else {
                Result.failure(Exception("Weather API error: ${response.msg}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCachedWeather(userId: String, location: String): Flow<Weather?> {
        return weatherCacheDao.getWeatherByLocation(userId, location).map { entity ->
            entity?.let {
                Weather(
                    location = it.location,
                    temperature = it.temperature,
                    condition = WeatherCondition.valueOf(it.condition),
                    humidity = it.humidity,
                    windLevel = it.windLevel
                )
            }
        }
    }

    private fun parseWeatherCondition(text: String): WeatherCondition {
        return when {
            text.contains("晴") || text.contains("Sunny") -> WeatherCondition.SUNNY
            text.contains("多云") || text.contains("Partly") -> WeatherCondition.CLOUDY
            text.contains("阴") || text.contains("Overcast") -> WeatherCondition.OVERCAST
            text.contains("雨") || text.contains("Rain") -> WeatherCondition.RAINY
            text.contains("雪") || text.contains("Snow") -> WeatherCondition.SNOWY
            text.contains("雾") || text.contains("Fog") -> WeatherCondition.FOGGY
            text.contains("风") || text.contains("Wind") -> WeatherCondition.WINDY
            text.contains("雷") || text.contains("Thunder") -> WeatherCondition.THUNDERSTORM
            else -> WeatherCondition.CLOUDY
        }
    }

    companion object {
        private const val API_KEY = "YOUR_WEATHER_API_KEY"
    }
}
