package com.ootd.app.domain.model

data class UserPreference(
    val preferredStyles: List<String>,
    val dislikedColors: List<String>,
    val bodyType: BodyType,
    val colorPalette: ColorPalette
)

enum class BodyType {
    HOURGLASS, PEAR, RECTANGLE, INVERTED_TRIANGLE, ROUND
}

enum class ColorPalette {
    SPRING, SUMMER, AUTUMN, WINTER
}
