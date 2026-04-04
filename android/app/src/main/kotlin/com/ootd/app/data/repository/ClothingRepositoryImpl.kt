package com.ootd.app.data.repository

import com.ootd.app.data.dao.ClothingDao
import com.ootd.app.data.entity.ClothingEntity
import com.ootd.app.domain.model.Clothing
import com.ootd.app.domain.model.Category
import com.ootd.app.domain.model.Color
import com.ootd.app.domain.model.Material
import com.ootd.app.domain.model.Occasion
import com.ootd.app.domain.model.Season
import com.ootd.app.domain.model.Style
import com.ootd.app.domain.model.SubCategory
import com.ootd.app.domain.repository.ClothingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ClothingRepositoryImpl(private val clothingDao: ClothingDao) : ClothingRepository {
    override suspend fun addClothing(clothing: Clothing) {
        clothingDao.insert(clothing.toEntity())
    }

    override suspend fun updateClothing(clothing: Clothing) {
        clothingDao.update(clothing.toEntity())
    }

    override suspend fun deleteClothing(clothingId: String) {
        val clothing = clothingDao.getClothingById(clothingId)
    }

    override fun getClothingById(clothingId: String): Flow<Clothing?> {
        return clothingDao.getClothingById(clothingId).map { it?.toDomain() }
    }

    override fun getClothingsByUser(userId: String): Flow<List<Clothing>> {
        return clothingDao.getClothingsByUser(userId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getClothingsByCategory(userId: String, category: String): Flow<List<Clothing>> {
        return clothingDao.getClothingsByCategory(userId, category).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getFavoriteClothings(userId: String): Flow<List<Clothing>> {
        return clothingDao.getFavoriteClothings(userId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun searchClothings(userId: String, query: String): Flow<List<Clothing>> {
        return clothingDao.searchClothings(userId, query).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun incrementWearCount(clothingId: String) {
        clothingDao.incrementWearCount(clothingId, System.currentTimeMillis())
    }

    override suspend fun updateFavorite(clothingId: String, isFavorite: Boolean) {
        clothingDao.updateFavorite(clothingId, isFavorite)
    }

    private fun Clothing.toEntity() = ClothingEntity(
        id = id,
        userId = userId,
        name = name,
        category = category.name,
        subCategory = subCategory.name,
        colors = colors.joinToString(",") { it.name },
        material = material?.name,
        seasons = seasons.joinToString(",") { it.name },
        occasions = occasions.joinToString(",") { it.name },
        styles = styles.joinToString(",") { it.name },
        brand = brand,
        price = price,
        purchaseDate = purchaseDate,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        notes = notes,
        wearCount = wearCount,
        lastWornAt = lastWornAt,
        isFavorite = isFavorite,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    private fun ClothingEntity.toDomain() = Clothing(
        id = id,
        userId = userId,
        name = name,
        category = Category.valueOf(category),
        subCategory = SubCategory.valueOf(subCategory),
        colors = colors.split(",").filter { it.isNotEmpty() }
            .map { Color.valueOf(it) },
        material = material?.let { Material.valueOf(it) },
        seasons = seasons.split(",").filter { it.isNotEmpty() }
            .map { Season.valueOf(it) },
        occasions = occasions.split(",").filter { it.isNotEmpty() }
            .map { Occasion.valueOf(it) },
        styles = styles.split(",").filter { it.isNotEmpty() }
            .map { Style.valueOf(it) },
        brand = brand,
        price = price,
        purchaseDate = purchaseDate,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        notes = notes,
        wearCount = wearCount,
        lastWornAt = lastWornAt,
        isFavorite = isFavorite,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
