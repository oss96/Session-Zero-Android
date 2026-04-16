package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.R
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun DiamondStat(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
) {
    Box(
        modifier = modifier
            .size(size = 80.dp),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier
                .rotate(degrees = 45f)
                .size(size = 80.dp),
            painter = painterResource(R.drawable.square_24px),
            contentDescription = "HP",
            tint = MaterialTheme.colorScheme.error
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onError,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onError,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun HeartStatPreview() {
    SessionZeroTheme {
        DiamondStat(
            modifier = Modifier.padding(all = 16.dp),
            label = "HP",
            value = "44",
        )
    }
}
