package com.ootd.app.domain.algorithm

import com.ootd.app.domain.model.Category
import com.ootd.app.domain.model.Clothing
import com.ootd.app.domain.model.Color
import com.ootd.app.domain.model.ColorHarmony
import com.ootd.app.domain.model.Occasion
import com.ootd.app.domain.model.Outfit
import com.ootd.app.domain.model.Season
import com.ootd.app.domain.model.Weather
import com.ootd.app.domain.model.WeatherCondition
import java.util.UUID

class RecommendationEngine {
    fun generateOutfits(
        clothings: List<Clothing>,
        occasion: Occasion,
        season: Season,
        weather: Weather?
    ): List<Outfit> {
        val tops = clothings.filter { it.category == Category.TOP }
        val bottoms = clothings.filter { it.category == Category.BOTTOM }
        val shoes = clothings.filter { it.category == Category.SHOES }
        val accessories = clothings.filter { it.category == Category.ACCESSORIES }

        val recommendations = mutableListOf<Outfit>()

        for (top in tops) {
            if (!matchesOccasion(top, occasion) || !matchesSeason(top, season)) continue

            for (shoe in shoes) {
                if (!matchesSeason(shoe, season)) continue

                val bottom = bottoms.filter { bottom ->
                    matchesOccasion(bottom, occasion) &&
                    matchesSeason(bottom, season) &&
                    colorHarmony(top.colors, bottom.colors) > 0.5f
                }.randomOrNull()

                if (bottom != null) {
                    val accessoryList = accessories.filter { acc ->
                        matchesSeason(acc, season) &&
                        colorHarmony(top.colors, acc.colors) > 0.5f
                    }.take(2)

                    val weatherTags = weather?.let { listOf(it.condition) } ?: emptyList()

                    val outfit = Outfit(
                        id = UUID.randomUUID().toString(),
                        userId = top.userId,
                        name = null,
                        topId = top.id,
                        bottomId = bottom.id,
                        onePieceId = null,
                        shoesId = shoe.id,
                        accessoryIds = accessoryList.map { it.id },
                        occasion = occasion,
                        season = season,
                        weatherTags = weatherTags,
                        description = null,
                        isFavorite = false,
                        isSystemGenerated = true,
                        wearCount = 0,
                        lastWornAt = null,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                    recommendations.add(outfit)
                    if (recommendations.size >= 5) break
                }
            }
            if (recommendations.size >= 5) break
        }

        return recommendations
    }

    private fun matchesOccasion(clothing: Clothing, occasion: Occasion): Boolean {
        return clothing.occasions.isEmpty() || clothing.occasions.contains(occasion)
    }

    private fun matchesSeason(clothing: Clothing, season: Season): Boolean {
        return clothing.seasons.isEmpty() || clothing.seasons.contains(season)
    }

    private fun colorHarmony(colors1: List<Color>, colors2: List<Color>): Float {
        if (colors1.isEmpty() || colors2.isEmpty()) return 0.5f

        val color1 = colors1.first()
        val color2 = colors2.first()

        return when {
            color1 == color2 -> ColorHarmony.MONOCHROMATIC.weight
            isComplementary(color1, color2) -> ColorHarmony.COMPLEMENTARY.weight
            isAnalogous(color1, color2) -> ColorHarmony.ANALOGOUS.weight
            isNeutral(color1) || isNeutral(color2) -> ColorHarmony.NEUTRAL_BASE.weight
            else -> 0.6f
        }
    }

    private fun isNeutral(color: Color): Boolean {
        return color in listOf(
            Color.WHITE, Color.BLACK, Color.GRAY,
            Color.BEIGE, Color.BROWN, Color.GOLD, Color.SILVER
        )
    }

    private fun isComplementary(color1: Color, color2: Color): Boolean {
        return (color1 == Color.RED && color2 == Color.GREEN) ||
               (color1 == Color.GREEN && color2 == Color.RED) ||
               (color1 == Color.BLUE && color2 == Color.ORANGE) ||
               (color1 == Color.ORANGE && color2 == Color.BLUE) ||
               (color1 == Color.PURPLE && color2 == Color.YELLOW) ||
               (color1 == Color.YELLOW && color2 == Color.PURPLE)
    }

    private fun isAnalogous(color1: Color, color2: Color): Boolean {
        val colorWheel = listOf(
            Color.RED, Color.ORANGE, Color.YELLOW,
            Color.GREEN, Color.BLUE, Color.PURPLE
        )
        val index1 = colorWheel.indexOf(color1)
        val index2 = colorWheel.indexOf(color2)

        if (index1 == -1 || index2 == -1) return false
        val distance = Math.abs(index1 - index2)
        return distance == 1 || distance == colorWheel.size - 1
    }
}
