package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    val scrollState = rememberScrollState()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(state = scrollState)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        steps.forEachIndexed { index, label ->
            val selected = index <= currentStep
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                InputChip(
                    selected = selected,
                    onClick = { onStepClick(index) },
                    label = {
                        Text(
                            text = "${index + 1}",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    })
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier.padding(top = 2.dp),
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
                "Review"
            ),
            currentStep = 3,
        )
    }
}
