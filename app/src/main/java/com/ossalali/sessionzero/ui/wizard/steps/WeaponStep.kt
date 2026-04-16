package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        SectionHeader(text = "Weapons")

        if (classDef != null) {
            Text(
                text = "${classDef.name.displayName}: ${classDef.weaponProficiencies.joinToString(separator = ", ")}",
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
            text = "${selectedNames.size} weapon${if (selectedNames.size != 1) "s" else ""} selected",
            style = MaterialTheme.typography.bodyMedium,
        )

        EquipmentData.WEAPONS_BY_CATEGORY.forEach { (category, weapons) ->
            Spacer(modifier = Modifier.height(height = 12.dp))

            Text(
                text = category,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 4.dp),
            )

            weapons.forEach { weapon ->
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
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha),
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

@PreviewLightDark
@Composable
private fun WeaponStepPreview() {
    SessionZeroTheme {
        WeaponStep(character = PreviewData.sampleCharacter)
    }
}
