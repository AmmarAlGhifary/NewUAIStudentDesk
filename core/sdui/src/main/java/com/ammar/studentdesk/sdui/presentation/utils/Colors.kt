package com.ammar.studentdesk.sdui.presentation.utils

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

fun String?.toColor(defaultColor: Color) : Color {
    if (this == null) return defaultColor
    return try {
        Color(this.toColorInt())
    } catch (e: Exception) {
        defaultColor
    }
 }