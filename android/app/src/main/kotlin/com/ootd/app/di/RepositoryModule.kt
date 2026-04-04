package com.ootd.app.di

import com.ootd.app.data.dao.ClothingDao
import com.ootd.app.data.dao.OutfitDao
import com.ootd.app.data.dao.UserDao
import com.ootd.app.data.dao.WeatherCacheDao
import com.ootd.app.data.network.api.WeatherApi
import com.ootd.app.data.repository.ClothingRepositoryImpl
import com.ootd.app.data.repository.OutfitRepositoryImpl
import com.ootd.app.data.repository.UserRepositoryImpl
import com.ootd.app.data.repository.WeatherRepositoryImpl
import com.ootd.app.domain.repository.ClothingRepository
import com.ootd.app.domain.repository.OutfitRepository
import com.ootd.app.domain.repository.UserRepository
import com.ootd.app.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Singleton
    @Provides
    fun provideClothingRepository(clothingDao: ClothingDao): ClothingRepository {
        return ClothingRepositoryImpl(clothingDao)
    }

    @Singleton
    @Provides
    fun provideOutfitRepository(outfitDao: OutfitDao): OutfitRepository {
        return OutfitRepositoryImpl(outfitDao)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(
        weatherApi: WeatherApi,
        weatherCacheDao: WeatherCacheDao
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi, weatherCacheDao)
    }
}
