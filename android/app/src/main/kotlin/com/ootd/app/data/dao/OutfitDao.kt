package com.ootd.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ootd.app.data.entity.OutfitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OutfitDao {
    @Insert
    suspend fun insert(outfit: OutfitEntity): Long

    @Update
    suspend fun update(outfit: OutfitEntity)

    @Delete
    suspend fun delete(outfit: OutfitEntity)

    @Query("SELECT * FROM outfits WHERE id = :outfitId LIMIT 1")
    fun getOutfitById(outfitId: String): Flow<OutfitEntity?>

    @Query("SELECT * FROM outfits WHERE userId = :userId ORDER BY createdAt DESC")
    fun getOutfitsByUser(userId: String): Flow<List<OutfitEntity>>

    @Query("SELECT * FROM outfits WHERE userId = :userId AND occasion = :occasion ORDER BY createdAt DESC")
    fun getOutfitsByOccasion(userId: String, occasion: String): Flow<List<OutfitEntity>>

    @Query("SELECT * FROM outfits WHERE userId = :userId AND isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavoriteOutfits(userId: String): Flow<List<OutfitEntity>>

    @Query("SELECT * FROM outfits WHERE userId = :userId AND season = :season ORDER BY createdAt DESC")
    fun getOutfitsBySeason(userId: String, season: String): Flow<List<OutfitEntity>>

    @Query("SELECT * FROM outfits WHERE userId = :userId AND isSystemGenerated = 1 ORDER BY createdAt DESC LIMIT :limit")
    fun getRecentGeneratedOutfits(userId: String, limit: Int = 10): Flow<List<OutfitEntity>>

    @Query("UPDATE outfits SET wearCount = wearCount + 1, lastWornAt = :timestamp WHERE id = :outfitId")
    suspend fun incrementWearCount(outfitId: String, timestamp: Long)

    @Query("UPDATE outfits SET isFavorite = :isFavorite WHERE id = :outfitId")
    suspend fun updateFavorite(outfitId: String, isFavorite: Boolean)

    @Query("DELETE FROM outfits WHERE userId = :userId")
    suspend fun deleteAllByUser(userId: String)
}
