package com.ossalali.sessionzero.ui.sheet.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun PersonalitySection(character: Character) {
    SectionHeader(text = "Personality")
    if (character.personalityTraits.isNotEmpty()) {
        Text(
            text = "Traits: ${character.personalityTraits}",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
    if (character.ideals.isNotEmpty()) {
        Text(text = "Ideals: ${character.ideals}", style = MaterialTheme.typography.bodyMedium)
    }
    if (character.bonds.isNotEmpty()) {
        Text(text = "Bonds: ${character.bonds}", style = MaterialTheme.typography.bodyMedium)
    }
    if (character.flaws.isNotEmpty()) {
        Text(text = "Flaws: ${character.flaws}", style = MaterialTheme.typography.bodyMedium)
    }
}

@PreviewLightDark
@Composable
private fun PersonalitySectionPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 16.dp),
        ) {
            PersonalitySection(character = PreviewData.sampleCharacter)
        }
    }
}
