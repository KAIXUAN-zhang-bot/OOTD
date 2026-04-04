package com.ootd.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ootd.app.data.entity.ClothingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothingDao {
    @Insert
    suspend fun insert(clothing: ClothingEntity): Long

    @Update
    suspend fun update(clothing: ClothingEntity)

    @Delete
    suspend fun delete(clothing: ClothingEntity)

    @Query("SELECT * FROM clothings WHERE id = :clothingId LIMIT 1")
    fun getClothingById(clothingId: String): Flow<ClothingEntity?>

    @Query("SELECT * FROM clothings WHERE userId = :userId ORDER BY createdAt DESC")
    fun getClothingsByUser(userId: String): Flow<List<ClothingEntity>>

    @Query("SELECT * FROM clothings WHERE userId = :userId AND category = :category ORDER BY createdAt DESC")
    fun getClothingsByCategory(userId: String, category: String): Flow<List<ClothingEntity>>

    @Query("SELECT * FROM clothings WHERE userId = :userId AND isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavoriteClothings(userId: String): Flow<List<ClothingEntity>>

    @Query("SELECT * FROM clothings WHERE userId = :userId AND name LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchClothings(userId: String, query: String): Flow<List<ClothingEntity>>

    @Query("UPDATE clothings SET wearCount = wearCount + 1, lastWornAt = :timestamp WHERE id = :clothingId")
    suspend fun incrementWearCount(clothingId: String, timestamp: Long)

    @Query("UPDATE clothings SET isFavorite = :isFavorite WHERE id = :clothingId")
    suspend fun updateFavorite(clothingId: String, isFavorite: Boolean)

    @Query("DELETE FROM clothings WHERE userId = :userId")
    suspend fun deleteAllByUser(userId: String)
}
