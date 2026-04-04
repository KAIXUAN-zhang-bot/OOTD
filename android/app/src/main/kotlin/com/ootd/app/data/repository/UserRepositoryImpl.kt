package com.ootd.app.data.repository

import com.ootd.app.data.dao.UserDao
import com.ootd.app.data.entity.UserEntity
import com.ootd.app.domain.model.Color
import com.ootd.app.domain.model.Style
import com.ootd.app.domain.model.User
import com.ootd.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override suspend fun createUser(user: User) {
        userDao.insert(user.toEntity())
    }

    override suspend fun updateUser(user: User) {
        userDao.update(user.toEntity())
    }

    override fun getUserById(userId: String): Flow<User?> {
        return userDao.getUserById(userId).map { it?.toDomain() }
    }

    override fun getCurrentUser(): Flow<User?> {
        return userDao.getCurrentUser().map { it?.toDomain() }
    }

    override suspend fun deleteUser(userId: String) {
        userDao.deleteUser(userId)
    }

    private fun User.toEntity() = UserEntity(
        id = id,
        phone = phone,
        nickname = nickname,
        avatar = avatar,
        gender = gender.name,
        height = height,
        weight = weight,
        stylePreferences = stylePreferences.joinToString(",") { it.name },
        colorPreferences = colorPreferences.joinToString(",") { it.name },
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    private fun UserEntity.toDomain() = User(
        id = id,
        phone = phone,
        nickname = nickname,
        avatar = avatar,
        gender = com.ootd.app.domain.model.Gender.valueOf(gender),
        height = height,
        weight = weight,
        stylePreferences = stylePreferences.split(",").filter { it.isNotEmpty() }
            .map { Style.valueOf(it) },
        colorPreferences = colorPreferences.split(",").filter { it.isNotEmpty() }
            .map { Color.valueOf(it) },
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
