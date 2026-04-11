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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.rules.GameRules
import com.ossalali.sessionzero.ui.common.AbilityScoreBox
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.wizard.WizardViewModel

@Composable
fun ReviewStep(
    character: Character,
    derivedStats: DerivedStats,
    viewModel: WizardViewModel,
) {
    val isSaving by viewModel.isSaving.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        SectionHeader(text = "Character Summary")

        // Basic Info
        ReviewRow("Name", character.name.ifEmpty { "Unnamed" })
        ReviewRow("Class", character.className?.displayName ?: "None")
        ReviewRow("Level", "${character.level}")
        character.subclass?.let { ReviewRow("Subclass", it) }
        ReviewRow("Species", character.species?.displayName ?: "None")
        character.speciesLineage?.let { ReviewRow("Lineage", it) }
        ReviewRow("Background", character.background?.displayName ?: "None")
        character.alignment?.let { ReviewRow("Alignment", it.displayName) }

        Spacer(Modifier.height(16.dp))
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

        Spacer(Modifier.height(16.dp))
        SectionHeader(text = "Combat Stats")

        ReviewRow("Max HP", "${derivedStats.maxHP}")
        ReviewRow("AC", "${derivedStats.armorClass}")
        ReviewRow("Initiative", "+${derivedStats.initiative}")
        ReviewRow("Speed", "${derivedStats.speed}ft")
        ReviewRow("Hit Dice", derivedStats.hitDice)
        ReviewRow("Proficiency Bonus", "+${derivedStats.proficiencyBonus}")
        derivedStats.spellSaveDC?.let { ReviewRow("Spell Save DC", "$it") }
        derivedStats.spellAttackBonus?.let { ReviewRow("Spell Attack", "+$it") }

        if (character.skillProficiencies.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            SectionHeader(text = "Skill Proficiencies")
            Text(character.skillProficiencies.joinToString(", ") { it.displayName })
        }

        if (character.equipment.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            SectionHeader(text = "Equipment")
            character.equipment.forEach { item ->
                Text("- ${item.name}${if (item.quantity > 1) " x${item.quantity}" else ""}")
            }
        }

        Spacer(Modifier.height(24.dp))

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
            onClick = { viewModel.saveCharacter() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSaving && nameValid,
        ) {
            if (isSaving) {
                CircularProgressIndicator(modifier = Modifier.padding(4.dp))
            } else {
                Text("Save Character")
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
