package com.ootd.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ootd.app.domain.model.Clothing
import com.ootd.app.domain.model.Color
import com.ootd.app.domain.model.Material
import com.ootd.app.domain.model.Occasion
import com.ootd.app.domain.model.Season
import com.ootd.app.domain.model.Style
import com.ootd.app.domain.model.SubCategory
import com.ootd.app.domain.repository.ClothingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

data class AddClothingUiState(
    val name: String = "",
    val subCategory: SubCategory? = null,
    val selectedColors: List<Color> = emptyList(),
    val material: Material? = null,
    val selectedSeasons: List<Season> = emptyList(),
    val selectedOccasions: List<Occasion> = emptyList(),
    val selectedStyles: List<Style> = emptyList(),
    val brand: String = "",
    val price: String = "",
    val imageUrl: String = "",
    val notes: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val savedSuccess: Boolean = false
)

class AddClothingViewModel(
    private val clothingRepository: ClothingRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddClothingUiState())
    val uiState: StateFlow<AddClothingUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updateSubCategory(subCategory: SubCategory) {
        _uiState.value = _uiState.value.copy(subCategory = subCategory)
    }

    fun toggleColor(color: Color) {
        val current = _uiState.value.selectedColors.toMutableList()
        if (current.contains(color)) {
            current.remove(color)
        } else {
            current.add(color)
        }
        _uiState.value = _uiState.value.copy(selectedColors = current)
    }

    fun updateMaterial(material: Material) {
        _uiState.value = _uiState.value.copy(material = material)
    }

    fun toggleSeason(season: Season) {
        val current = _uiState.value.selectedSeasons.toMutableList()
        if (current.contains(season)) {
            current.remove(season)
        } else {
            current.add(season)
        }
        _uiState.value = _uiState.value.copy(selectedSeasons = current)
    }

    fun toggleOccasion(occasion: Occasion) {
        val current = _uiState.value.selectedOccasions.toMutableList()
        if (current.contains(occasion)) {
            current.remove(occasion)
        } else {
            current.add(occasion)
        }
        _uiState.value = _uiState.value.copy(selectedOccasions = current)
    }

    fun toggleStyle(style: Style) {
        val current = _uiState.value.selectedStyles.toMutableList()
        if (current.contains(style)) {
            current.remove(style)
        } else {
            current.add(style)
        }
        _uiState.value = _uiState.value.copy(selectedStyles = current)
    }

    fun updateBrand(brand: String) {
        _uiState.value = _uiState.value.copy(brand = brand)
    }

    fun updatePrice(price: String) {
        _uiState.value = _uiState.value.copy(price = price)
    }

    fun updateImageUrl(imageUrl: String) {
        _uiState.value = _uiState.value.copy(imageUrl = imageUrl)
    }

    fun updateNotes(notes: String) {
        _uiState.value = _uiState.value.copy(notes = notes)
    }

    fun saveClothing(userId: String) {
        viewModelScope.launch {
            val state = _uiState.value
            if (state.name.isBlank() || state.subCategory == null || state.selectedColors.isEmpty()) {
                _uiState.value = _uiState.value.copy(error = "Please fill required fields")
                return@launch
            }

            _uiState.value = _uiState.value.copy(loading = true)
            try {
                val clothing = Clothing(
                    id = UUID.randomUUID().toString(),
                    userId = userId,
                    name = state.name,
                    category = when (state.subCategory) {
                        SubCategory.T_SHIRT, SubCategory.SHIRT, SubCategory.SWEATER, SubCategory.JACKET ->
                            com.ootd.app.domain.model.Category.TOP
                        SubCategory.LONG_PANTS, SubCategory.SHORT_PANTS, SubCategory.SKIRT ->
                            com.ootd.app.domain.model.Category.BOTTOM
                        SubCategory.DRESS, SubCategory.JUMPSUIT ->
                            com.ootd.app.domain.model.Category.ONE_PIECE
                        SubCategory.SNEAKERS, SubCategory.CASUAL_SHOES, SubCategory.FORMAL_SHOES, SubCategory.BOOTS ->
                            com.ootd.app.domain.model.Category.SHOES
                        else ->
                            com.ootd.app.domain.model.Category.ACCESSORIES
                    },
                    subCategory = state.subCategory!!,
                    colors = state.selectedColors,
                    material = state.material,
                    seasons = state.selectedSeasons,
                    occasions = state.selectedOccasions,
                    styles = state.selectedStyles,
                    brand = state.brand.takeIf { it.isNotBlank() },
                    price = state.price.toFloatOrNull(),
                    purchaseDate = null,
                    imageUrl = state.imageUrl,
                    thumbnailUrl = state.imageUrl,
                    notes = state.notes.takeIf { it.isNotBlank() },
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                clothingRepository.addClothing(clothing)
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    savedSuccess = true,
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
