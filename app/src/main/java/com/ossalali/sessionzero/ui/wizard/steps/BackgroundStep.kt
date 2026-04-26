package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.AbilityScores
import com.ossalali.sessionzero.domain.model.BackgroundName
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.rules.BackgroundData
import com.ossalali.sessionzero.ui.common.DuskSectionHeader
import com.ossalali.sessionzero.ui.common.DuskSegmentedControl
import com.ossalali.sessionzero.ui.common.DuskTag
import com.ossalali.sessionzero.ui.common.DuskTagVariant
import com.ossalali.sessionzero.ui.common.MonoLabel
import com.ossalali.sessionzero.ui.common.SelectableCard
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackgroundStep(
    character: Character,
    onBackgroundSelected: (BackgroundName) -> Unit = {},
    onAbilityBonusesChanged: (Map<AbilityName, Int>) -> Unit = {},
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val bgDef = character.background?.let { name -> BackgroundData.ALL_BACKGROUNDS.find { it.name == name } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        DuskSectionHeader(title = "Choose a background", kicker = "Origin story")

        var userExpandedGrid by remember { mutableStateOf(value = false) }
        val showCollapsed = bgDef != null && !userExpandedGrid

        AnimatedContent(
            targetState = showCollapsed,
            transitionSpec = {
                (fadeIn() + slideInVertically(initialOffsetY = { it / 6 }))
                    .togetherWith(fadeOut() + slideOutVertically(targetOffsetY = { it / 6 }))
            },
            label = "backgroundGridCollapse",
        ) { collapsed ->
            if (collapsed && bgDef != null) {
                // Collapsed summary card with Change button
                SelectableCard(contentPadding = 14) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(size = 40.dp)
                                .clip(shape = RoundedCornerShape(size = 10.dp))
                                .background(color = tokens.accentSoft),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = null,
                                tint = scheme.primary,
                                modifier = Modifier.size(size = 20.dp),
                            )
                        }
                        Spacer(modifier = Modifier.width(width = 10.dp))
                        Column(modifier = Modifier.weight(weight = 1f)) {
                            Text(
                                text = bgDef.name.displayName,
                                style = MaterialTheme.typography.titleLarge,
                            )
                            Text(
                                text = bgDef.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = tokens.textDim,
                                maxLines = 2,
                            )
                        }
                        TextButton(onClick = { userExpandedGrid = true }) {
                            Text(text = "Change")
                        }
                    }
                }
            } else {
                // Expanded single-column background list
                Column {
                    BackgroundData.ALL_BACKGROUNDS.forEach { bg ->
                        val isActive = bg.name == character.background
                        SelectableCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 6.dp),
                            selected = isActive,
                            onClick = {
                                onBackgroundSelected(bg.name)
                                userExpandedGrid = false
                            },
                            cornerRadius = 12,
                            contentPadding = 10,
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(size = 32.dp)
                                        .clip(shape = RoundedCornerShape(size = 8.dp))
                                        .background(color = tokens.surface2),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                        contentDescription = null,
                                        tint = if (isActive) scheme.primary else tokens.textDim,
                                        modifier = Modifier.size(size = 16.dp),
                                    )
                                }
                                Spacer(modifier = Modifier.width(width = 10.dp))
                                Column(modifier = Modifier.weight(weight = 1f)) {
                                    Text(
                                        text = bg.name.displayName,
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                    Text(
                                        text = bg.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = tokens.textDim,
                                        maxLines = 2,
                                    )
                                }
                                if (isActive) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = scheme.primary,
                                        modifier = Modifier.size(size = 16.dp),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (bgDef != null) {
            Spacer(modifier = Modifier.height(height = 10.dp))

            SelectableCard(contentPadding = 12) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    MonoLabel(text = "${bgDef.name.displayName} grants")
                    Spacer(modifier = Modifier.height(height = 8.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(space = 6.dp)) {
                        bgDef.skillProficiencies.forEach { skill ->
                            DuskTag(
                                text = skill.displayName,
                                variant = DuskTagVariant.Default,
                                modifier = Modifier.padding(bottom = 6.dp),
                            )
                        }
                        bgDef.toolProficiency?.let { tool ->
                            DuskTag(
                                text = tool,
                                variant = DuskTagVariant.Default,
                                modifier = Modifier.padding(bottom = 6.dp),
                            )
                        }
                        DuskTag(
                            text = bgDef.originFeat,
                            variant = DuskTagVariant.Gold,
                            modifier = Modifier.padding(bottom = 6.dp),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(height = 14.dp))
            DuskSectionHeader(title = "Ability score bonuses", kicker = "Boost")
            Text(
                text = "Choose +2/+1 to two abilities, or +1/+1/+1 to three abilities",
                style = MaterialTheme.typography.bodySmall,
                color = tokens.textDim,
            )
            Spacer(modifier = Modifier.height(height = 10.dp))

            var bonusMode by remember { mutableIntStateOf(value = 0) }
            DuskSegmentedControl(
                options = listOf("+2/+1", "+1/+1/+1"),
                selectedIndex = bonusMode,
                onSelect = {
                    bonusMode = it
                    onAbilityBonusesChanged(emptyMap())
                },
            )

            Spacer(modifier = Modifier.height(height = 10.dp))

            if (bonusMode == 0) {
                TwoAbilityPicker(
                    currentBonuses = character.abilityScoreBonuses,
                    onBonusesChanged = onAbilityBonusesChanged,
                )
            } else {
                ThreeAbilityPicker(
                    currentBonuses = character.abilityScoreBonuses,
                    onBonusesChanged = onAbilityBonusesChanged,
                )
            }
        }

        Spacer(modifier = Modifier.height(height = 24.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TwoAbilityPicker(
    currentBonuses: Map<AbilityName, Int>,
    onBonusesChanged: (Map<AbilityName, Int>) -> Unit,
) {
    val abilities = AbilityScores.ABILITY_LABELS.entries.toList()
    val plus2 = currentBonuses.entries.firstOrNull { it.value == 2 }?.key
    val plus1 = currentBonuses.entries.firstOrNull { it.value == 1 }?.key

    MonoLabel(text = "+2 Ability")
    Spacer(modifier = Modifier.height(height = 4.dp))
    var expanded2 by remember { mutableStateOf(value = false) }
    ExposedDropdownMenuBox(expanded = expanded2, onExpandedChange = { expanded2 = it }) {
        OutlinedTextField(
            value = plus2?.let { AbilityScores.ABILITY_LABELS[it] } ?: "Select…",
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded2) },
            shape = RoundedCornerShape(size = 10.dp),
            modifier = Modifier
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
        )
        ExposedDropdownMenu(expanded = expanded2, onDismissRequest = { expanded2 = false }) {
            abilities.forEach { (ability, label) ->
                DropdownMenuItem(
                    text = { Text(text = label) },
                    onClick = {
                        val newBonuses = mutableMapOf<AbilityName, Int>()
                        newBonuses[ability] = 2
                        plus1?.let { if (it != ability) newBonuses[it] = 1 }
                        onBonusesChanged(newBonuses)
                        expanded2 = false
                    },
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(height = 10.dp))
    MonoLabel(text = "+1 Ability")
    Spacer(modifier = Modifier.height(height = 4.dp))
    var expanded1 by remember { mutableStateOf(value = false) }
    ExposedDropdownMenuBox(expanded = expanded1, onExpandedChange = { expanded1 = it }) {
        OutlinedTextField(
            value = plus1?.let { AbilityScores.ABILITY_LABELS[it] } ?: "Select…",
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded1) },
            shape = RoundedCornerShape(size = 10.dp),
            modifier = Modifier
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
        )
        ExposedDropdownMenu(expanded = expanded1, onDismissRequest = { expanded1 = false }) {
            abilities.filter { it.key != plus2 }.forEach { (ability, label) ->
                DropdownMenuItem(
                    text = { Text(text = label) },
                    onClick = {
                        val newBonuses = mutableMapOf<AbilityName, Int>()
                        plus2?.let { newBonuses[it] = 2 }
                        newBonuses[ability] = 1
                        onBonusesChanged(newBonuses)
                        expanded1 = false
                    },
                )
            }
        }
    }
}

@Composable
private fun ThreeAbilityPicker(
    currentBonuses: Map<AbilityName, Int>,
    onBonusesChanged: (Map<AbilityName, Int>) -> Unit,
) {
    val abilities = AbilityScores.ABILITY_LABELS.entries.toList()

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 6.dp),
    ) {
        abilities.forEach { (ability, label) ->
            val isSelected = currentBonuses.containsKey(ability)
            SelectableCard(
                modifier = Modifier.padding(bottom = 6.dp),
                selected = isSelected,
                onClick = {
                    val newBonuses = currentBonuses.toMutableMap()
                    if (isSelected) {
                        newBonuses.remove(ability)
                    } else if (newBonuses.size < 3) {
                        newBonuses[ability] = 1
                    }
                    onBonusesChanged(newBonuses)
                },
                cornerRadius = 999,
                contentPadding = 6,
            ) {
                Text(
                    text = label.take(n = 3).uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 6.dp),
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun BackgroundStepSelectedPreview() {
    SessionZeroTheme(dynamicColor = false) {
        BackgroundStep(character = PreviewData.sampleCharacter)
    }
}

@PreviewLightDark
@Composable
private fun BackgroundStepEmptyPreview() {
    SessionZeroTheme(dynamicColor = false) {
        BackgroundStep(character = PreviewData.emptyCharacter)
    }
}
