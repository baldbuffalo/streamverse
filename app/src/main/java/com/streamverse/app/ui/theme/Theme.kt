package com.streamverse.app.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.darkColorScheme

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun StreamVerseTheme(content: @Composable () -> Unit) {
    val colorScheme = darkColorScheme(
        primary         = AccentRed,
        onPrimary       = TextPrimary,
        background      = BackgroundDark,
        onBackground    = TextPrimary,
        surface         = SurfaceDark,
        onSurface       = TextPrimary,
        surfaceVariant  = CardDark,
        onSurfaceVariant= TextSecondary,
        error           = AccentRed,
    )
    MaterialTheme(
        colorScheme = colorScheme,
        content     = content,
    )
}
