package com.ootd.app.domain.usecase

import com.ootd.app.domain.model.*
import com.ootd.app.domain.repository.WardrobeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class GetRecommendationUseCase(
    private val wardrobeRepository: WardrobeRepository
) {
    operator fun invoke(
        weather: WeatherInfo,
        occasion: Occasion,
        mood: Mood
    ): Flow<List<RecommendedItem>> {
        return wardrobeRepository.getAllItems().map { items ->
            items.map { item ->
                val sWeather = calculateWeatherScore(item, weather)
                val sOccasion = calculateOccasionScore(item, occasion)
                val sMood = calculateMoodScore(item, mood)

                val totalScore = (sWeather * 0.5) + (sOccasion * 0.3) + (sMood * 0.2)
                RecommendedItem(item, totalScore)
            }
            .filter { it.matchScore > 0.3 } // Minimum threshold
            .sortedByDescending { it.matchScore }
        }
    }

    private fun calculateWeatherScore(item: WardrobeItem, weather: WeatherInfo): Double {
        val temp = weather.temperature
        val targetWarmth = when {
            temp < 0 -> 10
            temp < 10 -> 8
            temp < 20 -> 5
            temp < 30 -> 2
            else -> 1
        }

        // Base score based on warmth index proximity
        var score = (1.0 - (Math.abs(item.warmthIndex - targetWarmth) / 10.0)).coerceAtLeast(0.0)

        // Rainy/Snowy penalty
        val desc = weather.description.lowercase(Locale.ROOT)
        if (desc.contains("rain") || desc.contains("snow")) {
            if (item.fabric == Fabric.SILK || item.fabric == Fabric.LEATHER) {
                score *= 0.5
            }
        }

        return score
    }

    private fun calculateOccasionScore(item: WardrobeItem, occasion: Occasion): Double {
        val targetFormalityRange = when (occasion) {
            Occasion.CASUAL -> 1..4
            Occasion.WORK -> 5..8
            Occasion.DATE -> 4..7
            Occasion.PARTY -> 3..7
            Occasion.GYM -> 1..2
        }

        var score = if (item.formality in targetFormalityRange) 1.0 else 0.5

        // Check style tags for specific matches
        val occasionTag = occasion.name.lowercase(Locale.ROOT)
        if (item.styleTags.any { it.lowercase(Locale.ROOT) == occasionTag }) {
            score = (score + 0.2).coerceAtMost(1.0)
        }

        return score
    }

    private fun calculateMoodScore(item: WardrobeItem, mood: Mood): Double {
        val brightColors = listOf("yellow", "pink", "red", "orange", "cyan", "magenta", "purple")
        val neutralColors = listOf("gray", "beige", "black", "white", "navy", "brown")

        val itemColors = item.colors.map { it.lowercase(Locale.ROOT) }
        
        return when (mood) {
            Mood.ENERGETIC, Mood.VIBRANT -> {
                if (itemColors.any { it in brightColors }) 1.0 else 0.5
            }
            Mood.LOW_KEY -> {
                if (itemColors.any { it in neutralColors }) 1.0 else 0.5
            }
        }
    }
}
