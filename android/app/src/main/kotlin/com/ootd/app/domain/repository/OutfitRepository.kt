package com.ootd.app.domain.repository

import com.ootd.app.domain.model.Outfit
import com.ootd.app.domain.model.OutfitWithDetails
import kotlinx.coroutines.flow.Flow

interface OutfitRepository {
    suspend fun addOutfit(outfit: Outfit)
    suspend fun updateOutfit(outfit: Outfit)
    suspend fun deleteOutfit(outfitId: String)
    fun getOutfitById(outfitId: String): Flow<Outfit?>
    fun getOutfitsByUser(userId: String): Flow<List<Outfit>>
    fun getOutfitsByOccasion(userId: String, occasion: String): Flow<List<Outfit>>
    fun getFavoriteOutfits(userId: String): Flow<List<Outfit>>
    fun getRecentGeneratedOutfits(userId: String): Flow<List<Outfit>>
    suspend fun incrementWearCount(outfitId: String)
    suspend fun updateFavorite(outfitId: String, isFavorite: Boolean)
}
