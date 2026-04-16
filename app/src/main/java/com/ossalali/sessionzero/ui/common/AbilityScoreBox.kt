package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun AbilityScoreBox(
    modifier: Modifier = Modifier,
    label: String,
    score: Int,
    signModifier: Int,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = modifier.size(72.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            RectangleScore(
                modifier = Modifier,
                label = label,
                value = "$score",
                signModifier = signModifier
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AbilityScoreBoxPreview() {
    SessionZeroTheme {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AbilityScoreBox(label = "STR", score = 18, signModifier = 4)
            AbilityScoreBox(label = "DEX", score = 14, signModifier = 2)
            AbilityScoreBox(label = "CON", score = 14, signModifier = -1)
            AbilityScoreBox(label = "INT", score = 8, signModifier = -1)
            AbilityScoreBox(label = "WIS", score = 12, signModifier = -1)
            AbilityScoreBox(label = "CHA", score = 8, signModifier = -1)
        }
    }
}
