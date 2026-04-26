package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

/**
 * Compact section header. When [kicker] is non-null, renders a small
 * uppercase mono kicker above the title (matches the Dusk style).
 */
@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    text: String,
    kicker: String? = null,
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
    ) {
        if (kicker != null) {
            MonoLabel(text = kicker)
            Spacer(modifier = Modifier.height(height = 2.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@PreviewLightDark
@Composable
private fun SectionHeaderPreview() {
    SessionZeroTheme {
        Column {
            SectionHeader(text = "Ability Scores")
            SectionHeader(text = "Choose a class", kicker = "Calling")
        }
    }
}
