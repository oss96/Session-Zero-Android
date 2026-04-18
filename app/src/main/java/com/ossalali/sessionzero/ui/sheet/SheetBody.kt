package com.ossalali.sessionzero.ui.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.sheet.sections.AbilityScoresSection
import com.ossalali.sessionzero.ui.sheet.sections.AppearanceSection
import com.ossalali.sessionzero.ui.sheet.sections.CombatSection
import com.ossalali.sessionzero.ui.sheet.sections.EquipmentSection
import com.ossalali.sessionzero.ui.sheet.sections.FeaturesSection
import com.ossalali.sessionzero.ui.sheet.sections.HeaderSection
import com.ossalali.sessionzero.ui.sheet.sections.PersonalitySection
import com.ossalali.sessionzero.ui.sheet.sections.SavingThrowsSection
import com.ossalali.sessionzero.ui.sheet.sections.SkillsSection
import com.ossalali.sessionzero.ui.sheet.sections.SpellcastingSection
import com.ossalali.sessionzero.ui.sheet.sections.WeaponsSection
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun SheetBody(
    character: Character,
    derivedStats: DerivedStats,
) {
    val classDef = character.className?.let {
        ClassData.ALL_CLASSES.find { c -> c.name == it }
    }

    HeaderSection(character = character)

    Spacer(modifier = Modifier.height(height = 16.dp))
    HorizontalDivider()
    Spacer(modifier = Modifier.height(height = 16.dp))

    AbilityScoresSection(character = character)

    Spacer(modifier = Modifier.height(height = 16.dp))

    Row(
        horizontalArrangement = Arrangement.Center,
    ) {
        SkillsSection(character = character, derivedStats = derivedStats)
        Spacer(modifier = Modifier.weight(1f))
        SavingThrowsSection(derivedStats = derivedStats, classDef = classDef)
    }

    Spacer(modifier = Modifier.height(height = 16.dp))

    CombatSection(derivedStats = derivedStats)

    if (character.weapons.isNotEmpty()) {
        Spacer(modifier = Modifier.height(height = 16.dp))
        WeaponsSection(character = character)
    }

    Spacer(modifier = Modifier.height(height = 16.dp))
    HorizontalDivider()
    Spacer(modifier = Modifier.height(height = 16.dp))

    val hasPersonality = character.personalityTraits.isNotEmpty() ||
            character.ideals.isNotEmpty() ||
            character.bonds.isNotEmpty() ||
            character.flaws.isNotEmpty()
    if (hasPersonality) {
        PersonalitySection(character = character)
        Spacer(modifier = Modifier.height(height = 16.dp))
    }

    if (character.backstory.isNotEmpty()) {
        SectionHeader(text = "Backstory")
        Text(text = character.backstory)
        Spacer(modifier = Modifier.height(height = 16.dp))
    }

    EquipmentSection(character = character)
    Spacer(modifier = Modifier.height(height = 16.dp))

    if (classDef?.spellcasting != null) {
        SpellcastingSection(
            character = character,
            derivedStats = derivedStats,
            classDef = classDef,
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

    if (character.feats.isNotEmpty()) {
        SectionHeader(text = "Feats")
        character.feats.forEach { Text(text = "- $it") }
        Spacer(modifier = Modifier.height(height = 16.dp))
    }

    if (character.languages.isNotEmpty()) {
        SectionHeader(text = "Languages")
        Text(text = character.languages.joinToString(separator = ", "))
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
        SectionHeader(text = "Allies & Organizations")
        Text(text = character.alliesAndOrganizations)
        Spacer(modifier = Modifier.height(height = 16.dp))
    }

    if (character.additionalNotes.isNotEmpty()) {
        SectionHeader(text = "Additional Notes")
        Text(text = character.additionalNotes)
        Spacer(modifier = Modifier.height(height = 16.dp))
    }
}

@PreviewLightDark
@Composable
private fun SheetBodyPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 16.dp),
        ) {
            SheetBody(
                character = PreviewData.sampleCharacter,
                derivedStats = PreviewData.sampleDerivedStats,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SheetBodyCasterPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 16.dp),
        ) {
            SheetBody(
                character = PreviewData.sampleCasterCharacter,
                derivedStats = PreviewData.sampleCasterDerivedStats,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SheetBodyScrolledPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState(initial = 2200))
                .padding(all = 16.dp),
        ) {
            SheetBody(
                character = PreviewData.sampleCharacter,
                derivedStats = PreviewData.sampleDerivedStats,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SheetBodyCasterScrolled2Preview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState(initial = 2200))
                .padding(all = 16.dp),
        ) {
            SheetBody(
                character = PreviewData.sampleCasterCharacter,
                derivedStats = PreviewData.sampleCasterDerivedStats,
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun SheetBodyCasterScrolledMaxPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState(initial = 4400))
                .padding(all = 16.dp),
        ) {
            SheetBody(
                character = PreviewData.sampleCasterCharacter,
                derivedStats = PreviewData.sampleCasterDerivedStats,
            )
        }
    }
}
