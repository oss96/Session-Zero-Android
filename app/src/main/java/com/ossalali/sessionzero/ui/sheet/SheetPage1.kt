package com.ossalali.sessionzero.ui.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme
import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.AbilityScores
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.model.SkillName
import com.ossalali.sessionzero.domain.rules.GameRules
import com.ossalali.sessionzero.ui.common.AbilityScoreBox
import com.ossalali.sessionzero.ui.common.SectionHeader

@Composable
fun SheetPage1(
    character: Character,
    derivedStats: DerivedStats,
) {
    // Header
    Column {
        Text(
            text = character.name.ifEmpty { "Unnamed Character" },
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        val subtitle = buildList {
            character.className?.let { add("Level ${character.level} ${it.displayName}") }
            character.subclass?.let { add(it) }
            character.species?.let { add(it.displayName) }
            character.background?.let { add(it.displayName) }
        }.joinToString(" | ")
        if (subtitle.isNotEmpty()) {
            Text(subtitle, style = MaterialTheme.typography.bodyLarge)
        }
        character.alignment?.let {
            Text(it.displayName, style = MaterialTheme.typography.bodyMedium)
        }
    }

    Spacer(Modifier.height(16.dp))
    HorizontalDivider()
    Spacer(Modifier.height(16.dp))

    // Ability Scores
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

    // Saving Throws
    SectionHeader(text = "Saving Throws")
    val classDef = character.className?.let {
        com.ossalali.sessionzero.domain.rules.ClassData.ALL_CLASSES.find { c -> c.name == it }
    }
    AbilityName.entries.forEach { ability ->
        val bonus = derivedStats.savingThrows[ability] ?: 0
        val isProficient = classDef?.savingThrows?.contains(ability) == true
        val sign = if (bonus >= 0) "+" else ""
        Text(
            text = "${if (isProficient) "●" else "○"} ${AbilityScores.ABILITY_LABELS[ability]}: $sign$bonus",
            style = MaterialTheme.typography.bodyMedium,
        )
    }

    Spacer(Modifier.height(16.dp))

    // Skills
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

    Spacer(Modifier.height(16.dp))

    // Combat Stats
    SectionHeader(text = "Combat")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        CombatStatCard("AC", "${derivedStats.armorClass}")
        CombatStatCard("HP", "${derivedStats.maxHP}")
        CombatStatCard("Init", "+${derivedStats.initiative}")
        CombatStatCard("Speed", "${derivedStats.speed}ft")
    }

    Spacer(Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        CombatStatCard("Hit Dice", derivedStats.hitDice)
        CombatStatCard("Prof", "+${derivedStats.proficiencyBonus}")
    }

    Spacer(Modifier.height(8.dp))
    Text("Passive Perception: ${derivedStats.passivePerception}")
    Text("Passive Investigation: ${derivedStats.passiveInvestigation}")
    Text("Passive Insight: ${derivedStats.passiveInsight}")

    // Weapons
    if (character.weapons.isNotEmpty()) {
        Spacer(Modifier.height(16.dp))
        SectionHeader(text = "Weapons")
        character.weapons.forEach { weapon ->
            Text(
                "${weapon.name}: ${weapon.attackBonus.ifEmpty { "—" }} | ${weapon.damage.ifEmpty { "—" }}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun CombatStatCard(label: String, value: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        ) {
            Text(label, style = MaterialTheme.typography.labelSmall)
            Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}

@PreviewLightDark
@Composable
private fun SheetPage1Preview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            SheetPage1(
                character = PreviewData.sampleCharacter,
                derivedStats = PreviewData.sampleDerivedStats,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SheetPage1CasterPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            SheetPage1(
                character = PreviewData.sampleCasterCharacter,
                derivedStats = PreviewData.sampleCasterDerivedStats,
            )
        }
    }
}
