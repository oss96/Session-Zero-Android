package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

/**
 * Dusk form field: uppercase mono label placed above an outlined text
 * field. Surface background, line outline, 10dp corner radius. Mirrors the
 * `TextField`/`TextArea` composables in the Dusk prototype.
 */
@Composable
fun DuskTextField(
    modifier: Modifier = Modifier,
    label: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    singleLine: Boolean = true,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val scheme = MaterialTheme.colorScheme
    Column(modifier = modifier) {
        if (label != null) {
            MonoLabel(text = label)
            Spacer(modifier = Modifier.height(height = 4.dp))
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder?.let { { Text(text = it) } },
            singleLine = singleLine,
            minLines = minLines,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            readOnly = readOnly,
            trailingIcon = trailingIcon,
            shape = RoundedCornerShape(size = 10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = scheme.surface,
                unfocusedContainerColor = scheme.surface,
                disabledContainerColor = scheme.surface,
                focusedBorderColor = scheme.primary,
                unfocusedBorderColor = scheme.outline,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@PreviewLightDark
@Composable
private fun DuskTextFieldPreview() {
    SessionZeroTheme {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            DuskTextField(
                label = "Character name",
                value = "Kurruk Anakalathai",
                onValueChange = {},
            )
        }
    }
}
