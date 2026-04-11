package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.ClassName
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.common.SelectionGrid
import com.ossalali.sessionzero.ui.wizard.WizardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassStep(
    character: Character,
    viewModel: WizardViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        SectionHeader(text = "Choose Your Class")

        SelectionGrid(
            items = ClassData.ALL_CLASSES,
            selectedItem = character.className?.let { name -> ClassData.ALL_CLASSES.find { it.name == name } },
            onSelect = { viewModel.setClass(it.name) },
            label = { it.name.displayName },
            description = { "d${it.hitDie} hit die" },
        )

        Spacer(Modifier.height(16.dp))

        SectionHeader(text = "Level: ${character.level}")
        Slider(
            value = character.level.toFloat(),
            onValueChange = { viewModel.setLevel(it.toInt()) },
            valueRange = 1f..20f,
            steps = 18,
            modifier = Modifier.fillMaxWidth(),
        )

        if (character.level >= 3 && character.className != null) {
            val classDef = ClassData.ALL_CLASSES.find { it.name == character.className }
            val subclasses = classDef?.subclasses ?: emptyList()
            if (subclasses.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
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
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
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
                                        Text(sub.name)
                                        Text(
                                            sub.description,
                                            style = MaterialTheme.typography.bodySmall,
                                            maxLines = 1,
                                        )
                                    }
                                },
                                onClick = {
                                    viewModel.setSubclass(sub.name)
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
