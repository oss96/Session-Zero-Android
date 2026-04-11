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
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.AbilityScores
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.rules.AbilityRules
import com.ossalali.sessionzero.domain.rules.GameRules
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbilityScoresStep(
    character: Character,
    onMethodChanged: (String) -> Unit = {},
    onBaseScoreChanged: (AbilityName, Int) -> Unit = { _, _ -> },
    onAllScoresChanged: (Int, Int, Int, Int, Int, Int) -> Unit = { _, _, _, _, _, _ -> },
) {
    val methods = listOf("pointBuy", "standardArray", "rolled", "manual")
    val methodLabels = listOf("Point Buy", "Standard", "Roll", "Manual")
    val selectedMethod = character.abilityScoreMethod ?: "pointBuy"
    val selectedIndex = methods.indexOf(selectedMethod).coerceAtLeast(0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        SectionHeader(text = "Ability Scores")

        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            methodLabels.forEachIndexed { index, label ->
                SegmentedButton(
                    selected = index == selectedIndex,
                    onClick = { onMethodChanged(methods[index]) },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = methods.size),
                ) { Text(text = label, style = MaterialTheme.typography.labelSmall) }
            }
        }

        Spacer(modifier = Modifier.height(height = 16.dp))

        when (selectedMethod) {
            "pointBuy" -> PointBuyPanel(
                character = character,
                onBaseScoreChanged = onBaseScoreChanged,
            )
            "standardArray" -> StandardArrayPanel(
                character = character,
                onBaseScoreChanged = onBaseScoreChanged,
            )
            "rolled" -> RollPanel(
                character = character,
                onMethodChanged = onMethodChanged,
                onAllScoresChanged = onAllScoresChanged,
            )
            "manual" -> ManualPanel(
                character = character,
                onBaseScoreChanged = onBaseScoreChanged,
            )
        }
    }
}

@Composable
private fun PointBuyPanel(
    character: Character,
    onBaseScoreChanged: (AbilityName, Int) -> Unit,
) {
    val scores = character.baseAbilityScores
    val scoreList = AbilityName.entries.map { scores[it] }
    val remaining = AbilityRules.remainingPoints(scoreList)

    Text(
        text = "Points Remaining: $remaining / ${AbilityRules.POINT_BUY_TOTAL}",
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
    )

    Spacer(modifier = Modifier.height(height = 8.dp))

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
            Text(text = label, modifier = Modifier.width(width = 100.dp))
            IconButton(
                onClick = { onBaseScoreChanged(ability, score - 1) },
                enabled = AbilityRules.canDecrease(score),
            ) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease")
            }
            Text(
                text = "$score",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(width = 32.dp),
            )
            IconButton(
                onClick = { onBaseScoreChanged(ability, score + 1) },
                enabled = AbilityRules.canIncrease(score, remaining),
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
            }
            Text(
                text = "($sign$mod)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.weight(weight = 1f))
            Text(
                text = "Cost: ${AbilityRules.pointBuyCost(score)}",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StandardArrayPanel(
    character: Character,
    onBaseScoreChanged: (AbilityName, Int) -> Unit,
) {
    val assignments = remember { mutableStateMapOf<AbilityName, Int>() }

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
        text = "Standard Array: ${AbilityRules.STANDARD_ARRAY.joinToString(", ")}",
        style = MaterialTheme.typography.titleSmall,
    )

    Spacer(modifier = Modifier.height(height = 8.dp))

    AbilityName.entries.forEach { ability ->
        val label = AbilityScores.ABILITY_LABELS[ability] ?: ability.name
        var expanded by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = label, modifier = Modifier.width(width = 100.dp))

            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                OutlinedTextField(
                    value = assignments[ability]?.toString() ?: "—",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                        .width(width = 120.dp),
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    AbilityRules.STANDARD_ARRAY.forEach { value ->
                        val isUsed = value in usedValues && assignments[ability] != value
                        DropdownMenuItem(
                            text = { Text(text = "$value") },
                            onClick = {
                                assignments[ability] = value
                                expanded = false
                                assignments.forEach { (a, s) -> onBaseScoreChanged(a, s) }
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
private fun RollPanel(
    character: Character,
    onMethodChanged: (String) -> Unit,
    onAllScoresChanged: (Int, Int, Int, Int, Int, Int) -> Unit,
) {
    Column {
        Text(
            text = "Roll 4d6, drop lowest for each ability score.",
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(height = 8.dp))

        Button(
            onClick = {
                val scores = AbilityName.entries.map { AbilityRules.rollAbilityScore() }
                onAllScoresChanged(scores[0], scores[1], scores[2], scores[3], scores[4], scores[5])
                onMethodChanged("rolled")
            },
        ) {
            Icon(imageVector = Icons.Default.Casino, contentDescription = null)
            Spacer(modifier = Modifier.width(width = 8.dp))
            Text(text = "Roll All")
        }

        Spacer(modifier = Modifier.height(height = 16.dp))

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
                Text(text = label, modifier = Modifier.width(width = 100.dp))
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
private fun ManualPanel(
    character: Character,
    onBaseScoreChanged: (AbilityName, Int) -> Unit,
) {
    val abilities = AbilityName.entries
    val focusRequesters = remember { abilities.map { FocusRequester() } }
    val focusManager = LocalFocusManager.current

    abilities.forEachIndexed { index, ability ->
        val score = character.baseAbilityScores[ability]
        val label = AbilityScores.ABILITY_LABELS[ability] ?: ability.name
        val scoreText = score.toString()
        var textFieldValue by remember(score) {
            mutableStateOf(TextFieldValue(text = scoreText, selection = TextRange(scoreText.length)))
        }
        val isLast = index == abilities.lastIndex

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            Text(text = label, modifier = Modifier.width(width = 100.dp))
            OutlinedTextField(
                value = textFieldValue,
                onValueChange = { newValue ->
                    val filtered = newValue.text.filter { it.isDigit() }
                    textFieldValue = newValue.copy(text = filtered)
                    val parsed = filtered.toIntOrNull()
                    if (parsed != null) {
                        onBaseScoreChanged(ability, parsed.coerceIn(1, 30))
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (isLast) ImeAction.Done else ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesters[index + 1].requestFocus() },
                    onDone = { focusManager.clearFocus() },
                ),
                modifier = Modifier
                    .width(width = 80.dp)
                    .focusRequester(focusRequester = focusRequesters[index])
                    .onFocusChanged { state ->
                        if (state.isFocused) {
                            textFieldValue = textFieldValue.copy(
                                selection = TextRange(start = 0, end = textFieldValue.text.length),
                            )
                        }
                    },
                singleLine = true,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AbilityScoresStepPreview() {
    SessionZeroTheme {
        AbilityScoresStep(character = PreviewData.sampleCharacter)
    }
}
