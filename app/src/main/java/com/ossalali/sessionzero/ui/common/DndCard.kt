package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun DndCard(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        border = if (selected) {
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        } else null,
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            content()
        }
    }
}

@PreviewLightDark
@Composable
private fun DndCardPreview() {
    SessionZeroTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            DndCard(selected = false) {
                Text("Unselected Card")
            }
            DndCard(selected = true, modifier = Modifier.padding(top = 8.dp)) {
                Text("Selected Card")
            }
        }
    }
}
