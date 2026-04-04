package com.ootd.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ootd.app.domain.model.Occasion
import com.ootd.app.domain.model.Outfit
import com.ootd.app.domain.model.Weather
import com.ootd.app.domain.repository.OutfitRepository
import com.ootd.app.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val selectedOccasion: Occasion = Occasion.DAILY,
    val weather: Weather? = null,
    val weatherLoading: Boolean = false,
    val recommendedOutfits: List<Outfit> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)

class HomeViewModel(
    private val weatherRepository: WeatherRepository,
    private val outfitRepository: OutfitRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun selectOccasion(occasion: Occasion) {
        _uiState.value = _uiState.value.copy(selectedOccasion = occasion)
        generateRecommendations()
    }

    fun refreshWeather(location: String = "beijing") {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(weatherLoading = true)
            val result = weatherRepository.getWeather(location)
            result.onSuccess { weather ->
                _uiState.value = _uiState.value.copy(
                    weather = weather,
                    weatherLoading = false
                )
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    error = error.message,
                    weatherLoading = false
                )
            }
        }
    }

    private fun generateRecommendations() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            try {
                val occasion = _uiState.value.selectedOccasion
                _uiState.value = _uiState.value.copy(
                    recommendedOutfits = emptyList(),
                    loading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    loading = false
                )
            }
        }
    }
}
