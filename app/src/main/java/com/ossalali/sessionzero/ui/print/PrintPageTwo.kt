package com.ossalali.sessionzero.ui.print

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.ClassDefinition
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.model.SpeciesDefinition
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.domain.rules.SpeciesData
import com.ossalali.sessionzero.domain.rules.SpellSlotTables
import com.ossalali.sessionzero.ui.preview.PreviewData

private val labelColor = Color(0xFF555555)

@Composable
fun PrintPageTwo(
    character: Character,
    derivedStats: DerivedStats,
    classDef: ClassDefinition?,
    speciesDef: SpeciesDefinition?,
) {
    val isCaster = classDef?.spellcasting != null

    PrintTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
        ) {
            Column(modifier = Modifier.padding(all = 12.dp)) {
                if (isCaster) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(space = 6.dp),
                    ) {
                        // Left: Spells
                        Column(modifier = Modifier.weight(weight = 0.45f)) {
                            SpellcastingColumn(
                                character = character,
                                derivedStats = derivedStats,
                                classDef = classDef!!,
                            )
                        }
                        // Right: Features
                        Column(modifier = Modifier.weight(weight = 0.55f)) {
                            FeaturesColumn(
                                character = character,
                                classDef = classDef,
                                speciesDef = speciesDef,
                            )
                        }
                    }
                } else {
                    FeaturesColumn(
                        character = character,
                        classDef = classDef,
                        speciesDef = speciesDef,
                    )
                }
            }
        }
    }
}

// ── Spellcasting Column ──

@Composable
private fun SpellcastingColumn(
    character: Character,
    derivedStats: DerivedStats,
    classDef: ClassDefinition,
) {
    val spellcasting = classDef.spellcasting ?: return
    val abilityLabel = spellcasting.ability.name

    // Spellcasting header
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .printSectionBorder(),
    ) {
        SpellHeaderCell(label = "SPELLCASTING\nABILITY", value = abilityLabel, modifier = Modifier.weight(weight = 1f))
        SpellHeaderCell(label = "SPELL SAVE\nDC", value = "${derivedStats.spellSaveDC ?: "—"}", modifier = Modifier.weight(weight = 1f))
        SpellHeaderCell(label = "SPELL ATTACK\nBONUS", value = "+${derivedStats.spellAttackBonus ?: 0}", modifier = Modifier.weight(weight = 1f))
    }

    Spacer(modifier = Modifier.height(height = 4.dp))

    // Cantrips
    if (character.knownCantrips.isNotEmpty()) {
        SpellSection(title = "CANTRIPS", spells = character.knownCantrips)
        Spacer(modifier = Modifier.height(height = 3.dp))
    }

    // Spell slots by level
    val slots = SpellSlotTables.getSpellSlots(type = spellcasting.type, level = character.level)
    slots.forEachIndexed { index, slotCount ->
        if (slotCount > 0) {
            val levelSpells = character.preparedSpells // Simplified: show all prepared spells under available levels
            SpellLevelSection(
                level = index + 1,
                slotCount = slotCount,
                spells = if (index == 0) levelSpells else emptyList(),
            )
            Spacer(modifier = Modifier.height(height = 3.dp))
        }
    }

    // Show prepared spells if not already shown
    if (slots.isEmpty() && character.preparedSpells.isNotEmpty()) {
        SpellSection(title = "PREPARED SPELLS", spells = character.preparedSpells)
    }
}

