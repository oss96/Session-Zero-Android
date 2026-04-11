package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.ClassName
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.ui.common.DndCard
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.common.SelectionGrid
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassStep(
    character: Character,
    onClassSelected: (ClassName) -> Unit = {},
    onLevelChanged: (Int) -> Unit = {},
    onSubclassSelected: (String?) -> Unit = {},
) {
    val classDef = character.className?.let { name -> ClassData.ALL_CLASSES.find { it.name == name } }
    var userExpandedGrid by remember { mutableStateOf(false) }
    val showCollapsed = classDef != null && !userExpandedGrid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        SectionHeader(text = "Choose Your Class")

        AnimatedContent(
            targetState = showCollapsed,
            transitionSpec = {
                (fadeIn() + slideInVertically())
                    .togetherWith(fadeOut() + slideOutVertically())
            },
            label = "classGridCollapse",
        ) { collapsed ->
            if (collapsed && classDef != null) {
                DndCard(
                    selected = true,
                    onClick = { userExpandedGrid = true },
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(weight = 1f)) {
                            Text(
                                text = classDef.name.displayName,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "d${classDef.hitDie} hit die",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                        TextButton(onClick = { userExpandedGrid = true }) {
                            Text(text = "Change")
                        }
                    }
                }
            } else {
                SelectionGrid(
                    items = ClassData.ALL_CLASSES,
                    selectedItem = classDef,
                    onSelect = {
                        onClassSelected(it.name)
                        userExpandedGrid = false
                    },
                    label = { it.name.displayName },
                    description = { "d${it.hitDie} hit die" },
                )
            }
        }

        Spacer(modifier = Modifier.height(height = 16.dp))

        SectionHeader(text = "Level: ${character.level}")
        Slider(
            value = character.level.toFloat(),
            onValueChange = { onLevelChanged(it.toInt()) },
            valueRange = 1f..20f,
            steps = 18,
            modifier = Modifier.fillMaxWidth(),
        )

        val subclassLevel = classDef?.subclassLevel ?: 3
        if (character.level >= subclassLevel && classDef != null) {
            val subclasses = classDef.subclasses
            if (subclasses.isNotEmpty()) {
                Spacer(modifier = Modifier.height(height = 16.dp))
                SectionHeader(text = "Subclass")

                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                ) {
                    OutlinedTextField(
                        value = character.subclass ?: "Select subclass...",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth(),
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        subclasses.forEach { sub ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(text = sub.name)
                                        Text(
                                            text = sub.description,
                                            style = MaterialTheme.typography.bodySmall,
                                            maxLines = 1,
                                        )
                                    }
                                },
                                onClick = {
                                    onSubclassSelected(sub.name)
                                    expanded = false
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun ClassStepSelectedPreview() {
    SessionZeroTheme {
        ClassStep(character = PreviewData.sampleCharacter)
    }
}

@PreviewLightDark
@Composable
private fun ClassStepEmptyPreview() {
    SessionZeroTheme {
        ClassStep(character = PreviewData.emptyCharacter)
    }
}
