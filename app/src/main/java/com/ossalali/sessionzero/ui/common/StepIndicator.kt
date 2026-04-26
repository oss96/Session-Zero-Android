package com.ossalali.sessionzero.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

/**
 * Thin wrapper around [DuskStepper] preserved for backwards compatibility.
 * Existing call-sites (notably `WizardContent`) keep working; new code
 * should call `DuskStepper` directly.
 */
@Composable
fun StepIndicator(
    modifier: Modifier = Modifier,
    steps: List<String>,
    currentStep: Int,
    onStepClick: (Int) -> Unit = {},
) {
    DuskStepper(
        modifier = modifier,
        steps = steps,
        currentStep = currentStep,
        onStepClick = onStepClick,
    )
}

@PreviewLightDark
@Composable
private fun StepIndicatorPreview() {
    SessionZeroTheme {
        StepIndicator(
            steps = listOf(
                "Class", "Species", "Background", "Abilities",
                "Skills", "Equipment", "Details", "Review",
            ),
            currentStep = 3,
        )
    }
}
