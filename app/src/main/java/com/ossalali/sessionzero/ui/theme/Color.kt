package com.ossalali.sessionzero.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Session Zero "Dusk" palette — a refined dark + teal brand system.
 *
 * Tokens are split into dark / light variants that map onto Material 3
 * colour-scheme slots (see [Theme.kt]) and a set of Dusk-specific semantic
 * values (textSub, accentSoft, gold, etc.) exposed via [LocalDuskTokens].
 */
object DuskColors {
    // ── Dark (default) ──────────────────────────────────────────────────────
    val BgDark = Color(0xFF0B1414)
    val BgAltDark = Color(0xFF0F1A1A)
    val SurfaceDark = Color(0xFF16201F)
    val Surface2Dark = Color(0xFF1C2928)
    val SurfaceHiDark = Color(0xFF223432)
    val LineDark = Color(0xFF2A3A38)
    val LineHiDark = Color(0xFF3A4E4B)
    val TextDark = Color(0xFFE6EFED)
    val TextDimDark = Color(0xFF9EB1AD)
    val TextSubDark = Color(0xFF6B817D)
    val AccentDark = Color(0xFF5DCCBA)
    val AccentDimDark = Color(0xFF3BA08E)
    val AccentSoftDark = Color(0xFF1D3734)
    val AccentInkDark = Color(0xFF0B1414)
    val DangerDark = Color(0xFFE89595)
    val DangerSoftDark = Color(0xFF3A1F1F)
    val GoldDark = Color(0xFFD4B572)

    // ── Light ───────────────────────────────────────────────────────────────
    val BgLight = Color(0xFFF6F8F7)
    val BgAltLight = Color(0xFFEEF3F1)
    val SurfaceLight = Color(0xFFFFFFFF)
    val Surface2Light = Color(0xFFF0F5F3)
    val SurfaceHiLight = Color(0xFFE3ECE9)
    val LineLight = Color(0xFFD4DEDB)
    val LineHiLight = Color(0xFFB8C6C2)
    val TextLight = Color(0xFF0F1A1A)
    val TextDimLight = Color(0xFF47605C)
    val TextSubLight = Color(0xFF7A908C)
    val AccentLight = Color(0xFF0F6B5E)
    val AccentDimLight = Color(0xFF0A4E44)
    val AccentSoftLight = Color(0xFFD6EBE6)
    val AccentInkLight = Color(0xFFFFFFFF)
    val DangerLight = Color(0xFFA12828)
    val DangerSoftLight = Color(0xFFF5DCDC)
    val GoldLight = Color(0xFF8A6A1F)
}

/**
 * Dusk-specific semantic tokens that don't cleanly map onto Material 3's
 * colour-scheme slots — exposed via a CompositionLocal so screens can reach
 * them without threading props.
 */
data class DuskTokens(
    val bgAlt: Color,
    val surface2: Color,
    val surfaceHi: Color,
    val lineHi: Color,
    val textDim: Color,
    val textSub: Color,
    val accentDim: Color,
    val accentSoft: Color,
    val accentInk: Color,
    val gold: Color,
    val dangerSoft: Color,
)
