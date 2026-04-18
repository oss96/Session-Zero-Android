package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.Weapon
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.domain.rules.EquipmentData
import com.ossalali.sessionzero.ui.common.DndCard
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun WeaponStep(
    character: Character,
    onWeaponsChanged: (List<Weapon>) -> Unit = {},
) {
    val classDef = character.className?.let { name ->
        ClassData.ALL_CLASSES.find { it.name == name }
    }
    val proficientNames = EquipmentData.proficientWeaponNames(
        classProficiencies = classDef?.weaponProficiencies ?: emptyList(),
    )
    val selectedNames = character.weapons.map { it.name }.toSet()
    val catalogNames = remember { EquipmentData.ALL_WEAPONS.map { it.name }.toSet() }
    val catalogSelectedCount = selectedNames.count { it in catalogNames }
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
    ) {
        SectionHeader(text = "Weapons")

        if (classDef != null) {
            Text(
                text = "${classDef.name.displayName}: ${
                    classDef.weaponProficiencies.joinToString(
                        separator = ", "
                    )
                }",
                style = MaterialTheme.typography.bodySmall,
            )
        } else {
            Text(
                text = "Select a class to see weapon proficiencies.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Spacer(modifier = Modifier.height(height = 4.dp))

        Text(
            text = "$catalogSelectedCount weapon${if (catalogSelectedCount != 1) "s" else ""} selected",
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(height = 8.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(text = "Search weapons") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search",
                        )
                    }
                }
            },
        )

        val query = searchQuery.trim().lowercase()
        val weaponsByCategory = EquipmentData.WEAPONS_BY_CATEGORY
        LazyColumn(modifier = Modifier.weight(weight = 1f)) {
            weaponsByCategory.forEach { (category, weapons) ->
                val filteredWeapons = if (query.isEmpty()) {
                    weapons
                } else {
                    weapons.filter { weapon ->
                        weapon.name.lowercase().contains(query) ||
                                weapon.damage.lowercase().contains(query)
                    }
                }

                if (filteredWeapons.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(height = 12.dp))
                    }

                    stickyHeader {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.surface)
                                .padding(vertical = 4.dp),
                        )
                    }

                    items(items = filteredWeapons) { weapon ->
                        val isSelected = weapon.name in selectedNames
                        val isProficient = classDef == null || weapon.name in proficientNames
                        val contentAlpha = if (isProficient) 1f else 0.38f

                        DndCard(
                            selected = isSelected,
                            onClick = {
                                if (isSelected) {
                                    onWeaponsChanged(character.weapons.filter { it.name != weapon.name })
                                } else {
                                    onWeaponsChanged(character.weapons + weapon)
                                }
                            },
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column(modifier = Modifier.weight(weight = 1f)) {
                                    Text(
                                        text = weapon.name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                                    )
                                    Text(
                                        text = weapon.damage,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                            alpha = contentAlpha
                                        ),
                                    )
                                }
                                if (!isProficient) {
                                    Text(
                                        text = "(not proficient)",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(height = 6.dp))
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun WeaponStepPreview() {
    SessionZeroTheme {
        WeaponStep(character = PreviewData.sampleCharacter)
    }
}
