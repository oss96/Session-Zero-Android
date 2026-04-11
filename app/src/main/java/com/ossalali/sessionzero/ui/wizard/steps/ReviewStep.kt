package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.rules.GameRules
import com.ossalali.sessionzero.ui.common.AbilityScoreBox
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun ReviewStep(
    character: Character,
    derivedStats: DerivedStats,
    isSaving: Boolean = false,
    onSave: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        SectionHeader(text = "Character Summary")

        ReviewRow(label = "Name", value = character.name.ifEmpty { "Unnamed" })
        ReviewRow(label = "Class", value = character.className?.displayName ?: "None")
        ReviewRow(label = "Level", value = "${character.level}")
        character.subclass?.let { ReviewRow(label = "Subclass", value = it) }
        ReviewRow(label = "Species", value = character.species?.displayName ?: "None")
        character.speciesLineage?.let { ReviewRow(label = "Lineage", value = it) }
        ReviewRow(label = "Background", value = character.background?.displayName ?: "None")
        character.alignment?.let { ReviewRow(label = "Alignment", value = it.displayName) }

        Spacer(modifier = Modifier.height(height = 16.dp))
        SectionHeader(text = "Ability Scores")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            val scores = character.totalAbilityScores
            AbilityName.entries.forEach { ability ->
                val score = scores[ability]
                AbilityScoreBox(
                    label = ability.name,
                    score = score,
                    signModifier = GameRules.abilityModifier(score),
                )
            }
        }

        Spacer(modifier = Modifier.height(height = 16.dp))
        SectionHeader(text = "Combat Stats")

        ReviewRow(label = "Max HP", value = "${derivedStats.maxHP}")
        ReviewRow(label = "AC", value = "${derivedStats.armorClass}")
        ReviewRow(label = "Initiative", value = "+${derivedStats.initiative}")
        ReviewRow(label = "Speed", value = "${derivedStats.speed}ft")
        ReviewRow(label = "Hit Dice", value = derivedStats.hitDice)
        ReviewRow(label = "Proficiency Bonus", value = "+${derivedStats.proficiencyBonus}")
        derivedStats.spellSaveDC?.let { ReviewRow(label = "Spell Save DC", value = "$it") }
        derivedStats.spellAttackBonus?.let { ReviewRow(label = "Spell Attack", value = "+$it") }

        if (character.skillProficiencies.isNotEmpty()) {
            Spacer(modifier = Modifier.height(height = 16.dp))
            SectionHeader(text = "Skill Proficiencies")
            Text(text = character.skillProficiencies.joinToString(separator = ", ") { it.displayName })
        }

        if (character.equipment.isNotEmpty()) {
            Spacer(modifier = Modifier.height(height = 16.dp))
            SectionHeader(text = "Equipment")
            character.equipment.forEach { item ->
                Text(text = "- ${item.name}${if (item.quantity > 1) " x${item.quantity}" else ""}")
            }
        }

        Spacer(modifier = Modifier.height(height = 24.dp))

        val nameValid = character.name.isNotBlank()
        if (!nameValid) {
            Text(
                text = "Name is required before saving",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp),
            )
        }

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSaving && nameValid,
        ) {
            if (isSaving) {
                CircularProgressIndicator(modifier = Modifier.padding(all = 4.dp))
            } else {
                Text(text = "Save Character")
            }
        }
    }
}

@Composable
private fun ReviewRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@PreviewLightDark
@Composable
private fun ReviewStepPreview() {
    SessionZeroTheme {
        ReviewStep(
            character = PreviewData.sampleCharacter,
            derivedStats = PreviewData.sampleDerivedStats,
        )
    }
}

@PreviewLightDark
@Composable
private fun ReviewStepSavingPreview() {
    SessionZeroTheme {
        ReviewStep(
            character = PreviewData.sampleCharacter,
            derivedStats = PreviewData.sampleDerivedStats,
            isSaving = true,
        )
    }
}
