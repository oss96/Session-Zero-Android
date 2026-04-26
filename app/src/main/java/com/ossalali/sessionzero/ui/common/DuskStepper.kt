package com.ossalali.sessionzero.ui.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

/**
 * Dusk step indicator — thin gradient progress bar above a horizontal row
 * of pill chips. Pills have three states:
 *  - **active**: 2dp accent border + accent-soft background
 *  - **done**: 1dp lineHi border + leading check glyph
 *  - **pending**: 1dp outline border, dimmed text
 *
 * Replaces the earlier `InputChip`-based indicator. The outer signature
 * (`steps` / `currentStep` / `onStepClick`) is preserved for drop-in
 * compatibility with callers.
 */
@Composable
fun DuskStepper(
    modifier: Modifier = Modifier,
    steps: List<String>,
    currentStep: Int,
    onStepClick: (Int) -> Unit = {},
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val listState = rememberLazyListState()

    LaunchedEffect(currentStep) {
        listState.animateScrollToItem(index = currentStep)
    }

    val targetFraction = if (steps.isEmpty()) 0f
    else ((currentStep + 1).toFloat() / steps.size.toFloat()).coerceIn(0f, 1f)
    val fraction by animateFloatAsState(targetValue = targetFraction, label = "stepProgress")

    Column(modifier = modifier.fillMaxWidth()) {
        // Gradient progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(height = 3.dp)
                .clip(shape = RoundedCornerShape(size = 2.dp))
                .background(color = scheme.outline),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = fraction)
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(tokens.accentDim, scheme.primary),
                        ),
                    ),
            )
        }
        Spacer(modifier = Modifier.height(height = 10.dp))

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            itemsIndexed(items = steps) { index, label ->
                DuskStepPill(
                    label = label,
                    state = when {
                        index == currentStep -> PillState.Active
                        index < currentStep -> PillState.Done
                        else -> PillState.Pending
                    },
                    onClick = { onStepClick(index) },
                )
            }
        }
    }
}

private enum class PillState { Pending, Active, Done }

@Composable
private fun DuskStepPill(
    label: String,
    state: PillState,
    onClick: () -> Unit,
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme

    val bg: Color = when (state) {
        PillState.Active -> tokens.accentSoft
        PillState.Done, PillState.Pending -> Color.Transparent
    }
    val borderColor: Color = when (state) {
        PillState.Active -> scheme.primary
        PillState.Done -> tokens.lineHi
        PillState.Pending -> scheme.outline
    }
    val borderWidth = if (state == PillState.Active) 2.dp else 1.dp
    val textColor: Color = when (state) {
        PillState.Active -> scheme.onSurface
        PillState.Done -> scheme.onSurface
        PillState.Pending -> tokens.textDim
    }
    val fontWeight = if (state == PillState.Active) FontWeight.SemiBold else FontWeight.Medium

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(shape = CircleShape)
            .background(color = bg, shape = CircleShape)
            .border(width = borderWidth, color = borderColor, shape = CircleShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        if (state == PillState.Done) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = scheme.primary,
                modifier = Modifier
                    .size(size = 11.dp)
                    .padding(end = 4.dp),
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = fontWeight,
        )
    }
}

@PreviewLightDark
@Composable
private fun DuskStepperPreview() {
    SessionZeroTheme {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            DuskStepper(
                steps = listOf(
                    "Class", "Species", "Background", "Abilities",
                    "Skills", "Equipment", "Weapons", "Details", "Review",
                ),
                currentStep = 3,
            )
        }
    }
}
