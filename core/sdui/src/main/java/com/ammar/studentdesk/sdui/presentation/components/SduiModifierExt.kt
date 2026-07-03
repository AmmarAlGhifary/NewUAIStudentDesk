package com.ammar.studentdesk.sdui.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.ammar.studentdesk.sdui.domain.model.SduiModifier

@SuppressLint("ModifierFactoryExtensionFunction")
fun SduiModifier.toComposeModifier(baseModifier: Modifier = Modifier): Modifier {
    var mod = baseModifier

    // 1. MARGIN (Outer Spacing)
    margin?.let {
        mod = mod.padding(
            start = (it.start ?: it.horizontal ?: it.all ?: 0).dp,
            top = (it.top ?: it.vertical ?: it.all ?: 0).dp,
            end = (it.end ?: it.horizontal ?: it.all ?: 0).dp,
            bottom = (it.bottom ?: it.vertical ?: it.all ?: 0).dp
        )
    }

    // 2. SIZING
    width?.let {
        mod = when (it.type) {
            "fill" -> mod.fillMaxWidth()
            "wrap" -> mod.wrapContentWidth()
            "exact" -> it.value?.let { v -> mod.width(v.dp) } ?: mod
            else -> mod
        }
    }

    height?.let {
        mod = when (it.type) {
            "fill" -> mod.fillMaxHeight()
            "wrap" -> mod.wrapContentHeight()
            "exact" -> it.value?.let { v -> mod.height(v.dp) } ?: mod
            else -> mod
        }
    }

    // 3. CLIP (For making circles/rounded corners physically cut the image)
    cornerRadius?.let {
        mod = mod.clip(RoundedCornerShape(it.dp))
    }

    // 4. BORDER
    borderWidth?.let { bw ->
        borderColor?.let { hex ->
            try {
                val color = Color(hex.toColorInt())
                val shape = RoundedCornerShape((cornerRadius ?: 0).dp)
                mod = mod.border(bw.dp, color, shape)
            } catch (_: Exception) {/**Not used**/}
        }
    }

    // 5. ELEVATION (Shadows)
    elevation?.let { ev ->
        mod = mod.shadow(
            elevation = ev.dp,
            shape = RoundedCornerShape((cornerRadius ?: 0).dp)
        )
    }

    // 6. BACKGROUND COLOR
    backgroundColor?.let { hex ->
        try {
            val color = Color(hex.toColorInt())
            val shape = RoundedCornerShape((cornerRadius ?: 0).dp)
            mod = mod.background(color = color, shape = shape)
        } catch (_: Exception) { /**not used**/}
    }

    // 7. PADDING (Inner Spacing)
    padding?.let {
        mod = mod.padding(
            start = (it.start ?: it.horizontal ?: it.all ?: 0).dp,
            top = (it.top ?: it.vertical ?: it.all ?: 0).dp,
            end = (it.end ?: it.horizontal ?: it.all ?: 0).dp,
            bottom = (it.bottom ?: it.vertical ?: it.all ?: 0).dp
        )
    }

    return mod
}