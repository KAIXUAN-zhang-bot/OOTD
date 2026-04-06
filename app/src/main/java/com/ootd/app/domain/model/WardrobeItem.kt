package com.ootd.app.domain.model

import java.time.LocalDateTime

data class WardrobeItem(
    val id: String,
    val category: ClothingCategory,
    val subcategory: String,
    val seasons: List<Season>,
    val colors: List<String>,
    val fabric: Fabric,
    val formality: Int,
    val warmthIndex: Int,
    val styleTags: List<String>,
    val imageUri: String,
    val lastWorn: LocalDateTime?
)

enum class ClothingCategory {
    TOP, BOTTOM, DRESS, OUTERWEAR, SHOES, ACCESSORY
}

enum class Season {
    SPRING, SUMMER, AUTUMN, WINTER
}

enum class Fabric {
    COTTON, LINEN, SILK, WOOL, POLYESTER, LEATHER
}
