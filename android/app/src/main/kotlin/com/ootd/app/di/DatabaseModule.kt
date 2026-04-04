package com.ootd.app.di

import android.content.Context
import androidx.room.Room
import com.ootd.app.data.database.OotdDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideOotdDatabase(
        @ApplicationContext context: Context
    ): OotdDatabase {
        return Room.databaseBuilder(
            context,
            OotdDatabase::class.java,
            "ootd_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: OotdDatabase) = database.userDao()

    @Singleton
    @Provides
    fun provideClothingDao(database: OotdDatabase) = database.clothingDao()

    @Singleton
    @Provides
    fun provideOutfitDao(database: OotdDatabase) = database.outfitDao()

    @Singleton
    @Provides
    fun provideWeatherCacheDao(database: OotdDatabase) = database.weatherCacheDao()
}
