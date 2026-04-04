package com.ootd.app.domain.model

data class Clothing(
    val id: String,
    val userId: String,
    val name: String,
    val category: Category,
    val subCategory: SubCategory,
    val colors: List<Color>,
    val material: Material?,
    val seasons: List<Season>,
    val occasions: List<Occasion>,
    val styles: List<Style>,
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
