package com.ootd.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ootd.app.data.entity.WeatherCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherCacheDao {
    @Insert
    suspend fun insert(weather: WeatherCacheEntity)

    @Update
    suspend fun update(weather: WeatherCacheEntity)

    @Query("SELECT * FROM weather_cache WHERE userId = :userId AND location = :location ORDER BY cachedAt DESC LIMIT 1")
    fun getWeatherByLocation(userId: String, location: String): Flow<WeatherCacheEntity?>

    @Query("DELETE FROM weather_cache WHERE expiresAt < :currentTime")
    suspend fun deleteExpired(currentTime: Long)

    @Query("DELETE FROM weather_cache WHERE userId = :userId")
    suspend fun deleteAllByUser(userId: String)
}
