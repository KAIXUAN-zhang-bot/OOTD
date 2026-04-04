package com.ootd.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PrimaryColor = Color(0xFFE91E63)
private val SecondaryColor = Color(0xFF03DAC6)
private val TertiaryColor = Color(0xFF018786)
private val BackgroundColor = Color(0xFFFAFAFA)
private val SurfaceColor = Color(0xFFFFFFFF)
private val ErrorColor = Color(0xFFB00020)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    tertiary = TertiaryColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    error = ErrorColor
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    tertiary = TertiaryColor,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    error = ErrorColor
)

@Composable
fun OOTDTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
