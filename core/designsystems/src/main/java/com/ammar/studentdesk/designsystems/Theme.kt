package com.ammar.studentdesk.designsystems

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = darkColorScheme(
    primary = StudentDeskPrimaryColor,
    background = StudentDeskDarkBackground,
    surface = StudentDeskSurface,
    onBackground = StudentDeskTextWhite,
    onSurface = StudentDeskTextWhite,
    secondary = StudentDeskextSecondary
)

@Composable
fun StudentDeskTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        content = content
    )
}