package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.AbilityScores
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.rules.BackgroundData
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.common.SelectionGrid
import com.ossalali.sessionzero.ui.wizard.WizardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackgroundStep(
    character: Character,
    viewModel: WizardViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        SectionHeader("Choose Your Background")

        SelectionGrid(
            items = BackgroundData.ALL_BACKGROUNDS,
            selectedItem = character.background?.let { name -> BackgroundData.ALL_BACKGROUNDS.find { it.name == name } },
            onSelect = { viewModel.setBackground(it.name) },
            label = { it.name.displayName },
            description = { it.description },
        )

        val bgDef = character.background?.let { name -> BackgroundData.ALL_BACKGROUNDS.find { it.name == name } }

        if (bgDef != null) {
            Spacer(Modifier.height(16.dp))
            SectionHeader("Origin Feat: ${bgDef.originFeat}")

            Spacer(Modifier.height(8.dp))
            SectionHeader("Ability Score Bonuses")
            Text(
                text = "Choose +2/+1 to two abilities, or +1/+1/+1 to three abilities",
                style = MaterialTheme.typography.bodySmall,
            )

            var bonusMode by remember { mutableIntStateOf(0) }
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(
                    selected = bonusMode == 0,
                    onClick = {
                        bonusMode = 0
                        viewModel.setAbilityScoreBonuses(emptyMap())
                    },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                ) { Text("+2/+1") }
                SegmentedButton(
                    selected = bonusMode == 1,
                    onClick = {
                        bonusMode = 1
                        viewModel.setAbilityScoreBonuses(emptyMap())
                    },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                ) { Text("+1/+1/+1") }
            }

            Spacer(Modifier.height(8.dp))

            if (bonusMode == 0) {
                TwoAbilityPicker(character.abilityScoreBonuses, viewModel)
            } else {
                ThreeAbilityPicker(character.abilityScoreBonuses, viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TwoAbilityPicker(
    currentBonuses: Map<AbilityName, Int>,
    viewModel: WizardViewModel,
) {
    val abilities = AbilityScores.ABILITY_LABELS.entries.toList()
    val plus2 = currentBonuses.entries.firstOrNull { it.value == 2 }?.key
    val plus1 = currentBonuses.entries.firstOrNull { it.value == 1 }?.key

    Text("+2 Ability:", style = MaterialTheme.typography.bodyMedium)
    var expanded2 by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded2, onExpandedChange = { expanded2 = it }) {
        OutlinedTextField(
            value = plus2?.let { AbilityScores.ABILITY_LABELS[it] } ?: "Select...",
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded2) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
        )
        ExposedDropdownMenu(expanded = expanded2, onDismissRequest = { expanded2 = false }) {
            abilities.forEach { (ability, label) ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        val newBonuses = mutableMapOf<AbilityName, Int>()
                        newBonuses[ability] = 2
                        plus1?.let { if (it != ability) newBonuses[it] = 1 }
                        viewModel.setAbilityScoreBonuses(newBonuses)
                        expanded2 = false
                    },
                )
            }
        }
    }

    Spacer(Modifier.height(8.dp))
    Text("+1 Ability:", style = MaterialTheme.typography.bodyMedium)
    var expanded1 by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded1, onExpandedChange = { expanded1 = it }) {
        OutlinedTextField(
            value = plus1?.let { AbilityScores.ABILITY_LABELS[it] } ?: "Select...",
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded1) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
        )
        ExposedDropdownMenu(expanded = expanded1, onDismissRequest = { expanded1 = false }) {
            abilities.filter { it.key != plus2 }.forEach { (ability, label) ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        val newBonuses = mutableMapOf<AbilityName, Int>()
                        plus2?.let { newBonuses[it] = 2 }
                        newBonuses[ability] = 1
                        viewModel.setAbilityScoreBonuses(newBonuses)
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
    viewModel: WizardViewModel,
) {
    val abilities = AbilityScores.ABILITY_LABELS.entries.toList()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        abilities.forEach { (ability, label) ->
            val isSelected = currentBonuses.containsKey(ability)
            androidx.compose.material3.FilterChip(
                selected = isSelected,
                onClick = {
                    val newBonuses = currentBonuses.toMutableMap()
                    if (isSelected) {
                        newBonuses.remove(ability)
                    } else if (newBonuses.size < 3) {
                        newBonuses[ability] = 1
                    }
                    viewModel.setAbilityScoreBonuses(newBonuses)
                },
                label = { Text(label.take(3)) },
            )
        }
    }
}
