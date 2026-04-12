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
import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.AbilityScores
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.ClassDefinition
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun SavingThrowsSection(
    derivedStats: DerivedStats,
    classDef: ClassDefinition?,
) {
    SectionHeader(text = "Saving Throws")
    AbilityName.entries.forEach { ability ->
        val bonus = derivedStats.savingThrows[ability] ?: 0
        val isProficient = classDef?.savingThrows?.contains(ability) == true
        val sign = if (bonus >= 0) "+" else ""
        Text(
            text = "${if (isProficient) "●" else "○"} ${AbilityScores.ABILITY_LABELS[ability]}: $sign$bonus",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@PreviewLightDark
@Composable
private fun SavingThrowsSectionPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 16.dp),
        ) {
            SavingThrowsSection(
                derivedStats = PreviewData.sampleDerivedStats,
                classDef = com.ossalali.sessionzero.domain.rules.ClassData.ALL_CLASSES.find {
                    it.name == PreviewData.sampleCharacter.className
                },
            )
        }
    }
}
