package com.ootd.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ootd.app.data.dao.ClothingDao
import com.ootd.app.data.dao.OutfitDao
import com.ootd.app.data.dao.UserDao
import com.ootd.app.data.dao.WeatherCacheDao
import com.ootd.app.data.entity.ClothingEntity
import com.ootd.app.data.entity.OutfitEntity
import com.ootd.app.data.entity.UserEntity
import com.ootd.app.data.entity.WeatherCacheEntity

@Database(
    entities = [
        UserEntity::class,
        ClothingEntity::class,
        OutfitEntity::class,
        WeatherCacheEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class OotdDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun clothingDao(): ClothingDao
    abstract fun outfitDao(): OutfitDao
    abstract fun weatherCacheDao(): WeatherCacheDao
}
