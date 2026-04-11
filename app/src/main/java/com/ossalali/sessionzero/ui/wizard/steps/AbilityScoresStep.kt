package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.AbilityScores
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.rules.AbilityRules
import com.ossalali.sessionzero.domain.rules.GameRules
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.wizard.WizardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbilityScoresStep(
    character: Character,
    viewModel: WizardViewModel,
) {
    val methods = listOf("pointBuy", "standardArray", "rolled", "manual")
    val methodLabels = listOf("Point Buy", "Standard", "Roll", "Manual")
    val selectedMethod = character.abilityScoreMethod ?: "pointBuy"
    val selectedIndex = methods.indexOf(selectedMethod).coerceAtLeast(0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        SectionHeader("Ability Scores")

        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            methodLabels.forEachIndexed { index, label ->
                SegmentedButton(
                    selected = index == selectedIndex,
                    onClick = { viewModel.setAbilityScoreMethod(methods[index]) },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = methods.size),
                ) { Text(label, style = MaterialTheme.typography.labelSmall) }
            }
        }

        Spacer(Modifier.height(16.dp))

        when (selectedMethod) {
            "pointBuy" -> PointBuyPanel(character, viewModel)
            "standardArray" -> StandardArrayPanel(character, viewModel)
            "rolled" -> RollPanel(character, viewModel)
            "manual" -> ManualPanel(character, viewModel)
        }
    }
}

@Composable
private fun PointBuyPanel(character: Character, viewModel: WizardViewModel) {
    val scores = character.baseAbilityScores
    val scoreList = AbilityName.entries.map { scores[it] }
    val remaining = AbilityRules.remainingPoints(scoreList)

    Text(
        text = "Points Remaining: $remaining / ${AbilityRules.POINT_BUY_TOTAL}",
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
    )

    Spacer(Modifier.height(8.dp))

    AbilityName.entries.forEach { ability ->
        val score = scores[ability]
        val label = AbilityScores.ABILITY_LABELS[ability] ?: ability.name
        val mod = GameRules.abilityModifier(score)
        val sign = if (mod >= 0) "+" else ""

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = label, modifier = Modifier.width(100.dp))
            IconButton(
                onClick = { viewModel.setBaseAbilityScore(ability, score - 1) },
                enabled = AbilityRules.canDecrease(score),
            ) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease")
            }
            Text(
                text = "$score",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(32.dp),
            )
            IconButton(
                onClick = { viewModel.setBaseAbilityScore(ability, score + 1) },
                enabled = AbilityRules.canIncrease(score, remaining),
            ) {
                Icon(Icons.Default.Add, contentDescription = "Increase")
            }
            Text(
                text = "($sign$mod)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "Cost: ${AbilityRules.pointBuyCost(score)}",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StandardArrayPanel(character: Character, viewModel: WizardViewModel) {
    val assignments = remember { mutableStateMapOf<AbilityName, Int>() }

    // Initialize from character if already set
    if (assignments.isEmpty()) {
        val scores = character.baseAbilityScores
        AbilityName.entries.forEach { ability ->
            val score = scores[ability]
            if (score in AbilityRules.STANDARD_ARRAY) {
                assignments[ability] = score
            }
        }
    }

    val usedValues = assignments.values.toSet()

    Text(
        "Standard Array: ${AbilityRules.STANDARD_ARRAY.joinToString(", ")}",
        style = MaterialTheme.typography.titleSmall,
    )

    Spacer(Modifier.height(8.dp))

    AbilityName.entries.forEach { ability ->
        val label = AbilityScores.ABILITY_LABELS[ability] ?: ability.name
        var expanded by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = label, modifier = Modifier.width(100.dp))

            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                OutlinedTextField(
                    value = assignments[ability]?.toString() ?: "—",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .width(120.dp),
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    AbilityRules.STANDARD_ARRAY.forEach { value ->
                        val isUsed = value in usedValues && assignments[ability] != value
                        DropdownMenuItem(
                            text = { Text("$value") },
                            onClick = {
                                assignments[ability] = value
                                expanded = false
                                applyAssignments(assignments, viewModel)
                            },
                            enabled = !isUsed,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RollPanel(character: Character, viewModel: WizardViewModel) {
    Column {
        Text("Roll 4d6, drop lowest for each ability score.", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                val scores = AbilityName.entries.map { AbilityRules.rollAbilityScore() }
                viewModel.setAllBaseScores(scores[0], scores[1], scores[2], scores[3], scores[4], scores[5])
                viewModel.setAbilityScoreMethod("rolled")
            },
        ) {
            Icon(Icons.Default.Casino, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Roll All")
        }

        Spacer(Modifier.height(16.dp))

        AbilityName.entries.forEach { ability ->
            val score = character.baseAbilityScores[ability]
            val label = AbilityScores.ABILITY_LABELS[ability] ?: ability.name
            val mod = GameRules.abilityModifier(score)
            val sign = if (mod >= 0) "+" else ""

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = label, modifier = Modifier.width(100.dp))
                Text(
                    text = "$score",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = " ($sign$mod)",
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Composable
private fun ManualPanel(character: Character, viewModel: WizardViewModel) {
    AbilityName.entries.forEach { ability ->
        val score = character.baseAbilityScores[ability]
        val label = AbilityScores.ABILITY_LABELS[ability] ?: ability.name

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(text = label, modifier = Modifier.width(100.dp))
            OutlinedTextField(
                value = score.toString(),
                onValueChange = { input ->
                    val newVal = input.toIntOrNull()?.coerceIn(1, 30) ?: return@OutlinedTextField
                    viewModel.setBaseAbilityScore(ability, newVal)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(80.dp),
                singleLine = true,
            )
        }
    }
}

private fun applyAssignments(assignments: Map<AbilityName, Int>, viewModel: WizardViewModel) {
    assignments.forEach { (ability, score) ->
        viewModel.setBaseAbilityScore(ability, score)
    }
}
