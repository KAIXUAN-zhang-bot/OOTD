package com.ootd.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ootd.app.domain.model.Color
import com.ootd.app.domain.model.Gender
import com.ootd.app.domain.model.Style
import com.ootd.app.domain.model.User
import com.ootd.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

data class UserUiState(
    val user: User? = null,
    val loading: Boolean = false,
    val error: String? = null
)

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    fun loadCurrentUser() {
        viewModelScope.launch {
            userRepository.getCurrentUser().collect { user ->
                _uiState.value = _uiState.value.copy(user = user)
            }
        }
    }

    fun createUser(
        phone: String,
        nickname: String,
        gender: Gender
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            try {
                val user = User(
                    id = UUID.randomUUID().toString(),
                    phone = phone,
                    nickname = nickname,
                    avatar = null,
                    gender = gender,
                    height = null,
                    weight = null,
                    stylePreferences = emptyList(),
                    colorPreferences = emptyList(),
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                userRepository.createUser(user)
                _uiState.value = _uiState.value.copy(
                    user = user,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun updateUser(
        nickname: String? = null,
        height: Int? = null,
        weight: Int? = null,
        stylePreferences: List<Style>? = null,
        colorPreferences: List<Color>? = null
    ) {
        viewModelScope.launch {
            val currentUser = _uiState.value.user ?: return@launch
            _uiState.value = _uiState.value.copy(loading = true)
            try {
                val updated = currentUser.copy(
                    nickname = nickname ?: currentUser.nickname,
                    height = height ?: currentUser.height,
                    weight = weight ?: currentUser.weight,
                    stylePreferences = stylePreferences ?: currentUser.stylePreferences,
                    colorPreferences = colorPreferences ?: currentUser.colorPreferences,
                    updatedAt = System.currentTimeMillis()
                )
                userRepository.updateUser(updated)
                _uiState.value = _uiState.value.copy(
                    user = updated,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }
}
