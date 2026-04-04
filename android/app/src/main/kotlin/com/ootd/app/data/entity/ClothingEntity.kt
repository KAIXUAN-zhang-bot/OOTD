package com.ootd.app.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "clothings",
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
data class ClothingEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val name: String,
    val category: String,
    val subCategory: String,
    val colors: String,
    val material: String?,
    val seasons: String,
    val occasions: String,
    val styles: String,
    val brand: String?,
    val price: Float?,
    val purchaseDate: Long?,
    val imageUrl: String,
    val thumbnailUrl: String,
    val notes: String?,
    val wearCount: Int = 0,
    val lastWornAt: Long?,
    val isFavorite: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
)
