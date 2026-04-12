package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

val HeartShape: Shape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val w = size.width
        val h = size.height

        val path = Path().apply {
            // Bottom point
            moveTo(x = w * 0.5f, y = h)

            // Left side curve up to left lobe
            cubicTo(
                x1 = w * 0.15f, y1 = h * 0.75f,
                x2 = 0f, y2 = h * 0.5f,
                x3 = 0f, y3 = h * 0.35f,
            )

            // Left lobe
            cubicTo(
                x1 = 0f, y1 = h * 0.15f,
                x2 = w * 0.1f, y2 = 0f,
                x3 = w * 0.25f, y3 = 0f,
            )

            // Left lobe to center dip
            cubicTo(
                x1 = w * 0.4f, y1 = 0f,
                x2 = w * 0.5f, y2 = h * 0.1f,
                x3 = w * 0.5f, y3 = h * 0.2f,
            )

            // Center dip to right lobe
            cubicTo(
                x1 = w * 0.5f, y1 = h * 0.1f,
                x2 = w * 0.6f, y2 = 0f,
                x3 = w * 0.75f, y3 = 0f,
            )

            // Right lobe
            cubicTo(
                x1 = w * 0.9f, y1 = 0f,
                x2 = w, y2 = h * 0.15f,
                x3 = w, y3 = h * 0.35f,
            )

            // Right side curve down to bottom point
            cubicTo(
                x1 = w, y1 = h * 0.5f,
                x2 = w * 0.85f, y2 = h * 0.75f,
                x3 = w * 0.5f, y3 = h,
            )

            close()
        }
        return Outline.Generic(path = path)
    }
}

@PreviewLightDark
@Composable
private fun HeartShapePreview() {
    SessionZeroTheme {
        Box(
            modifier = Modifier
                .padding(all = 16.dp)
                .size(size = 80.dp)
                .background(
                    color = MaterialTheme.colorScheme.error,
                    shape = HeartShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "HP",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onError,
                )
                Text(
                    text = "44",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onError,
                )
            }
        }
    }
}
