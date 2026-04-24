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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.model.SpellcastingProfile
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.domain.rules.SpellSlotTables
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun SpellcastingSection(
    character: Character,
    derivedStats: DerivedStats,
    spellcasting: SpellcastingProfile,
) {

    SectionHeader(text = "Spellcasting")
    derivedStats.spellSaveDC?.let {
        Text(text = "Spell Save DC: $it", style = MaterialTheme.typography.bodyMedium)
    }
    derivedStats.spellAttackBonus?.let {
        Text(text = "Spell Attack Bonus: +$it", style = MaterialTheme.typography.bodyMedium)
    }

    if (character.knownCantrips.isNotEmpty()) {
        Spacer(modifier = Modifier.height(height = 8.dp))
        Text(text = "Cantrips:", style = MaterialTheme.typography.titleSmall)
        character.knownCantrips.forEach { Text(text = "- $it") }
    }

    if (character.preparedSpells.isNotEmpty()) {
        Spacer(modifier = Modifier.height(height = 8.dp))
        Text(text = "Prepared Spells:", style = MaterialTheme.typography.titleSmall)
        character.preparedSpells.forEach { Text(text = "- $it") }
    }

    val slots = SpellSlotTables.getSpellSlots(type = spellcasting.type, level = character.level)
    if (slots.isNotEmpty()) {
        Spacer(modifier = Modifier.height(height = 8.dp))
        Text(text = "Spell Slots:", style = MaterialTheme.typography.titleSmall)
        slots.forEachIndexed { index, count ->
            if (count > 0) {
                Text(text = "  Level ${index + 1}: $count slots")
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SpellcastingSectionPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 16.dp),
        ) {
            val character = PreviewData.sampleCasterCharacter
            val classDef = ClassData.ALL_CLASSES.find { it.name == character.className }!!
            SpellcastingSection(
                character = character,
                derivedStats = PreviewData.sampleCasterDerivedStats,
                spellcasting = classDef.spellcasting!!,
            )
        }
    }
}
