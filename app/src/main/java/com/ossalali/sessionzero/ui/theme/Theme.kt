package com.ossalali.sessionzero.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext

private val DuskDarkScheme = darkColorScheme(
    primary = DuskColors.AccentDark,
    onPrimary = DuskColors.AccentInkDark,
    primaryContainer = DuskColors.AccentSoftDark,
    onPrimaryContainer = DuskColors.AccentDark,
    secondary = DuskColors.GoldDark,
    onSecondary = DuskColors.AccentInkDark,
    secondaryContainer = DuskColors.Surface2Dark,
    onSecondaryContainer = DuskColors.GoldDark,
    tertiary = DuskColors.GoldDark,
    onTertiary = DuskColors.AccentInkDark,
    tertiaryContainer = DuskColors.Surface2Dark,
    onTertiaryContainer = DuskColors.GoldDark,
    background = DuskColors.BgDark,
    onBackground = DuskColors.TextDark,
    surface = DuskColors.SurfaceDark,
    onSurface = DuskColors.TextDark,
    surfaceVariant = DuskColors.Surface2Dark,
    onSurfaceVariant = DuskColors.TextDimDark,
    surfaceContainerLowest = DuskColors.BgDark,
    surfaceContainerLow = DuskColors.BgAltDark,
    surfaceContainer = DuskColors.SurfaceDark,
    surfaceContainerHigh = DuskColors.Surface2Dark,
    surfaceContainerHighest = DuskColors.SurfaceHiDark,
    outline = DuskColors.LineDark,
    outlineVariant = DuskColors.LineHiDark,
    error = DuskColors.DangerDark,
    onError = DuskColors.AccentInkDark,
    errorContainer = DuskColors.DangerSoftDark,
    onErrorContainer = DuskColors.DangerDark,
)

private val DuskLightScheme = lightColorScheme(
    primary = DuskColors.AccentLight,
    onPrimary = DuskColors.AccentInkLight,
    primaryContainer = DuskColors.AccentSoftLight,
    onPrimaryContainer = DuskColors.AccentDimLight,
    secondary = DuskColors.GoldLight,
    onSecondary = DuskColors.AccentInkLight,
    secondaryContainer = DuskColors.Surface2Light,
    onSecondaryContainer = DuskColors.GoldLight,
    tertiary = DuskColors.GoldLight,
    onTertiary = DuskColors.AccentInkLight,
    tertiaryContainer = DuskColors.Surface2Light,
    onTertiaryContainer = DuskColors.GoldLight,
    background = DuskColors.BgLight,
    onBackground = DuskColors.TextLight,
    surface = DuskColors.SurfaceLight,
    onSurface = DuskColors.TextLight,
    surfaceVariant = DuskColors.Surface2Light,
    onSurfaceVariant = DuskColors.TextDimLight,
    surfaceContainerLowest = DuskColors.SurfaceLight,
    surfaceContainerLow = DuskColors.BgAltLight,
    surfaceContainer = DuskColors.Surface2Light,
    surfaceContainerHigh = DuskColors.SurfaceHiLight,
    surfaceContainerHighest = DuskColors.LineLight,
    outline = DuskColors.LineLight,
    outlineVariant = DuskColors.LineHiLight,
    error = DuskColors.DangerLight,
    onError = DuskColors.AccentInkLight,
    errorContainer = DuskColors.DangerSoftLight,
    onErrorContainer = DuskColors.DangerLight,
)

private val DarkDuskTokens = DuskTokens(
    bgAlt = DuskColors.BgAltDark,
    surface2 = DuskColors.Surface2Dark,
    surfaceHi = DuskColors.SurfaceHiDark,
    lineHi = DuskColors.LineHiDark,
    textDim = DuskColors.TextDimDark,
    textSub = DuskColors.TextSubDark,
    accentDim = DuskColors.AccentDimDark,
    accentSoft = DuskColors.AccentSoftDark,
    accentInk = DuskColors.AccentInkDark,
    gold = DuskColors.GoldDark,
    dangerSoft = DuskColors.DangerSoftDark,
)

private val LightDuskTokens = DuskTokens(
    bgAlt = DuskColors.BgAltLight,
    surface2 = DuskColors.Surface2Light,
    surfaceHi = DuskColors.SurfaceHiLight,
    lineHi = DuskColors.LineHiLight,
    textDim = DuskColors.TextDimLight,
    textSub = DuskColors.TextSubLight,
    accentDim = DuskColors.AccentDimLight,
    accentSoft = DuskColors.AccentSoftLight,
    accentInk = DuskColors.AccentInkLight,
    gold = DuskColors.GoldLight,
    dangerSoft = DuskColors.DangerSoftLight,
)

/**
 * CompositionLocal that exposes Dusk-specific semantic tokens (alt-bg,
 * accent-soft, textSub, gold, etc.) not covered by Material 3's colour-scheme
 * slots. Consumed inside screens via `LocalDuskTokens.current`.
 */
val LocalDuskTokens = compositionLocalOf { DarkDuskTokens }

@Composable
fun SessionZeroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context = context)
            else dynamicLightColorScheme(context = context)
        }
        darkTheme -> DuskDarkScheme
        else -> DuskLightScheme
    }

    val duskTokens = if (darkTheme) DarkDuskTokens else LightDuskTokens

    CompositionLocalProvider(
        LocalDuskTokens provides duskTokens,
        LocalDuskTypography provides DefaultDuskTypography,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}
