package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun RectangleScore(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    signModifier: Int,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = label, style = MaterialTheme.typography.labelSmall)
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        val sign = if (signModifier >= 0) "+" else ""
        Text(
            text = "$sign$signModifier",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@PreviewLightDark
@Composable
private fun RectangleScorePreview() {
    SessionZeroTheme {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            RectangleStat(label = "AC", value = "18")
            RectangleStat(label = "HP", value = "44")
            RectangleStat(label = "Init", value = "+2")
        }
    }
}
