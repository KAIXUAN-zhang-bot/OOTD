package com.ootd.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ootd.app.domain.model.Clothing
import com.ootd.app.domain.repository.ClothingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class WardrobeUiState(
    val clothings: List<Clothing> = emptyList(),
    val selectedCategory: String = "ALL",
    val loading: Boolean = false,
    val error: String? = null
)

class WardrobeViewModel(
    private val clothingRepository: ClothingRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(WardrobeUiState())
    val uiState: StateFlow<WardrobeUiState> = _uiState.asStateFlow()

    fun loadClothings(userId: String, category: String = "ALL") {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true, selectedCategory = category)
            try {
                clothingRepository.getClothingsByUser(userId).collect { clothings ->
                    val filtered = if (category == "ALL") {
                        clothings
                    } else {
                        clothings.filter { it.category.name == category }
                    }
                    _uiState.value = _uiState.value.copy(
                        clothings = filtered,
                        loading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    loading = false
                )
            }
        }
    }

    fun deleteClothing(clothingId: String) {
        viewModelScope.launch {
            try {
                clothingRepository.deleteClothing(clothingId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun toggleFavorite(clothingId: String, currentState: Boolean) {
        viewModelScope.launch {
            try {
                clothingRepository.updateFavorite(clothingId, !currentState)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
}
