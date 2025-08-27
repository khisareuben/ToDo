package com.example.todo.presentation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomTheme(
    val containerColor: Color,
    val buttonColor: Color,
    val background: Color,
    val cardBackground: Color,
    val titleColor: Color,
    val bodyColor: Color,
    val logoColor: Color
)


val darkThemeColors = CustomTheme(
    containerColor = Color(0xFF0A0A0A),
    buttonColor = Color(0xFFFFAB00),
    background = Color(0xFF0A0A0A),
    cardBackground = Color.DarkGray,
    titleColor = Color(0xFFFFFFFF),
    bodyColor = Color.LightGray,
    logoColor = Color(0xFFFFAB00)
)

val lightThemeColor = CustomTheme(
    containerColor = Color(0xFFF1F1F1),
    buttonColor = Color(0xFFFFAB00),
    background = Color.White,
    cardBackground = Color(0xFFFFFFFF),
    titleColor = Color(0xFF0A0A0A),
    bodyColor = Color.Gray,
    logoColor = Color(0xFFFFAB00)
)

val LocalTheme = staticCompositionLocalOf<CustomTheme> {
    error("No theme provided")
}