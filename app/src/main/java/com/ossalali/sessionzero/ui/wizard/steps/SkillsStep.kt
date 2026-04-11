package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.AbilityScores
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.SkillName
import com.ossalali.sessionzero.domain.rules.BackgroundData
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun SkillsStep(
    character: Character,
    onToggleSkill: (SkillName) -> Unit = {},
) {
    val classDef = character.className?.let { ClassData.ALL_CLASSES.find { c -> c.name == it } }
    val bgDef = character.background?.let { BackgroundData.ALL_BACKGROUNDS.find { b -> b.name == it } }
    val backgroundSkills = bgDef?.skillProficiencies ?: emptyList()
    val classSkillChoices = classDef?.skillChoices ?: emptyList()
    val maxClassSkills = classDef?.numSkillChoices ?: 2

    val classSelectedCount = character.skillProficiencies.count {
        it !in backgroundSkills && it in classSkillChoices
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        SectionHeader(text = "Skill Proficiencies")

        if (backgroundSkills.isNotEmpty()) {
            Text(
                text = "Background skills (locked): ${backgroundSkills.joinToString(", ") { it.displayName }}",
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(height = 4.dp))
        }

        Text(
            text = "Choose $maxClassSkills class skills ($classSelectedCount selected)",
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(height = 12.dp))

        AbilityScores.SKILLS_BY_ABILITY.forEach { (ability, skills) ->
            val abilityLabel = AbilityScores.ABILITY_LABELS[ability] ?: ability.name

            Text(
                text = abilityLabel,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp),
            )

            skills.forEach { skill ->
                val isBackgroundSkill = skill in backgroundSkills
                val isSelected = skill in character.skillProficiencies || isBackgroundSkill
                val isClassChoice = skill in classSkillChoices
                val canToggle = !isBackgroundSkill && isClassChoice &&
                        (isSelected || classSelectedCount < maxClassSkills)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = {
                            if (canToggle) onToggleSkill(skill)
                        },
                        enabled = canToggle,
                    )
                    Text(
                        text = skill.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isBackgroundSkill) {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        } else if (!isClassChoice) {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                    )
                    if (isBackgroundSkill) {
                        Text(
                            text = " (background)",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SkillsStepPreview() {
    SessionZeroTheme {
        SkillsStep(character = PreviewData.sampleCharacter)
    }
}
