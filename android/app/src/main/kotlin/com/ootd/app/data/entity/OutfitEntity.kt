package com.ootd.app.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "outfits",
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
data class OutfitEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val name: String?,
    val topId: String,
    val bottomId: String?,
    val onePieceId: String?,
    val shoesId: String,
    val accessoryIds: String,
    val occasion: String,
    val season: String,
    val weatherTags: String,
    val description: String?,
    val isFavorite: Boolean = false,
    val isSystemGenerated: Boolean = true,
    val wearCount: Int = 0,
    val lastWornAt: Long?,
    val createdAt: Long,
    val updatedAt: Long
)
