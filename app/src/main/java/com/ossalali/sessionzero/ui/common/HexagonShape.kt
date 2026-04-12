package com.ossalali.sessionzero.ui.common

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Regular (symmetrical) pointy-top hexagon centered in the bounding box.
 * All six sides are equal length.
 */
val HexagonShape: Shape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val w = size.width
        val h = size.height

        // A regular pointy-top hexagon with radius r has:
        //   width  = r * sqrt(3)
        //   height = r * 2
        // Find the largest regular hexagon that fits in the bounding box.
        val rFromWidth = w / sqrt(3f)
        val rFromHeight = h / 2f
        val r = min(rFromWidth, rFromHeight)

        val cx = w / 2f
        val cy = h / 2f
        val halfW = r * sqrt(3f) / 2f

        val path = Path().apply {
            // Top point
            moveTo(x = cx, y = cy - r)
            // Top-right
            lineTo(x = cx + halfW, y = cy - r / 2f)
            // Bottom-right
            lineTo(x = cx + halfW, y = cy + r / 2f)
            // Bottom point
            lineTo(x = cx, y = cy + r)
            // Bottom-left
            lineTo(x = cx - halfW, y = cy + r / 2f)
            // Top-left
            lineTo(x = cx - halfW, y = cy - r / 2f)
            close()
        }
        return Outline.Generic(path = path)
    }
}
