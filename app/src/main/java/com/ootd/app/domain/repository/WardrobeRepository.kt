package com.ootd.app.domain.repository

import com.ootd.app.domain.model.WardrobeItem
import kotlinx.coroutines.flow.Flow

interface WardrobeRepository {
    fun getAllItems(): Flow<List<WardrobeItem>>
    suspend fun getItemById(id: String): WardrobeItem?
    suspend fun insertItem(item: WardrobeItem)
    suspend fun updateItem(item: WardrobeItem)
    suspend fun deleteItem(item: WardrobeItem)
}
