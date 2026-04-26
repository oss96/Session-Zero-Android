package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.Weapon
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.domain.rules.EquipmentData
import com.ossalali.sessionzero.ui.common.DuskSectionHeader
import com.ossalali.sessionzero.ui.common.DuskTag
import com.ossalali.sessionzero.ui.common.DuskTagVariant
import com.ossalali.sessionzero.ui.common.DuskTextField
import com.ossalali.sessionzero.ui.common.MonoLabel
import com.ossalali.sessionzero.ui.common.SelectableCard
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeaponStep(
    character: Character,
    onWeaponsChanged: (List<Weapon>) -> Unit = {},
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val classDef = character.className?.let { name ->
        ClassData.ALL_CLASSES.find { it.name == name }
    }
    val proficientNames = EquipmentData.proficientWeaponNames(
        classProficiencies = classDef?.weaponProficiencies ?: emptyList(),
    )
    val selectedNames = character.weapons.map { it.name }.toSet()
    val catalogNames = remember { EquipmentData.ALL_WEAPONS.map { it.name }.toSet() }
    val catalogSelectedCount = selectedNames.count { it in catalogNames }
    var searchQuery by remember { mutableStateOf(value = "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        DuskSectionHeader(title = "Weapons", kicker = "Arsenal")

        // Selected weapons — always at top
        if (character.weapons.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(space = 8.dp)) {
                character.weapons.forEach { weapon ->
                    SelectedWeaponCard(weapon = weapon)
                }
            }
            Spacer(modifier = Modifier.height(height = 12.dp))
        }

        val prefix = if (classDef != null) "${classDef.name.displayName} · " else ""
        Text(
            text = "$prefix$catalogSelectedCount weapon${if (catalogSelectedCount != 1) "s" else ""} selected",
            style = MaterialTheme.typography.bodySmall,
            color = tokens.textDim,
        )
        Spacer(modifier = Modifier.height(height = 8.dp))

        DuskTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = "Search weapons",
            trailingIcon = if (searchQuery.isNotEmpty()) {
                @Composable {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                        )
                    }
                }
            } else null,
        )
        Spacer(modifier = Modifier.height(height = 10.dp))

        val query = searchQuery.trim().lowercase()
        val weaponsByCategory = EquipmentData.WEAPONS_BY_CATEGORY
        LazyColumn(
            modifier = Modifier.weight(weight = 1f),
            verticalArrangement = Arrangement.spacedBy(space = 6.dp),
        ) {
            weaponsByCategory.forEach { (category, weapons) ->
                val filtered = if (query.isEmpty()) weapons else weapons.filter { weapon ->
                    weapon.name.lowercase().contains(other = query) ||
                            weapon.damage.lowercase().contains(other = query)
                }
                if (filtered.isNotEmpty()) {
                    stickyHeader {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = scheme.background)
                                .padding(vertical = 6.dp),
                        ) {
                            MonoLabel(text = category)
                        }
                    }
                    items(items = filtered, key = { "${category}:${it.name}" }) { weapon ->
                        val isSelected = weapon.name in selectedNames
                        val isProficient = classDef == null || weapon.name in proficientNames
                        val alpha = if (isProficient) 1f else 0.45f

                        SelectableCard(
                            modifier = Modifier.fillMaxWidth(),
                            selected = isSelected,
                            onClick = {
                                if (isSelected) {
                                    onWeaponsChanged(character.weapons.filter { it.name != weapon.name })
                                } else {
                                    onWeaponsChanged(character.weapons + weapon)
                                }
                            },
                            cornerRadius = 10,
                            contentPadding = 10,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Column(modifier = Modifier.weight(weight = 1f)) {
                                    Text(
                                        text = weapon.name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = scheme.onSurface.copy(alpha = alpha),
                                    )
                                    MonoLabel(text = weapon.damage)
                                }
                                if (!isProficient) {
                                    DuskTag(
                                        text = "Not proficient",
                                        variant = DuskTagVariant.Default,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectedWeaponCard(weapon: Weapon) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme

    SelectableCard(modifier = Modifier.fillMaxWidth(), contentPadding = 12) {
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
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = scheme.primary,
                        modifier = Modifier.size(size = 20.dp),
                    )
                }
                Spacer(modifier = Modifier.width(width = 10.dp))
                Column(modifier = Modifier.weight(weight = 1f)) {
                    Text(text = weapon.name, style = MaterialTheme.typography.titleSmall)
                    MonoLabel(text = weapon.damage)
                }
            }
            Spacer(modifier = Modifier.height(height = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(space = 8.dp)) {
                StatPanel(
                    modifier = Modifier.weight(weight = 1f),
                    label = "To Hit",
                    value = weapon.attackBonus.ifEmpty { "—" },
                    accent = true,
                )
                StatPanel(
                    modifier = Modifier.weight(weight = 2f),
                    label = "Damage",
                    value = weapon.damage.ifEmpty { "—" },
                    accent = false,
                )
            }
        }
    }
}

@Composable
private fun StatPanel(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    accent: Boolean,
) {
    val scheme = MaterialTheme.colorScheme
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(color = scheme.background)
            .border(
                width = 1.dp,
                color = scheme.outline,
                shape = RoundedCornerShape(size = 8.dp),
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
    ) {
        MonoLabel(text = label)
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = if (accent) scheme.primary else scheme.onSurface,
        )
    }
}

@PreviewLightDark
@Composable
private fun WeaponStepPreview() {
    SessionZeroTheme(dynamicColor = false) {
        WeaponStep(character = PreviewData.sampleCharacter)
    }
}
