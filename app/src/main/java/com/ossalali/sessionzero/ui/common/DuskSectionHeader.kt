package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

/**
 * Dusk section header — tiny uppercase mono "kicker" label above a
 * display-style title rendered in the primary/accent colour. An optional
 * trailing slot ([trailing]) is rendered on the right-hand edge for
 * inline actions like "Add" buttons.
 */
@Composable
fun DuskSectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    kicker: String? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 10.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(modifier = Modifier.padding(end = 8.dp)) {
            if (kicker != null) {
                MonoLabel(text = kicker)
                Spacer(modifier = Modifier.height(height = 2.dp))
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        if (trailing != null) trailing()
    }
}

@PreviewLightDark
@Composable
private fun DuskSectionHeaderPreview() {
    SessionZeroTheme {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            DuskSectionHeader(title = "Choose a class", kicker = "Calling")
            DuskSectionHeader(title = "No kicker")
        }
    }
}
