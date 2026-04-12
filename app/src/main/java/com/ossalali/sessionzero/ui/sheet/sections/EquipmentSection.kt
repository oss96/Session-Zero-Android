package com.ossalali.sessionzero.ui.sheet.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun EquipmentSection(character: Character) {
    if (character.equipment.isNotEmpty()) {
        SectionHeader(text = "Equipment")
        character.equipment.forEach { item ->
            Text(
                text = "- ${item.name}${if (item.quantity > 1) " x${item.quantity}" else ""}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(modifier = Modifier.height(height = 8.dp))
    }

    val coins = character.coins
    if (coins.cp > 0 || coins.sp > 0 || coins.gp > 0 || coins.pp > 0) {
        Text(
            text = "Coins: ${coins.cp} CP, ${coins.sp} SP, ${coins.gp} GP, ${coins.pp} PP",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@PreviewLightDark
@Composable
private fun EquipmentSectionPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 16.dp),
        ) {
            EquipmentSection(character = PreviewData.sampleCharacter)
        }
    }
}
