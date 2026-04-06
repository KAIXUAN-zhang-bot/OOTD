package com.ootd.app.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ootd.app.data.remote.WeatherApiService
import com.ootd.app.data.repository.WeatherRepositoryImpl
import com.ootd.app.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideWeatherApiService(): WeatherApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(WeatherApiService.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApiService): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }
}
