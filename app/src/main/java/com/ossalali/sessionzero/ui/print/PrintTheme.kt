package com.ossalali.sessionzero.ui.print

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val PrintColorScheme = lightColorScheme(
    primary = Color(0xFF555555),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEEEEEE),
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFF666666),
    onSecondary = Color.White,
    tertiary = Color(0xFF888888),
    onTertiary = Color.White,
    error = Color(0xFFCC4444),
    onError = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = Color(0xFF555555),
    outline = Color(0xFFCCCCCC),
    outlineVariant = Color(0xFFDDDDDD),
)

private val PrintTypography = Typography(
    headlineMedium = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black),
    titleMedium = TextStyle(fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF555555)),
    titleSmall = TextStyle(fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Black),
    bodyLarge = TextStyle(fontSize = 8.sp, color = Color.Black),
    bodyMedium = TextStyle(fontSize = 7.sp, color = Color.Black),
    bodySmall = TextStyle(fontSize = 6.sp, color = Color(0xFF555555)),
    labelSmall = TextStyle(fontSize = 6.sp, fontWeight = FontWeight.Medium, color = Color(0xFF555555)),
    labelMedium = TextStyle(fontSize = 7.sp, fontWeight = FontWeight.Medium, color = Color(0xFF555555)),
)

@Composable
fun PrintTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PrintColorScheme,
        typography = PrintTypography,
        content = content,
    )
}
