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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.Coins
import com.ossalali.sessionzero.domain.model.EquipmentItem
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.ui.common.DuskSectionHeader
import com.ossalali.sessionzero.ui.common.DuskSegmentedControl
import com.ossalali.sessionzero.ui.common.DuskTextField
import com.ossalali.sessionzero.ui.common.MonoLabel
import com.ossalali.sessionzero.ui.common.SelectableCard
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun EquipmentStep(
    character: Character,
    onEquipmentChoiceChanged: (String) -> Unit = {},
    onCoinsChanged: (Coins) -> Unit = {},
    onEquipmentSet: (List<EquipmentItem>) -> Unit = {},
    onAddEquipmentItem: (EquipmentItem) -> Unit = {},
    onRemoveEquipmentItem: (Int) -> Unit = {},
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val classDef = character.className?.let { name -> ClassData.ALL_CLASSES.find { it.name == name } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        DuskSectionHeader(title = "Equipment package", kicker = "Starting gear")

        val modes = listOf("package", "gold")
        val modeLabels = listOf("Package", "Gold")
        val currentIndex = modes.indexOf(element = character.equipmentChoice).coerceAtLeast(minimumValue = 0)

        DuskSegmentedControl(
            options = modeLabels,
            selectedIndex = currentIndex,
            onSelect = { onEquipmentChoiceChanged(modes[it]) },
        )
        Spacer(modifier = Modifier.height(height = 10.dp))

        if (character.equipmentChoice == "package") {
            val packages = classDef?.equipmentPackages ?: emptyList()
            if (packages.isNotEmpty()) {
                packages.forEach { pkg ->
                    val isActive = character.equipment.map { it.name } == pkg.items.map { it.name }
                    SelectableCard(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        selected = isActive,
                        onClick = {
                            onEquipmentSet(pkg.items)
                            onCoinsChanged(pkg.coins)
                        },
                        contentPadding = 14,
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(size = 36.dp)
                                        .clip(shape = RoundedCornerShape(size = 10.dp))
                                        .background(color = tokens.surface2),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Backpack,
                                        contentDescription = null,
                                        tint = if (isActive) scheme.primary else tokens.textDim,
                                        modifier = Modifier.size(size = 20.dp),
                                    )
                                }
                                Spacer(modifier = Modifier.width(width = 10.dp))
                                Column(modifier = Modifier.weight(weight = 1f)) {
                                    Text(
                                        text = pkg.name,
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                    MonoLabel(text = "${pkg.items.size} items")
                                }
                                if (isActive) {
                                    Box(
                                        modifier = Modifier
                                            .size(size = 22.dp)
                                            .clip(shape = CircleShape)
                                            .background(color = scheme.primary),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = scheme.onPrimary,
                                            modifier = Modifier.size(size = 13.dp),
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(height = 8.dp))
                            pkg.items.forEach { item ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(size = 4.dp)
                                            .clip(shape = CircleShape)
                                            .background(color = scheme.primary.copy(alpha = 0.6f)),
                                    )
                                    Spacer(modifier = Modifier.width(width = 8.dp))
                                    Text(
                                        text = "${item.name}${if (item.quantity > 1) " ×${item.quantity}" else ""}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = tokens.textDim,
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "No equipment packages available for this class.",
                    style = MaterialTheme.typography.bodySmall,
                    color = tokens.textDim,
                )
            }
        }

        if (character.equipmentChoice == "gold") {
            if (classDef != null && classDef.startingGold.isNotEmpty()) {
                Text(
                    text = "Starting gold: ${classDef.startingGold}",
                    style = MaterialTheme.typography.bodySmall,
                    color = tokens.textDim,
                )
                Spacer(modifier = Modifier.height(height = 10.dp))
            }
        }

        // Coin purse (always visible)
        Spacer(modifier = Modifier.height(height = 8.dp))
        DuskSectionHeader(title = "Coin purse", kicker = "Starting gold")
        CoinPurse(coins = character.coins, onCoinsChanged = onCoinsChanged)

        Spacer(modifier = Modifier.height(height = 16.dp))
        DuskSectionHeader(title = "Inventory", kicker = "Equipment list")

        if (character.equipment.isEmpty()) {
            Text(
                text = "No items yet.",
                style = MaterialTheme.typography.bodySmall,
                color = tokens.textDim,
            )
        } else {
            SelectableCard(contentPadding = 0, cornerRadius = 12) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    character.equipment.forEachIndexed { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "${item.name}${if (item.quantity > 1) " ×${item.quantity}" else ""}",
                                modifier = Modifier.weight(weight = 1f),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            MonoLabel(text = "×${item.quantity}")
                            IconButton(onClick = { onRemoveEquipmentItem(index) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove",
                                    tint = tokens.textDim,
                                )
                            }
                        }
                        if (index < character.equipment.lastIndex) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(height = 1.dp)
                                    .background(color = scheme.outline),
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(height = 12.dp))

        var newItemName by remember { mutableStateOf(value = "") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DuskTextField(
                modifier = Modifier.weight(weight = 1f),
                value = newItemName,
                onValueChange = { newItemName = it },
                placeholder = "Add item",
            )
            Spacer(modifier = Modifier.width(width = 8.dp))
            IconButton(
                onClick = {
                    if (newItemName.isNotBlank()) {
                        onAddEquipmentItem(EquipmentItem(name = newItemName.trim()))
                        newItemName = ""
                    }
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }

        Spacer(modifier = Modifier.height(height = 24.dp))
    }
}

@Composable
private fun CoinPurse(
    coins: Coins,
    onCoinsChanged: (Coins) -> Unit,
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val entries = listOf(
        "CP" to coins.cp,
        "SP" to coins.sp,
        "GP" to coins.gp,
        "PP" to coins.pp,
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 6.dp),
    ) {
        entries.forEach { (label, value) ->
            Column(
                modifier = Modifier
                    .weight(weight = 1f)
                    .clip(shape = RoundedCornerShape(size = 10.dp))
                    .background(color = scheme.surface)
                    .border(
                        width = 1.dp,
                        color = scheme.outline,
                        shape = RoundedCornerShape(size = 10.dp),
                    )
                    .padding(vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                MonoLabel(text = label)
                androidx.compose.foundation.text.BasicTextField(
                    value = if (value > 0) "$value" else "0",
                    onValueChange = { input ->
                        val parsed = input.toIntOrNull()?.coerceAtLeast(minimumValue = 0) ?: 0
                        val newCoins = when (label) {
                            "CP" -> coins.copy(cp = parsed)
                            "SP" -> coins.copy(sp = parsed)
                            "GP" -> coins.copy(gp = parsed)
                            "PP" -> coins.copy(pp = parsed)
                            else -> coins
                        }
                        onCoinsChanged(newCoins)
                    },
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        color = if (value > 0) tokens.gold else scheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    cursorBrush = androidx.compose.ui.graphics.SolidColor(value = scheme.primary),
                    modifier = Modifier.width(width = 40.dp),
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun EquipmentStepPreview() {
    SessionZeroTheme(dynamicColor = false) {
        EquipmentStep(character = PreviewData.sampleCharacter)
    }
}
