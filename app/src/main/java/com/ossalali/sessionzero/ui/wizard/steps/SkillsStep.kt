package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.AbilityScores
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.SkillName
import com.ossalali.sessionzero.domain.rules.BackgroundData
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.ui.common.DuskSectionHeader
import com.ossalali.sessionzero.ui.common.MonoLabel
import com.ossalali.sessionzero.ui.common.SelectableCard
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun SkillsStep(
    character: Character,
    onToggleSkill: (SkillName) -> Unit = {},
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val classDef = character.className?.let { ClassData.ALL_CLASSES.find { c -> c.name == it } }
    val bgDef = character.background?.let { BackgroundData.ALL_BACKGROUNDS.find { b -> b.name == it } }
    val backgroundSkills = bgDef?.skillProficiencies ?: emptyList()
    val classSkillChoices = classDef?.skillChoices ?: emptyList()
    val maxClassSkills = classDef?.numSkillChoices ?: 2

    val classSelectedCount = character.skillProficiencies.count {
        it !in backgroundSkills && it in classSkillChoices
    }
    val totalCount = classSelectedCount + backgroundSkills.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        DuskSectionHeader(
            title = "Skill proficiencies",
            kicker = "$totalCount / ${maxClassSkills + backgroundSkills.size} chosen",
        )

        Text(
            text = buildString {
                val className = classDef?.name?.displayName ?: "Your class"
                append("$className grants $maxClassSkills")
                val bgName = bgDef?.name?.displayName
                if (bgName != null) append(" · $bgName grants ${backgroundSkills.size}")
            },
            style = MaterialTheme.typography.bodySmall,
            color = tokens.textDim,
        )
        Spacer(modifier = Modifier.height(height = 10.dp))

        // 2-col grid of skill rows
        AbilityScores.SKILLS_BY_ABILITY.forEach { (ability, skills) ->
            val abilityLabel = AbilityScores.ABILITY_LABELS[ability] ?: ability.name
            MonoLabel(text = abilityLabel, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))

            val rows = skills.chunked(size = 2)
            rows.forEach { pair ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(space = 6.dp),
                ) {
                    pair.forEach { skill ->
                        val isBackgroundSkill = skill in backgroundSkills
                        val isSelected = skill in character.skillProficiencies || isBackgroundSkill
                        val isClassChoice = skill in classSkillChoices
                        val canToggle = !isBackgroundSkill && isClassChoice &&
                                (isSelected || classSelectedCount < maxClassSkills)

                        SkillCell(
                            modifier = Modifier.weight(weight = 1f),
                            label = skill.displayName,
                            abbr = ability.name,
                            selected = isSelected,
                            disabled = !canToggle && !isBackgroundSkill,
                            locked = isBackgroundSkill,
                            onClick = { if (canToggle) onToggleSkill(skill) },
                        )
                    }
                    if (pair.size == 1) Spacer(modifier = Modifier.weight(weight = 1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(height = 24.dp))
    }
}

@Composable
private fun SkillCell(
    modifier: Modifier = Modifier,
    label: String,
    abbr: String,
    selected: Boolean,
    disabled: Boolean,
    locked: Boolean,
    onClick: () -> Unit,
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val alpha = if (disabled) 0.4f else 1f

    SelectableCard(
        modifier = modifier,
        selected = selected,
        onClick = if (disabled) null else onClick,
        cornerRadius = 10,
        contentPadding = 8,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val borderColor = if (selected) scheme.primary else tokens.lineHi
            Box(
                modifier = Modifier
                    .size(size = 16.dp)
                    .clip(shape = RoundedCornerShape(size = 4.dp))
                    .background(color = if (selected) scheme.primary else Color.Transparent)
                    .border(
                        width = 1.5.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(size = 4.dp),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                if (selected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = scheme.onPrimary,
                        modifier = Modifier.size(size = 10.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.width(width = 8.dp))
            Column(modifier = Modifier.weight(weight = 1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = scheme.onSurface.copy(alpha = alpha),
                )
                MonoLabel(text = if (locked) "$abbr · LOCKED" else abbr)
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SkillsStepPreview() {
    SessionZeroTheme(dynamicColor = false) {
        SkillsStep(character = PreviewData.sampleCharacter)
    }
}
