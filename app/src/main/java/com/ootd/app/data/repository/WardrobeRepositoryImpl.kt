package com.ootd.app.data.repository

import com.ootd.app.data.local.ClothingDao
import com.ootd.app.data.mapper.toClothingEntity
import com.ootd.app.data.mapper.toWardrobeItem
import com.ootd.app.domain.model.WardrobeItem
import com.ootd.app.domain.repository.WardrobeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WardrobeRepositoryImpl @Inject constructor(
    private val dao: ClothingDao
) : WardrobeRepository {

    override fun getAllItems(): Flow<List<WardrobeItem>> {
        return dao.getAllItems().map { entities ->
            entities.map { it.toWardrobeItem() }
        }
    }

    override suspend fun getItemById(id: String): WardrobeItem? {
        return dao.getItemById(id)?.toWardrobeItem()
    }

    override suspend fun insertItem(item: WardrobeItem) {
        dao.insertItem(item.toClothingEntity())
    }

    override suspend fun updateItem(item: WardrobeItem) {
        dao.updateItem(item.toClothingEntity())
    }

    override suspend fun deleteItem(item: WardrobeItem) {
        dao.deleteItem(item.toClothingEntity())
    }
}
