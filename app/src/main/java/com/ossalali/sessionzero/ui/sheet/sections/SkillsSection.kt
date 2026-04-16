package com.ossalali.sessionzero.ui.sheet.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.model.SkillName
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun SkillsSection(
    character: Character,
    derivedStats: DerivedStats,
) {
    Column(horizontalAlignment = Alignment.Start) {
        SectionHeader(text = "Skills")
        SkillName.entries.forEach { skill ->
            val bonus = derivedStats.skillBonuses[skill] ?: 0
            val isProficient = skill in character.skillProficiencies
            val sign = if (bonus >= 0) "+" else ""
            Text(
                text = "${if (isProficient) "●" else "○"} ${skill.displayName}: $sign$bonus",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SkillsSectionPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 16.dp),
        ) {
            SkillsSection(
                character = PreviewData.sampleCharacter,
                derivedStats = PreviewData.sampleDerivedStats,
            )
        }
    }
}
