package com.ootd.app.domain.repository

import com.ootd.app.domain.model.Clothing
import com.ootd.app.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface ClothingRepository {
    suspend fun addClothing(clothing: Clothing)
    suspend fun updateClothing(clothing: Clothing)
    suspend fun deleteClothing(clothingId: String)
    fun getClothingById(clothingId: String): Flow<Clothing?>
    fun getClothingsByUser(userId: String): Flow<List<Clothing>>
    fun getClothingsByCategory(userId: String, category: String): Flow<List<Clothing>>
    fun getFavoriteClothings(userId: String): Flow<List<Clothing>>
    fun searchClothings(userId: String, query: String): Flow<List<Clothing>>
    suspend fun incrementWearCount(clothingId: String)
    suspend fun updateFavorite(clothingId: String, isFavorite: Boolean)
}
