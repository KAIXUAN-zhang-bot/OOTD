package com.ootd.app.domain.model

data class Outfit(
    val id: String,
    val userId: String,
    val name: String?,
    val topId: String,
    val bottomId: String?,
    val onePieceId: String?,
    val shoesId: String,
    val accessoryIds: List<String>,
    val occasion: Occasion,
    val season: Season,
    val weatherTags: List<WeatherCondition>,
    val description: String?,
    val isFavorite: Boolean = false,
    val isSystemGenerated: Boolean = true,
    val wearCount: Int = 0,
    val lastWornAt: Long?,
    val createdAt: Long,
    val updatedAt: Long
)

data class OutfitWithDetails(
    val outfit: Outfit,
    val top: Clothing,
    val bottom: Clothing?,
    val onePiece: Clothing?,
    val shoes: Clothing,
    val accessories: List<Clothing>
)
