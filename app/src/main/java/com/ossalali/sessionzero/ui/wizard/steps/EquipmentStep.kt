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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.Coins
import com.ossalali.sessionzero.domain.model.EquipmentItem
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.ui.common.DndCard
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
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
    val classDef = character.className?.let { ClassData.ALL_CLASSES.find { c -> c.name == it } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        SectionHeader(text = "Equipment")

        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            SegmentedButton(
                selected = character.equipmentChoice == "package",
                onClick = { onEquipmentChoiceChanged("package") },
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
            ) { Text(text = "Equipment Package") }
            SegmentedButton(
                selected = character.equipmentChoice == "gold",
                onClick = { onEquipmentChoiceChanged("gold") },
                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
            ) { Text(text = "Starting Gold") }
        }

        Spacer(modifier = Modifier.height(height = 16.dp))

        if (character.equipmentChoice == "package") {
            val packages = classDef?.equipmentPackages ?: emptyList()
            if (packages.isNotEmpty()) {
                packages.forEach { pkg ->
                    DndCard(
                        selected = character.equipment.map { it.name } == pkg.items.map { it.name },
                        onClick = {
                            onEquipmentSet(pkg.items)
                            onCoinsChanged(pkg.coins)
                        },
                    ) {
                        Text(text = pkg.name, style = MaterialTheme.typography.titleSmall)
                        pkg.items.forEach { item ->
                            Text(text = "- ${item.name}${if (item.quantity > 1) " x${item.quantity}" else ""}")
                        }
                    }
                    Spacer(modifier = Modifier.height(height = 8.dp))
                }
            } else {
                Text(text = "No equipment packages available for this class.")
            }
        }

        if (character.equipmentChoice == "gold") {
            if (classDef != null && classDef.startingGold.isNotEmpty()) {
                Text(text = "Starting gold: ${classDef.startingGold}")
            }
            Spacer(modifier = Modifier.height(height = 8.dp))
            CoinInputs(
                coins = character.coins,
                onCoinsChanged = onCoinsChanged,
            )
        }

        Spacer(modifier = Modifier.height(height = 16.dp))
        SectionHeader(text = "Inventory")

        character.equipment.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${item.name}${if (item.quantity > 1) " x${item.quantity}" else ""}",
                    modifier = Modifier.weight(weight = 1f),
                )
                IconButton(onClick = { onRemoveEquipmentItem(index) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove")
                }
            }
        }

        var newItemName by remember { mutableStateOf("") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = newItemName,
                onValueChange = { newItemName = it },
                label = { Text(text = "Add item") },
                modifier = Modifier.weight(weight = 1f),
                singleLine = true,
            )
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
    }
}

@Composable
private fun CoinInputs(
    coins: Coins,
    onCoinsChanged: (Coins) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
    ) {
        listOf("CP" to coins.cp, "SP" to coins.sp, "GP" to coins.gp, "PP" to coins.pp)
            .forEach { (label, value) ->
                OutlinedTextField(
                    value = if (value > 0) value.toString() else "",
                    onValueChange = { input ->
                        val v = input.toIntOrNull()?.coerceAtLeast(0) ?: 0
                        val newCoins = when (label) {
                            "CP" -> coins.copy(cp = v)
                            "SP" -> coins.copy(sp = v)
                            "GP" -> coins.copy(gp = v)
                            "PP" -> coins.copy(pp = v)
                            else -> coins
                        }
                        onCoinsChanged(newCoins)
                    },
                    label = { Text(text = label) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(width = 72.dp),
                    singleLine = true,
                )
            }
    }
}

@PreviewLightDark
@Composable
private fun EquipmentStepPreview() {
    SessionZeroTheme {
        EquipmentStep(character = PreviewData.sampleCharacter)
    }
}
