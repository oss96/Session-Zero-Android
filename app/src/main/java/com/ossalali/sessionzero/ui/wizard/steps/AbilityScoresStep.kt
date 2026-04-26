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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.AbilityScores
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.rules.AbilityRules
import com.ossalali.sessionzero.domain.rules.GameRules
import com.ossalali.sessionzero.ui.common.DuskSectionHeader
import com.ossalali.sessionzero.ui.common.DuskSegmentedControl
import com.ossalali.sessionzero.ui.common.MonoLabel
import com.ossalali.sessionzero.ui.common.SelectableCard
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.LocalDuskTypography
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

private val METHODS = listOf("pointBuy", "standardArray", "rolled", "manual")
private val METHOD_LABELS = listOf("Point Buy", "Array", "Roll", "Manual")

@Composable
fun AbilityScoresStep(
    character: Character,
    onMethodChanged: (String) -> Unit = {},
    onBaseScoreChanged: (AbilityName, Int) -> Unit = { _, _ -> },
    onAllScoresChanged: (Int, Int, Int, Int, Int, Int) -> Unit = { _, _, _, _, _, _ -> },
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val selectedMethod = character.abilityScoreMethod ?: "pointBuy"
    val selectedIndex = METHODS.indexOf(element = selectedMethod).coerceAtLeast(minimumValue = 0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        DuskSectionHeader(title = "Ability scores", kicker = "Your aptitudes")

        DuskSegmentedControl(
            options = METHOD_LABELS,
            selectedIndex = selectedIndex,
            onSelect = { index -> onMethodChanged(METHODS[index]) },
        )
        Spacer(modifier = Modifier.height(height = 12.dp))

        when (selectedMethod) {
            "pointBuy" -> PointBuyHeaderCard(character = character)
            "rolled" -> RollHeaderCard(
                onRoll = {
                    val scores = AbilityName.entries.map { AbilityRules.rollAbilityScore() }
                    onAllScoresChanged(scores[0], scores[1], scores[2], scores[3], scores[4], scores[5])
                    onMethodChanged("rolled")
                },
            )
            "standardArray" -> StandardArrayHeaderCard()
            "manual" -> Unit
        }

        if (selectedMethod != "manual") Spacer(modifier = Modifier.height(height = 8.dp))

        when (selectedMethod) {
            "pointBuy" -> PointBuyRows(character = character, onBaseScoreChanged = onBaseScoreChanged)
            "standardArray" -> StandardArrayRows(
                character = character,
                onBaseScoreChanged = onBaseScoreChanged,
            )
            "rolled" -> RollRows(character = character)
            "manual" -> ManualRows(character = character, onBaseScoreChanged = onBaseScoreChanged)
        }

        Spacer(modifier = Modifier.height(height = 24.dp))
    }
}

@Composable
private fun PointBuyHeaderCard(character: Character) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val scoreList = AbilityName.entries.map { character.baseAbilityScores[it] }
    val remaining = AbilityRules.remainingPoints(scores = scoreList)
    val fraction = remaining.toFloat() / AbilityRules.POINT_BUY_TOTAL.toFloat()

    SelectableCard(contentPadding = 12) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(weight = 1f)) {
                MonoLabel(text = "Points remaining")
                Text(
                    text = "$remaining",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = scheme.primary,
                )
                MonoLabel(text = "of ${AbilityRules.POINT_BUY_TOTAL}")
            }
            // Simple progress ring via Canvas
            Box(
                modifier = Modifier.size(size = 60.dp),
                contentAlignment = Alignment.Center,
            ) {
                androidx.compose.foundation.Canvas(modifier = Modifier.size(size = 60.dp)) {
                    drawCircle(
                        color = tokens.surface2,
                        radius = size.minDimension / 2.4f,
                        style = Stroke(width = 5f),
                    )
                    val sweep = 360f * fraction.coerceIn(minimumValue = 0f, maximumValue = 1f)
                    drawArc(
                        color = scheme.primary,
                        startAngle = -90f,
                        sweepAngle = sweep,
                        useCenter = false,
                        style = Stroke(width = 5f, cap = androidx.compose.ui.graphics.StrokeCap.Round),
                    )
                }
            }
        }
    }
}

@Composable
private fun RollHeaderCard(onRoll: () -> Unit) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme

    SelectableCard(contentPadding = 12) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(weight = 1f)) {
                Text(
                    text = "Roll 4d6, drop lowest",
                    style = MaterialTheme.typography.bodyMedium,
                )
                MonoLabel(text = "6 rolls · assign freely")
            }
            Button(
                onClick = onRoll,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = scheme.primary,
                    contentColor = scheme.onPrimary,
                ),
            ) {
                Icon(
                    imageVector = Icons.Default.Casino,
                    contentDescription = null,
                    modifier = Modifier.size(size = 16.dp),
                )
                Spacer(modifier = Modifier.width(width = 6.dp))
                Text(text = "Roll")
            }
        }
    }
}

