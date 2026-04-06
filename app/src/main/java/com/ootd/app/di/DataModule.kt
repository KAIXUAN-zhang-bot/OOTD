package com.ootd.app.di

import android.content.Context
import androidx.room.Room
import com.ootd.app.data.local.AppDatabase
import com.ootd.app.data.local.ClothingDao
import com.ootd.app.data.repository.WardrobeRepositoryImpl
import com.ootd.app.domain.repository.WardrobeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "ootd_database"
        ).build()
    }

    @Provides
    fun provideClothingDao(database: AppDatabase): ClothingDao {
        return database.clothingDao()
    }

    @Provides
    @Singleton
    fun provideWardrobeRepository(dao: ClothingDao): WardrobeRepository {
        return WardrobeRepositoryImpl(dao)
    }
}
