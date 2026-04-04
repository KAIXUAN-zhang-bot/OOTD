package com.ootd.app.data.network.api

import com.ootd.app.data.network.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather/now")
    suspend fun getWeather(
        @Query("location") location: String,
        @Query("key") key: String
    ): WeatherResponse

    companion object {
        const val BASE_URL = "https://devapi.qweather.com/v7/"
    }
}
