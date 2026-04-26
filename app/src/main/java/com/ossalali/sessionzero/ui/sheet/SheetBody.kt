package com.ossalali.sessionzero.ui.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.ui.common.DuskSectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.sheet.sections.AbilityScoresSection
import com.ossalali.sessionzero.ui.sheet.sections.AppearanceSection
import com.ossalali.sessionzero.ui.sheet.sections.CombatSection
import com.ossalali.sessionzero.ui.sheet.sections.EquipmentSection
import com.ossalali.sessionzero.ui.sheet.sections.FeaturesSection
import com.ossalali.sessionzero.ui.sheet.sections.PersonalitySection
import com.ossalali.sessionzero.ui.sheet.sections.SavingThrowsSection
import com.ossalali.sessionzero.ui.sheet.sections.SkillsSection
import com.ossalali.sessionzero.ui.sheet.sections.SpellcastingSection
import com.ossalali.sessionzero.ui.sheet.sections.WeaponsSection
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

/**
 * Play mode — combat-glanceable. Stats up front, attacks + saves/skills
 * for quick reference. Reuses the existing section composables so the
 * mode toggle is purely additive.
 */
@Composable
fun SheetPlayBody(
    character: Character,
    derivedStats: DerivedStats,
) {
    val classDef = character.className?.let {
        ClassData.ALL_CLASSES.find { c -> c.name == it }
    }

    AbilityScoresSection(character = character)
    Spacer(modifier = Modifier.height(height = 16.dp))

    CombatSection(derivedStats = derivedStats)
    Spacer(modifier = Modifier.height(height = 16.dp))

    if (character.weapons.isNotEmpty()) {
        WeaponsSection(character = character)
        Spacer(modifier = Modifier.height(height = 16.dp))
    }

    Row(horizontalArrangement = Arrangement.Center) {
        SkillsSection(character = character, derivedStats = derivedStats)
        Spacer(modifier = Modifier.weight(weight = 1f))
        SavingThrowsSection(derivedStats = derivedStats, classDef = classDef)
    }
    Spacer(modifier = Modifier.height(height = 16.dp))

    val subclassDef = character.subclass?.let { name ->
        classDef?.subclasses?.find { it.name == name }
    }
    val effectiveSpellcasting = classDef?.spellcasting ?: subclassDef?.spellcasting
    if (effectiveSpellcasting != null) {
        SpellcastingSection(
            character = character,
            derivedStats = derivedStats,
            spellcasting = effectiveSpellcasting,
        )
        Spacer(modifier = Modifier.height(height = 16.dp))
    }

    if (classDef != null) {
        FeaturesSection(
            classDef = classDef,
            level = character.level,
            subclassName = character.subclass,
        )
        Spacer(modifier = Modifier.height(height = 16.dp))
    }
}

/**
 * Showcase mode — narrative, keepsake. Personality, backstory, equipment,
 * languages as a nicely-typeset character portrait. No combat stats.
 */
@Composable
fun SheetShowcaseBody(
    character: Character,
    @Suppress("UNUSED_PARAMETER") derivedStats: DerivedStats,
) {
    val hasPersonality = character.personalityTraits.isNotEmpty() ||
            character.ideals.isNotEmpty() ||
            character.bonds.isNotEmpty() ||
            character.flaws.isNotEmpty()

    if (character.backstory.isNotEmpty()) {
        ShowcaseTaleCard(text = character.backstory)
        Spacer(modifier = Modifier.height(height = 16.dp))
    }

    if (hasPersonality) {
        DuskSectionHeader(title = "Personality", kicker = "Who I am")
        ShowcasePersonalityRows(character = character)
        Spacer(modifier = Modifier.height(height = 16.dp))
    }

    if (character.equipment.isNotEmpty()) {
        DuskSectionHeader(title = "Equipment", kicker = "What I carry")
        ShowcaseEquipment(character = character)
        Spacer(modifier = Modifier.height(height = 16.dp))
    }

    if (character.languages.isNotEmpty()) {
        DuskSectionHeader(title = "Languages", kicker = "Tongues")
        ShowcaseLanguages(languages = character.languages)
        Spacer(modifier = Modifier.height(height = 16.dp))
    }

    val hasAppearance = listOf(
        character.appearance.age, character.appearance.height, character.appearance.weight,
        character.appearance.eyes, character.appearance.skin, character.appearance.hair,
    ).any { it.isNotEmpty() }
    if (hasAppearance) {
        AppearanceSection(appearance = character.appearance)
        Spacer(modifier = Modifier.height(height = 16.dp))
    }

    if (character.alliesAndOrganizations.isNotEmpty()) {
        DuskSectionHeader(title = "Allies", kicker = "Who I know")
        Text(text = character.alliesAndOrganizations)
        Spacer(modifier = Modifier.height(height = 16.dp))
    }

    if (character.additionalNotes.isNotEmpty()) {
        DuskSectionHeader(title = "Notes", kicker = "Other")
        Text(text = character.additionalNotes)
        Spacer(modifier = Modifier.height(height = 16.dp))
    }

    if (!hasPersonality && character.backstory.isEmpty() && character.equipment.isEmpty() &&
        character.languages.isEmpty() && !hasAppearance &&
        character.alliesAndOrganizations.isEmpty() && character.additionalNotes.isEmpty()
    ) {
        // Fallback content for characters with no narrative data
        PersonalitySection(character = character)
        Spacer(modifier = Modifier.height(height = 16.dp))
        EquipmentSection(character = character)
    }
}

/**
 * Kept for backwards compatibility with any existing previews; delegates
 * to [SheetPlayBody] (the previous default layout).
 */
@Composable
fun SheetBody(
    character: Character,
    derivedStats: DerivedStats,
) {
    SheetPlayBody(character = character, derivedStats = derivedStats)
    Spacer(modifier = Modifier.height(height = 16.dp))
    HorizontalDivider()
    Spacer(modifier = Modifier.height(height = 16.dp))
    SheetShowcaseBody(character = character, derivedStats = derivedStats)
}

@PreviewLightDark
@Composable
private fun SheetBodyPlayPreview() {
    SessionZeroTheme(dynamicColor = false) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 14.dp),
        ) {
            SheetPlayBody(
                character = PreviewData.sampleCharacter,
                derivedStats = PreviewData.sampleDerivedStats,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SheetBodyShowcasePreview() {
    SessionZeroTheme(dynamicColor = false) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 14.dp),
        ) {
            SheetShowcaseBody(
                character = PreviewData.sampleCharacter,
                derivedStats = PreviewData.sampleDerivedStats,
            )
        }
    }
}
