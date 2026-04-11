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
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.Coins
import com.ossalali.sessionzero.domain.model.EquipmentItem
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.wizard.WizardViewModel

@Composable
fun EquipmentStep(
    character: Character,
    viewModel: WizardViewModel,
) {
    val classDef = character.className?.let { ClassData.ALL_CLASSES.find { c -> c.name == it } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        SectionHeader("Equipment")

        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            SegmentedButton(
                selected = character.equipmentChoice == "package",
                onClick = { viewModel.setEquipmentChoice("package") },
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
            ) { Text("Equipment Package") }
            SegmentedButton(
                selected = character.equipmentChoice == "gold",
                onClick = { viewModel.setEquipmentChoice("gold") },
                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
            ) { Text("Starting Gold") }
        }

        Spacer(Modifier.height(16.dp))

        if (character.equipmentChoice == "package") {
            val packages = classDef?.equipmentPackages ?: emptyList()
            if (packages.isNotEmpty()) {
                packages.forEach { pkg ->
                    com.ossalali.sessionzero.ui.common.DndCard(
                        selected = character.equipment.map { it.name } == pkg.items.map { it.name },
                        onClick = {
                            viewModel.setEquipment(pkg.items)
                            viewModel.setCoins(pkg.coins)
                        },
                    ) {
                        Text(pkg.name, style = MaterialTheme.typography.titleSmall)
                        pkg.items.forEach { item ->
                            Text("- ${item.name}${if (item.quantity > 1) " x${item.quantity}" else ""}")
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            } else {
                Text("No equipment packages available for this class.")
            }
        }

        if (character.equipmentChoice == "gold") {
            if (classDef != null && classDef.startingGold.isNotEmpty()) {
                Text("Starting gold: ${classDef.startingGold}")
            }
            Spacer(Modifier.height(8.dp))
            CoinInputs(character.coins, viewModel)
        }

        Spacer(Modifier.height(16.dp))
        SectionHeader("Inventory")

        character.equipment.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "${item.name}${if (item.quantity > 1) " x${item.quantity}" else ""}",
                    modifier = Modifier.weight(1f),
                )
                IconButton(onClick = { viewModel.removeEquipmentItem(index) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove")
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
                label = { Text("Add item") },
                modifier = Modifier.weight(1f),
                singleLine = true,
            )
            IconButton(
                onClick = {
                    if (newItemName.isNotBlank()) {
                        viewModel.addEquipmentItem(EquipmentItem(name = newItemName.trim()))
                        newItemName = ""
                    }
                },
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}

@Composable
private fun CoinInputs(coins: Coins, viewModel: WizardViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                        viewModel.setCoins(newCoins)
                    },
                    label = { Text(label) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(72.dp),
                    singleLine = true,
                )
            }
    }
}
