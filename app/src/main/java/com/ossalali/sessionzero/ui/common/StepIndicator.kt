package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun StepIndicator(
    modifier: Modifier = Modifier,
    steps: List<String>,
    currentStep: Int,
    onStepClick: (Int) -> Unit = {},
) {
    val listState = rememberLazyListState()

    LaunchedEffect(currentStep) {
        listState.animateScrollToItem(index = currentStep)
    }

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = listState,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        itemsIndexed(items = steps) { index, label ->
            val selected = index <= currentStep
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                InputChip(
                    selected = selected,
                    onClick = { onStepClick(index) },
                    label = {
                        Text(
                            text = label,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    },
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun StepIndicatorPreview() {
    SessionZeroTheme {
        StepIndicator(
            steps = listOf(
                "Class",
                "Species",
                "Background",
                "Abilities",
                "Skills",
                "Equipment",
                "Details",
                "Review",
            ),
            currentStep = 3,
        )
    }
}
