package com.ootd.app.util

import androidx.compose.ui.graphics.Color
import com.ootd.app.domain.model.Color as DomainColor

object ColorUtils {
    fun domainColorToCompose(color: DomainColor): Color {
        return Color(android.graphics.Color.parseColor(color.hexCode))
    }

    fun getComplementaryColor(color: DomainColor): DomainColor? {
        return when (color) {
            DomainColor.RED -> DomainColor.GREEN
            DomainColor.GREEN -> DomainColor.RED
            DomainColor.BLUE -> DomainColor.ORANGE
            DomainColor.ORANGE -> DomainColor.BLUE
            DomainColor.PURPLE -> DomainColor.YELLOW
            DomainColor.YELLOW -> DomainColor.PURPLE
            else -> null
        }
    }

    fun getAnalogousColors(color: DomainColor): List<DomainColor> {
        val colorWheel = listOf(
            DomainColor.RED,
            DomainColor.ORANGE,
            DomainColor.YELLOW,
            DomainColor.GREEN,
            DomainColor.BLUE,
            DomainColor.PURPLE
        )

        val index = colorWheel.indexOf(color)
        if (index == -1) return emptyList()

        val analogous = mutableListOf<DomainColor>()
        if (index > 0) analogous.add(colorWheel[index - 1])
        if (index < colorWheel.size - 1) analogous.add(colorWheel[index + 1])

        return analogous
    }

    fun isNeutralColor(color: DomainColor): Boolean {
        return color in listOf(
            DomainColor.WHITE,
            DomainColor.BLACK,
            DomainColor.GRAY,
            DomainColor.BEIGE,
            DomainColor.BROWN,
            DomainColor.GOLD,
            DomainColor.SILVER
        )
    }
}
