package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

/**
 * Bottom bar for the wizard flow. Outlined ghost "Back" on the left,
 * pill-shaped filled primary button on the right. The right-side label
 * and action flip to "Save Character" on the final step, with an inline
 * checkmark.
 */
@Composable
fun WizardFooter(
    modifier: Modifier = Modifier,
    showPrevious: Boolean,
    isLastStep: Boolean,
    nextLabel: String = "Continue",
    lastStepLabel: String = "Save Character",
    isSaving: Boolean = false,
    nextEnabled: Boolean = true,
    saveEnabled: Boolean = true,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onSave: () -> Unit,
) {
    val tokens = LocalDuskTokens.current
    androidx.compose.foundation.layout.Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = tokens.bgAlt),
    ) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
        ) {
            if (showPrevious) {
                OutlinedButton(
                    onClick = onPrevious,
                    shape = CircleShape,
                ) {
                    Text(text = "Back")
                }
            } else {
                Spacer(modifier = Modifier.size(size = 1.dp))
            }
            Spacer(modifier = Modifier.weight(weight = 1f))
            if (isLastStep) {
                Button(
                    onClick = onSave,
                    enabled = saveEnabled && !isSaving,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(size = 16.dp),
                    )
                    Spacer(modifier = Modifier.size(size = 6.dp))
                    Text(text = if (isSaving) "Saving…" else lastStepLabel)
                }
            } else {
                Button(
                    onClick = onNext,
                    enabled = nextEnabled,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                ) {
                    Text(text = nextLabel)
                    Spacer(modifier = Modifier.size(size = 6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(size = 16.dp),
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun WizardFooterPreview() {
    SessionZeroTheme {
        WizardFooter(
            showPrevious = true,
            isLastStep = false,
            onPrevious = {},
            onNext = {},
            onSave = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun WizardFooterLastPreview() {
    SessionZeroTheme {
        WizardFooter(
            showPrevious = true,
            isLastStep = true,
            lastStepLabel = "Begin Adventure",
            onPrevious = {},
            onNext = {},
            onSave = {},
        )
    }
}
