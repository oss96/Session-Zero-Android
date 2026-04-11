package com.ossalali.sessionzero.ui.sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.domain.rules.SpellSlotTables
import com.ossalali.sessionzero.ui.common.SectionHeader

@Composable
fun SheetPage2(
    character: Character,
    derivedStats: DerivedStats,
) {
    Spacer(Modifier.height(16.dp))
    HorizontalDivider()
    Spacer(Modifier.height(16.dp))

    // Personality
    if (character.personalityTraits.isNotEmpty() || character.ideals.isNotEmpty() ||
        character.bonds.isNotEmpty() || character.flaws.isNotEmpty()
    ) {
        SectionHeader("Personality")
        if (character.personalityTraits.isNotEmpty()) {
            Text("Traits: ${character.personalityTraits}", style = MaterialTheme.typography.bodyMedium)
        }
        if (character.ideals.isNotEmpty()) {
            Text("Ideals: ${character.ideals}", style = MaterialTheme.typography.bodyMedium)
        }
        if (character.bonds.isNotEmpty()) {
            Text("Bonds: ${character.bonds}", style = MaterialTheme.typography.bodyMedium)
        }
        if (character.flaws.isNotEmpty()) {
            Text("Flaws: ${character.flaws}", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(Modifier.height(16.dp))
    }

    // Backstory
    if (character.backstory.isNotEmpty()) {
        SectionHeader("Backstory")
        Text(character.backstory, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(16.dp))
    }

    // Equipment
    if (character.equipment.isNotEmpty()) {
        SectionHeader("Equipment")
        character.equipment.forEach { item ->
            Text(
                "- ${item.name}${if (item.quantity > 1) " x${item.quantity}" else ""}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(Modifier.height(8.dp))
    }

    // Coins
    val coins = character.coins
    if (coins.cp > 0 || coins.sp > 0 || coins.gp > 0 || coins.pp > 0) {
        Text(
            "Coins: ${coins.cp} CP, ${coins.sp} SP, ${coins.gp} GP, ${coins.pp} PP",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(16.dp))
    }

    // Spellcasting
    val classDef = character.className?.let {
        ClassData.ALL_CLASSES.find { c -> c.name == it }
    }
    if (classDef?.spellcasting != null) {
        SectionHeader("Spellcasting")
        derivedStats.spellSaveDC?.let {
            Text("Spell Save DC: $it", style = MaterialTheme.typography.bodyMedium)
        }
        derivedStats.spellAttackBonus?.let {
            Text("Spell Attack Bonus: +$it", style = MaterialTheme.typography.bodyMedium)
        }

        if (character.knownCantrips.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text("Cantrips:", style = MaterialTheme.typography.titleSmall)
            character.knownCantrips.forEach { Text("- $it") }
        }

        if (character.preparedSpells.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text("Prepared Spells:", style = MaterialTheme.typography.titleSmall)
            character.preparedSpells.forEach { Text("- $it") }
        }

        // Spell slots
        val spellcasting = classDef.spellcasting
        val slots = SpellSlotTables.getSpellSlots(spellcasting.type, character.level)
        if (slots.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text("Spell Slots:", style = MaterialTheme.typography.titleSmall)
            slots.forEachIndexed { index, count ->
                if (count > 0) {
                    Text("  Level ${index + 1}: $count slots")
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }

    // Features & Traits
    if (classDef != null) {
        val features = classDef.features.filter { it.key <= character.level }
            .flatMap { it.value }
        if (features.isNotEmpty()) {
            SectionHeader("Features & Traits")
            features.forEach { feature ->
                Text(feature.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Text(feature.description, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(4.dp))
            }
            Spacer(Modifier.height(16.dp))
        }
    }

    // Feats
    if (character.feats.isNotEmpty()) {
        SectionHeader("Feats")
        character.feats.forEach { Text("- $it") }
        Spacer(Modifier.height(16.dp))
    }

    // Languages
    if (character.languages.isNotEmpty()) {
        SectionHeader("Languages")
        Text(character.languages.joinToString(", "))
        Spacer(Modifier.height(16.dp))
    }

    // Appearance
    val appearance = character.appearance
    val hasAppearance = listOf(
        appearance.age, appearance.height, appearance.weight,
        appearance.eyes, appearance.skin, appearance.hair,
    ).any { it.isNotEmpty() }
    if (hasAppearance) {
        SectionHeader("Appearance")
        if (appearance.age.isNotEmpty()) Text("Age: ${appearance.age}")
        if (appearance.height.isNotEmpty()) Text("Height: ${appearance.height}")
        if (appearance.weight.isNotEmpty()) Text("Weight: ${appearance.weight}")
        if (appearance.eyes.isNotEmpty()) Text("Eyes: ${appearance.eyes}")
        if (appearance.skin.isNotEmpty()) Text("Skin: ${appearance.skin}")
        if (appearance.hair.isNotEmpty()) Text("Hair: ${appearance.hair}")
        if (appearance.distinguishingMarks.isNotEmpty()) Text("Marks: ${appearance.distinguishingMarks}")
        Spacer(Modifier.height(16.dp))
    }

    // Allies
    if (character.alliesAndOrganizations.isNotEmpty()) {
        SectionHeader("Allies & Organizations")
        Text(character.alliesAndOrganizations)
        Spacer(Modifier.height(16.dp))
    }

    // Notes
    if (character.additionalNotes.isNotEmpty()) {
        SectionHeader("Additional Notes")
        Text(character.additionalNotes)
        Spacer(Modifier.height(16.dp))
    }
}
