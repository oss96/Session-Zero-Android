package com.ossalali.sessionzero.ui.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme

    Column(
        modifier = modifier.fillMaxSize().padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Dashed-circle d20 glyph with a radial accent glow
        Box(
            modifier = Modifier.size(size = 120.dp),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(tokens.accentSoft, scheme.background),
                        ),
                    ),
            )
            Canvas(
                modifier = Modifier
                    .size(size = 90.dp)
                    .padding(all = 4.dp),
            ) {
                drawCircle(
                    color = tokens.lineHi,
                    radius = size.minDimension / 2f,
                    style = Stroke(
                        width = 1.5f,
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(6f, 6f),
                        ),
                    ),
                )
            }
            Icon(
                imageVector = Icons.Default.Casino,
                contentDescription = null,
                tint = scheme.primary,
                modifier = Modifier.size(size = 50.dp),
            )
        }
        Spacer(modifier = Modifier.height(height = 16.dp))
        Text(
            text = "No heroes yet",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(height = 6.dp))
        Text(
            text = buildAnnotatedString {
                append("Every legend starts somewhere. Tap the ")
                withStyle(style = SpanStyle(color = scheme.primary, fontWeight = FontWeight.Bold)) {
                    append("+")
                }
                append(" below to forge your first character.")
            },
            style = MaterialTheme.typography.bodyMedium,
            color = tokens.textDim,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 280.dp),
        )
    }
}

@PreviewLightDark
@Composable
private fun EmptyStatePreview() {
    SessionZeroTheme(dynamicColor = false) {
        EmptyState(modifier = Modifier.fillMaxWidth().height(height = 500.dp))
    }
}