@Composable
private fun StandardArrayHeaderCard() {
    SelectableCard(contentPadding = 12) {
        Column(modifier = Modifier.fillMaxWidth()) {
            MonoLabel(text = "Standard array")
            Spacer(modifier = Modifier.height(height = 4.dp))
            Text(
                text = AbilityRules.STANDARD_ARRAY.joinToString(separator = "  ·  "),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun PointBuyRows(
    character: Character,
    onBaseScoreChanged: (AbilityName, Int) -> Unit,
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val scoreList = AbilityName.entries.map { character.baseAbilityScores[it] }
    val remaining = AbilityRules.remainingPoints(scores = scoreList)

    Column(verticalArrangement = Arrangement.spacedBy(space = 8.dp)) {
        AbilityName.entries.forEach { ability ->
            val score = character.baseAbilityScores[ability]
            AbilityRowFrame(
                ability = ability,
                trailing = {
                    CircleIconButton(
                        enabled = AbilityRules.canDecrease(currentScore = score),
                        onClick = { onBaseScoreChanged(ability, score - 1) },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease",
                            modifier = Modifier.size(size = 14.dp),
                        )
                    }
                    Text(
                        text = "$score",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(width = 36.dp),
                        textAlign = TextAlign.Center,
                    )
                    CircleIconButton(
                        enabled = AbilityRules.canIncrease(currentScore = score, remainingPoints = remaining),
                        onClick = { onBaseScoreChanged(ability, score + 1) },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            modifier = Modifier.size(size = 14.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(width = 4.dp))
                    Text(
                        text = modString(score = score),
                        style = MaterialTheme.typography.titleMedium,
                        color = scheme.primary,
                        modifier = Modifier.width(width = 34.dp),
                        textAlign = TextAlign.End,
                    )
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StandardArrayRows(
    character: Character,
    onBaseScoreChanged: (AbilityName, Int) -> Unit,
) {
    val assignments = remember { mutableStateMapOf<AbilityName, Int>() }
    if (assignments.isEmpty()) {
        AbilityName.entries.forEach { ability ->
            val score = character.baseAbilityScores[ability]
            if (score in AbilityRules.STANDARD_ARRAY) assignments[ability] = score
        }
    }
    val used = assignments.values.toSet()

    Column(verticalArrangement = Arrangement.spacedBy(space = 8.dp)) {
        AbilityName.entries.forEach { ability ->
            AbilityRowFrame(
                ability = ability,
                trailing = {
                    var expanded by remember { mutableStateOf(value = false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                    ) {
                        OutlinedTextField(
                            value = assignments[ability]?.toString() ?: "—",
                            onValueChange = {},
                            readOnly = true,
                            singleLine = true,
                            shape = RoundedCornerShape(size = 8.dp),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier
                                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                                .width(width = 96.dp),
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            AbilityRules.STANDARD_ARRAY.forEach { value ->
                                val isUsed = value in used && assignments[ability] != value
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
                },
            )
        }
    }
}

@Composable
private fun RollRows(character: Character) {
    val scheme = MaterialTheme.colorScheme
    Column(verticalArrangement = Arrangement.spacedBy(space = 8.dp)) {
        AbilityName.entries.forEach { ability ->
            val score = character.baseAbilityScores[ability]
            AbilityRowFrame(
                ability = ability,
                trailing = {
                    Text(
                        text = "$score",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(width = 36.dp),
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = modString(score = score),
                        style = MaterialTheme.typography.titleMedium,
                        color = scheme.primary,
                        modifier = Modifier.width(width = 36.dp),
                        textAlign = TextAlign.End,
                    )
                },
            )
        }
    }
}

@Composable
private fun ManualRows(
    character: Character,
    onBaseScoreChanged: (AbilityName, Int) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(space = 8.dp)) {
        AbilityName.entries.forEach { ability ->
            val score = character.baseAbilityScores[ability]
            var textFieldValue by remember(key1 = score) {
                mutableStateOf(value = TextFieldValue(text = "$score", selection = TextRange(index = "$score".length)))
            }
            AbilityRowFrame(
                ability = ability,
                trailing = {
                    OutlinedTextField(
                        value = textFieldValue,
                        onValueChange = { input ->
                            val filtered = input.text.filter { it.isDigit() }
                            val parsed = filtered.toIntOrNull()
                            if (parsed != null) {
                                val clamped = parsed.coerceIn(minimumValue = 1, maximumValue = 30)
                                textFieldValue = TextFieldValue(
                                    text = "$clamped",
                                    selection = TextRange(index = "$clamped".length),
                                )
                                onBaseScoreChanged(ability, clamped)
                            } else {
                                textFieldValue = input.copy(text = filtered)
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(size = 8.dp),
                        modifier = Modifier.width(width = 72.dp),
                    )
                },
            )
        }
    }
}

@Composable
private fun AbilityRowFrame(
    ability: AbilityName,
    trailing: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit,
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val label = AbilityScores.ABILITY_LABELS[ability] ?: ability.name

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(color = scheme.surface)
            .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 12.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(size = 34.dp)
                .clip(shape = RoundedCornerShape(size = 9.dp))
                .background(color = tokens.surface2),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = ability.name.first().toString(),
                style = MaterialTheme.typography.titleMedium,
                color = scheme.primary,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.width(width = 10.dp))
        Column(modifier = Modifier.weight(weight = 1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
            )
            MonoLabel(text = ability.name)
        }
        trailing()
    }
}

@Composable
private fun CircleIconButton(
    enabled: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val alpha = if (enabled) 1f else 0.4f
    androidx.compose.runtime.CompositionLocalProvider(
        androidx.compose.material3.LocalContentColor provides scheme.onSurface.copy(alpha = alpha),
    ) {
        Box(
            modifier = Modifier
                .size(size = 28.dp)
                .clip(shape = CircleShape)
                .background(color = tokens.surface2)
                .border(width = 1.dp, color = scheme.outline, shape = CircleShape)
                .then(other = if (enabled) Modifier.clickable(onClick = onClick) else Modifier)
                .padding(all = 6.dp),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

private fun modString(score: Int): String {
    val mod = GameRules.abilityModifier(score = score)
    return if (mod >= 0) "+$mod" else "$mod"
}

@PreviewLightDark
@Composable
private fun AbilityScoresStepPreview() {
    SessionZeroTheme(dynamicColor = false) {
        AbilityScoresStep(character = PreviewData.sampleCharacter)
    }
}
