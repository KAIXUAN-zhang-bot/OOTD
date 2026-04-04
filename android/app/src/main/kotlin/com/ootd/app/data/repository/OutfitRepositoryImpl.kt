package com.ootd.app.data.repository

import com.ootd.app.data.dao.OutfitDao
import com.ootd.app.data.entity.OutfitEntity
import com.ootd.app.domain.model.Occasion
import com.ootd.app.domain.model.Outfit
import com.ootd.app.domain.model.Season
import com.ootd.app.domain.model.WeatherCondition
import com.ootd.app.domain.repository.OutfitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OutfitRepositoryImpl(private val outfitDao: OutfitDao) : OutfitRepository {
    override suspend fun addOutfit(outfit: Outfit) {
        outfitDao.insert(outfit.toEntity())
    }

    override suspend fun updateOutfit(outfit: Outfit) {
        outfitDao.update(outfit.toEntity())
    }

    override suspend fun deleteOutfit(outfitId: String) {
        // Implementation would fetch entity first, then delete
    }

    override fun getOutfitById(outfitId: String): Flow<Outfit?> {
        return outfitDao.getOutfitById(outfitId).map { it?.toDomain() }
    }

    override fun getOutfitsByUser(userId: String): Flow<List<Outfit>> {
        return outfitDao.getOutfitsByUser(userId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getOutfitsByOccasion(userId: String, occasion: String): Flow<List<Outfit>> {
        return outfitDao.getOutfitsByOccasion(userId, occasion).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getFavoriteOutfits(userId: String): Flow<List<Outfit>> {
        return outfitDao.getFavoriteOutfits(userId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getRecentGeneratedOutfits(userId: String): Flow<List<Outfit>> {
        return outfitDao.getRecentGeneratedOutfits(userId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun incrementWearCount(outfitId: String) {
        outfitDao.incrementWearCount(outfitId, System.currentTimeMillis())
    }

    override suspend fun updateFavorite(outfitId: String, isFavorite: Boolean) {
        outfitDao.updateFavorite(outfitId, isFavorite)
    }

    private fun Outfit.toEntity() = OutfitEntity(
        id = id,
        userId = userId,
        name = name,
        topId = topId,
        bottomId = bottomId,
        onePieceId = onePieceId,
        shoesId = shoesId,
        accessoryIds = accessoryIds.joinToString(","),
        occasion = occasion.name,
        season = season.name,
        weatherTags = weatherTags.joinToString(",") { it.name },
        description = description,
        isFavorite = isFavorite,
        isSystemGenerated = isSystemGenerated,
        wearCount = wearCount,
        lastWornAt = lastWornAt,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    private fun OutfitEntity.toDomain() = Outfit(
        id = id,
        userId = userId,
        name = name,
        topId = topId,
        bottomId = bottomId,
        onePieceId = onePieceId,
        shoesId = shoesId,
        accessoryIds = accessoryIds.split(",").filter { it.isNotEmpty() },
        occasion = Occasion.valueOf(occasion),
        season = Season.valueOf(season),
        weatherTags = weatherTags.split(",").filter { it.isNotEmpty() }
            .map { WeatherCondition.valueOf(it) },
        description = description,
        isFavorite = isFavorite,
        isSystemGenerated = isSystemGenerated,
        wearCount = wearCount,
        lastWornAt = lastWornAt,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
