package com.ootd.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ootd.app.domain.model.ClothingCategory
import com.ootd.app.domain.model.Fabric
import com.ootd.app.domain.model.Season
import java.time.LocalDateTime

@Entity(tableName = "clothing_items")
data class ClothingEntity(
    @PrimaryKey val id: String,
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
