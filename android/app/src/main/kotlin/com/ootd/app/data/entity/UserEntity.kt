package com.ootd.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val phone: String,
    val nickname: String,
    val avatar: String?,
    val gender: String,
    val height: Int?,
    val weight: Int?,
    val stylePreferences: String,
    val colorPreferences: String,
    val createdAt: Long,
    val updatedAt: Long
)
