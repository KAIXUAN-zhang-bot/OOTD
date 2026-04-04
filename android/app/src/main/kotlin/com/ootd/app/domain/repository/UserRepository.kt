package com.ootd.app.domain.repository

import com.ootd.app.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: User)
    suspend fun updateUser(user: User)
    fun getUserById(userId: String): Flow<User?>
    fun getCurrentUser(): Flow<User?>
    suspend fun deleteUser(userId: String)
}
