package com.ossalali.sessionzero.ui.sheet.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.rules.GameRules
import com.ossalali.sessionzero.ui.common.AbilityScoreBox
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun AbilityScoresSection(character: Character) {
    SectionHeader(text = "Ability Scores")
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        maxItemsInEachRow = 3,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        val scores = character.totalAbilityScores
        AbilityName.entries.forEach { ability ->
            val score = scores[ability]
            AbilityScoreBox(
                modifier = Modifier.padding(all = 8.dp),
                label = ability.name,
                score = score,
                signModifier = GameRules.abilityModifier(score),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AbilityScoresSectionPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 16.dp),
        ) {
            AbilityScoresSection(character = PreviewData.sampleCharacter)
        }
    }
}
