package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.rules.GameRules
import com.ossalali.sessionzero.ui.common.DuskSectionHeader
import com.ossalali.sessionzero.ui.common.MonoLabel
import com.ossalali.sessionzero.ui.common.SelectableCard
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun ReviewStep(
    character: Character,
    derivedStats: DerivedStats,
    onEditStep: (Int) -> Unit = {},
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        // Hero gradient card
        HeroCard(character = character)

        Spacer(modifier = Modifier.height(height = 12.dp))

        // 4-cell stat grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 6.dp),
        ) {
            StatCell(modifier = Modifier.weight(weight = 1f), label = "AC", value = "${derivedStats.armorClass}")
            StatCell(modifier = Modifier.weight(weight = 1f), label = "HP", value = "${derivedStats.maxHP}")
            StatCell(modifier = Modifier.weight(weight = 1f), label = "Init", value = formatSigned(value = derivedStats.initiative))
            StatCell(modifier = Modifier.weight(weight = 1f), label = "Speed", value = "${derivedStats.speed}")
        }

        Spacer(modifier = Modifier.height(height = 12.dp))

        // 6-cell abilities grid
        val scores = character.totalAbilityScores
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
        ) {
            AbilityName.entries.forEach { ability ->
                val score = scores[ability]
                AbilityCell(
                    modifier = Modifier.weight(weight = 1f),
                    label = ability.name,
                    score = score,
                )
            }
        }

        Spacer(modifier = Modifier.height(height = 16.dp))
        DuskSectionHeader(title = "Summary", kicker = "Session Zero")

        ReviewRow(
            label = "Class",
            value = listOfNotNull(
                character.className?.displayName,
                character.subclass?.let { "($it)" },
            ).joinToString(separator = " "),
            stepIndex = 0,
            onEditStep = onEditStep,
        )
        ReviewRow(
            label = "Species",
            value = listOfNotNull(
                character.species?.displayName,
                character.speciesLineage?.let { "— $it" },
            ).joinToString(separator = " "),
            stepIndex = 1,
            onEditStep = onEditStep,
        )
        ReviewRow(
            label = "Background",
            value = character.background?.displayName ?: "—",
            stepIndex = 2,
            onEditStep = onEditStep,
        )
        ReviewRow(
            label = "Skills",
            value = character.skillProficiencies.joinToString(separator = ", ") { it.displayName }.ifEmpty { "—" },
            stepIndex = 4,
            onEditStep = onEditStep,
        )
        ReviewRow(
            label = "Equipment",
            value = "${character.equipment.size} items · ${character.coins.gp}gp",
            stepIndex = 5,
            onEditStep = onEditStep,
        )
        ReviewRow(
            label = "Weapons",
            value = character.weapons.joinToString(separator = ", ") { it.name }.ifEmpty { "—" },
            stepIndex = 6,
            onEditStep = onEditStep,
        )
        ReviewRow(
            label = "Details",
            value = character.name.ifEmpty { "Unnamed" },
            stepIndex = 7,
            onEditStep = onEditStep,
        )

        Spacer(modifier = Modifier.height(height = 24.dp))
    }
}

@Composable
private fun HeroCard(character: Character) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(tokens.accentSoft, scheme.surface),
                ),
            )
            .border(
                width = 1.dp,
                color = scheme.primary.copy(alpha = 0.26f),
                shape = RoundedCornerShape(size = 16.dp),
            )
            .padding(all = 16.dp),
    ) {
        Column {
            MonoLabel(
                text = "Lv ${character.level} ${character.className?.displayName ?: "Adventurer"}",
                color = scheme.primary,
            )
            Spacer(modifier = Modifier.height(height = 2.dp))
            Text(
                text = character.name.ifEmpty { "Unnamed" },
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(height = 4.dp))
            val subtitle = listOfNotNull(
                character.species?.displayName,
                character.background?.displayName,
                character.alignment?.displayName,
            ).joinToString(separator = " · ").ifEmpty { "Ready for adventure" }
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = tokens.textDim,
            )
        }
    }
}

@Composable
private fun StatCell(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
) {
    val scheme = MaterialTheme.colorScheme
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .background(color = scheme.surface)
            .border(
                width = 1.dp,
                color = scheme.outline,
                shape = RoundedCornerShape(size = 10.dp),
            )
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MonoLabel(text = label)
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun AbilityCell(
    modifier: Modifier = Modifier,
    label: String,
    score: Int,
) {
    val scheme = MaterialTheme.colorScheme
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(color = scheme.surface)
            .border(
                width = 1.dp,
                color = scheme.outline,
                shape = RoundedCornerShape(size = 8.dp),
            )
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MonoLabel(text = label)
        Text(
            text = "$score",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = formatSigned(value = GameRules.abilityModifier(score = score)),
            style = MaterialTheme.typography.labelSmall,
            color = scheme.primary,
        )
    }
}

@Composable
private fun ReviewRow(
    label: String,
    value: String,
    stepIndex: Int,
    onEditStep: (Int) -> Unit,
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme

    SelectableCard(
        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
        cornerRadius = 10,
        contentPadding = 12,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(weight = 1f)) {
                MonoLabel(text = label)
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                )
            }
            Box(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .border(
                        width = 1.dp,
                        color = scheme.outline,
                        shape = CircleShape,
                    )
                    .clickable { onEditStep(stepIndex) }
                    .padding(horizontal = 10.dp, vertical = 5.dp),
            ) {
                Text(
                    text = "Edit",
                    style = MaterialTheme.typography.labelSmall,
                    color = scheme.onSurface,
                )
            }
        }
    }
}

private fun formatSigned(value: Int): String = if (value >= 0) "+$value" else "$value"

@PreviewLightDark
@Composable
private fun ReviewStepPreview() {
    SessionZeroTheme(dynamicColor = false) {
        ReviewStep(
            character = PreviewData.sampleCharacter,
            derivedStats = PreviewData.sampleDerivedStats,
        )
    }
}
