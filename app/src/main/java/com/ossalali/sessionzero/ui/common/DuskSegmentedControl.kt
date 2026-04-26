package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

/**
 * Pill-shaped segmented control — N equal-weight segments inside a single
 * rounded container. Active segment fills with the primary colour and
 * flips its label to the on-primary ink colour.
 *
 * Segments are identified by [options] (labels) and [selectedIndex].
 */
@Composable
fun DuskSegmentedControl(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val containerShape = CircleShape

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = containerShape)
            .background(color = scheme.surface, shape = containerShape)
            .border(width = 1.dp, color = scheme.outline, shape = containerShape)
            .padding(all = 3.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        options.forEachIndexed { index, label ->
            val active = index == selectedIndex
            val segmentShape = CircleShape
            Box(
                modifier = Modifier
                    .weight(weight = 1f)
                    .clip(shape = segmentShape)
                    .background(color = if (active) scheme.primary else androidx.compose.ui.graphics.Color.Transparent)
                    .clickable { onSelect(index) }
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = if (active) FontWeight.SemiBold else FontWeight.Medium,
                    color = if (active) scheme.onPrimary else tokens.textDim,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DuskSegmentedControlPreview() {
    SessionZeroTheme {
        Box(modifier = Modifier.padding(all = 16.dp)) {
            DuskSegmentedControl(
                options = listOf("Point Buy", "Array", "Roll", "Manual"),
                selectedIndex = 2,
                onSelect = {},
            )
        }
    }
}