@Composable
private fun SpellHeaderCell(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp, vertical = 3.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 4.sp),
            textAlign = TextAlign.Center,
            color = labelColor,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SpellSection(
    title: String,
    spells: List<String>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .printSectionBorder(),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp),
        )
        PrintDivider()
        Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)) {
            spells.forEach { spell ->
                Text(text = spell, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun SpellLevelSection(
    level: Int,
    slotCount: Int,
    spells: List<String>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .printSectionBorder(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) {
            Text(
                text = "LEVEL $level",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
            SpellSlotRow(slotCount = slotCount)
        }
        PrintDivider()
        Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)) {
            if (spells.isNotEmpty()) {
                spells.forEach { spell ->
                    Text(text = spell, style = MaterialTheme.typography.bodySmall)
                }
            } else {
                // Empty lines for writing
                repeat(3) {
                    Spacer(modifier = Modifier.height(height = 10.dp))
                    PrintDivider()
                }
            }
        }
    }
}

// ── Features Column ──

@Composable
private fun FeaturesColumn(
    character: Character,
    classDef: ClassDefinition?,
    speciesDef: SpeciesDefinition?,
) {
    // Class Features
    if (classDef != null) {
        val features = classDef.features.filter { it.key <= character.level }.flatMap { it.value }
        if (features.isNotEmpty()) {
            FeatureBox(title = "CLASS FEATURES") {
                features.forEach { feature ->
                    Text(
                        text = feature.name,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                    Text(
                        text = feature.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF444444),
                    )
                    Spacer(modifier = Modifier.height(height = 2.dp))
                }
            }
            Spacer(modifier = Modifier.height(height = 4.dp))
        }
    }

    // Species Traits
    if (speciesDef != null && speciesDef.traits.isNotEmpty()) {
        FeatureBox(title = "SPECIES TRAITS") {
            speciesDef.traits.forEach { trait ->
                Text(text = trait.name, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = trait.description, style = MaterialTheme.typography.bodySmall, color = Color(0xFF444444))
                Spacer(modifier = Modifier.height(height = 2.dp))
            }
        }
        Spacer(modifier = Modifier.height(height = 4.dp))
    }

    // Feats
    if (character.feats.isNotEmpty()) {
        FeatureBox(title = "FEATS") {
            character.originFeat?.let {
                Text(text = "$it (Origin)", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            }
            character.feats.forEach { feat ->
                Text(text = feat, style = MaterialTheme.typography.bodySmall)
            }
        }
        Spacer(modifier = Modifier.height(height = 4.dp))
    }

    // Personality
    val hasPersonality = character.personalityTraits.isNotEmpty() ||
            character.ideals.isNotEmpty() ||
            character.bonds.isNotEmpty() ||
            character.flaws.isNotEmpty()
    if (hasPersonality) {
        FeatureBox(title = "PERSONALITY") {
            PersonalityField(label = "PERSONALITY TRAITS", value = character.personalityTraits)
            PersonalityField(label = "IDEALS", value = character.ideals)
            PersonalityField(label = "BONDS", value = character.bonds)
            PersonalityField(label = "FLAWS", value = character.flaws)
        }
        Spacer(modifier = Modifier.height(height = 4.dp))
    }

    // Appearance
    val app = character.appearance
    val hasAppearance = listOf(app.age, app.height, app.weight, app.eyes, app.skin, app.hair).any { it.isNotEmpty() }
    if (hasAppearance) {
        FeatureBox(title = "APPEARANCE") {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(weight = 1f)) {
                    if (app.age.isNotEmpty()) AppearanceField(label = "Age", value = app.age)
                    if (app.height.isNotEmpty()) AppearanceField(label = "Height", value = app.height)
                    if (app.weight.isNotEmpty()) AppearanceField(label = "Weight", value = app.weight)
                }
                Column(modifier = Modifier.weight(weight = 1f)) {
                    if (app.eyes.isNotEmpty()) AppearanceField(label = "Eyes", value = app.eyes)
                    if (app.skin.isNotEmpty()) AppearanceField(label = "Skin", value = app.skin)
                    if (app.hair.isNotEmpty()) AppearanceField(label = "Hair", value = app.hair)
                }
            }
        }
        Spacer(modifier = Modifier.height(height = 4.dp))
    }

    // Backstory
    if (character.backstory.isNotEmpty()) {
        FeatureBox(title = "BACKSTORY") {
            Text(text = character.backstory, style = MaterialTheme.typography.bodySmall, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(height = 4.dp))
    }

    // Allies
    if (character.alliesAndOrganizations.isNotEmpty()) {
        FeatureBox(title = "ALLIES & ORGANIZATIONS") {
            Text(text = character.alliesAndOrganizations, style = MaterialTheme.typography.bodySmall, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(height = 4.dp))
    }

    // Notes
    if (character.additionalNotes.isNotEmpty()) {
        FeatureBox(title = "ADDITIONAL NOTES") {
            Text(text = character.additionalNotes, style = MaterialTheme.typography.bodySmall, color = Color.Black)
        }
    }
}

@Composable
private fun FeatureBox(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .printSectionBorder(),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp),
        )
        PrintDivider()
        Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 3.dp)) {
            content()
        }
    }
}

@Composable
private fun PersonalityField(label: String, value: String) {
    if (value.isEmpty()) return
    Column(modifier = Modifier.padding(bottom = 2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 4.sp),
            color = labelColor,
        )
        Text(text = value, style = MaterialTheme.typography.bodySmall, color = Color.Black)
    }
    PrintDivider()
}

@Composable
private fun AppearanceField(label: String, value: String) {
    Row {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodySmall,
            color = labelColor,
        )
        Text(text = value, style = MaterialTheme.typography.bodySmall, color = Color.Black)
    }
}

@Preview(widthDp = 595, heightDp = 842, name = "Page 2 - Caster")
@Composable
private fun PrintPageTwoCasterPreview() {
    val character = PreviewData.sampleCasterCharacter
    PrintPageTwo(
        character = character,
        derivedStats = PreviewData.sampleCasterDerivedStats,
        classDef = ClassData.ALL_CLASSES.find { it.name == character.className },
        speciesDef = SpeciesData.ALL_SPECIES.find { it.name == character.species },
    )
}

@Preview(widthDp = 595, heightDp = 842, name = "Page 2 - Non-Caster")
@Composable
private fun PrintPageTwoFighterPreview() {
    val character = PreviewData.sampleCharacter
    PrintPageTwo(
        character = character,
        derivedStats = PreviewData.sampleDerivedStats,
        classDef = ClassData.ALL_CLASSES.find { it.name == character.className },
        speciesDef = SpeciesData.ALL_SPECIES.find { it.name == character.species },
    )
}
