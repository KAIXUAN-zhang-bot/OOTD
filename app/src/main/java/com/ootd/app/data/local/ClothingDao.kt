package com.ootd.app.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothingDao {
    @Query("SELECT * FROM clothing_items")
    fun getAllItems(): Flow<List<ClothingEntity>>

    @Query("SELECT * FROM clothing_items WHERE id = :id")
    suspend fun getItemById(id: String): ClothingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ClothingEntity)

    @Update
    suspend fun updateItem(item: ClothingEntity)

    @Delete
    suspend fun deleteItem(item: ClothingEntity)
}
