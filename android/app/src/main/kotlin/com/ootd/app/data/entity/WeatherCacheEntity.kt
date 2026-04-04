package com.ootd.app.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "weather_cache",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class WeatherCacheEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val location: String,
    val temperature: Int,
    val condition: String,
    val humidity: Int,
    val windLevel: Int,
    val cachedAt: Long,
    val expiresAt: Long
)
