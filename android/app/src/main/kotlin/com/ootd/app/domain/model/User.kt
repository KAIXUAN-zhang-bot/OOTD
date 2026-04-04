package com.ootd.app.domain.model

data class User(
    val id: String,
    val phone: String,
    val nickname: String,
    val avatar: String?,
    val gender: Gender,
    val height: Int?,
    val weight: Int?,
    val stylePreferences: List<Style>,
    val colorPreferences: List<Color>,
    val createdAt: Long,
    val updatedAt: Long
)
