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
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Alignment
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.wizard.WizardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsStep(
    character: Character,
    viewModel: WizardViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        SectionHeader(text = "Character Details")

        OutlinedTextField(
            value = character.name,
            onValueChange = { viewModel.setName(it) },
            label = { Text("Character Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = character.pronouns,
            onValueChange = { viewModel.setPronouns(it) },
            label = { Text("Pronouns") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(Modifier.height(8.dp))

        // Alignment dropdown
        var alignmentExpanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = alignmentExpanded,
            onExpandedChange = { alignmentExpanded = it },
        ) {
            OutlinedTextField(
                value = character.alignment?.displayName ?: "Select alignment...",
                onValueChange = {},
                readOnly = true,
                label = { Text("Alignment") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(alignmentExpanded) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth(),
            )
            ExposedDropdownMenu(
                expanded = alignmentExpanded,
                onDismissRequest = { alignmentExpanded = false },
            ) {
                Alignment.entries.forEach { alignment ->
                    DropdownMenuItem(
                        text = { Text(alignment.displayName) },
                        onClick = {
                            viewModel.setAlignment(alignment)
                            alignmentExpanded = false
                        },
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        SectionHeader(text = "Appearance")

        val appearance = character.appearance
        listOf(
            "Age" to appearance.age,
            "Height" to appearance.height,
            "Weight" to appearance.weight,
            "Eyes" to appearance.eyes,
            "Skin" to appearance.skin,
            "Hair" to appearance.hair,
        ).forEach { (label, value) ->
            OutlinedTextField(
                value = value,
                onValueChange = { newVal ->
                    val newAppearance = when (label) {
                        "Age" -> appearance.copy(age = newVal)
                        "Height" -> appearance.copy(height = newVal)
                        "Weight" -> appearance.copy(weight = newVal)
                        "Eyes" -> appearance.copy(eyes = newVal)
                        "Skin" -> appearance.copy(skin = newVal)
                        "Hair" -> appearance.copy(hair = newVal)
                        else -> appearance
                    }
                    viewModel.setAppearance(newAppearance)
                },
                label = { Text(label) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
            Spacer(Modifier.height(4.dp))
        }

        Spacer(Modifier.height(16.dp))
        SectionHeader(text = "Personality")

        OutlinedTextField(
            value = character.personalityTraits,
            onValueChange = { viewModel.setPersonalityTraits(it) },
            label = { Text("Personality Traits") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = character.ideals,
            onValueChange = { viewModel.setIdeals(it) },
            label = { Text("Ideals") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = character.bonds,
            onValueChange = { viewModel.setBonds(it) },
            label = { Text("Bonds") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = character.flaws,
            onValueChange = { viewModel.setFlaws(it) },
            label = { Text("Flaws") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
        )

        Spacer(Modifier.height(16.dp))
        SectionHeader(text = "Backstory")

        OutlinedTextField(
            value = character.backstory,
            onValueChange = { viewModel.setBackstory(it) },
            label = { Text("Backstory") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4,
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = character.alliesAndOrganizations,
            onValueChange = { viewModel.setAlliesAndOrganizations(it) },
            label = { Text("Allies & Organizations") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = character.additionalNotes,
            onValueChange = { viewModel.setAdditionalNotes(it) },
            label = { Text("Additional Notes") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
        )
    }
}
