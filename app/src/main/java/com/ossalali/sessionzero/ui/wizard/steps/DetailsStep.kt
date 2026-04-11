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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Alignment
import com.ossalali.sessionzero.domain.model.Appearance
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsStep(
    character: Character,
    onNameChanged: (String) -> Unit = {},
    onPronounsChanged: (String) -> Unit = {},
    onAlignmentChanged: (Alignment?) -> Unit = {},
    onAppearanceChanged: (Appearance) -> Unit = {},
    onPersonalityTraitsChanged: (String) -> Unit = {},
    onIdealsChanged: (String) -> Unit = {},
    onBondsChanged: (String) -> Unit = {},
    onFlawsChanged: (String) -> Unit = {},
    onBackstoryChanged: (String) -> Unit = {},
    onAlliesChanged: (String) -> Unit = {},
    onNotesChanged: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        SectionHeader(text = "Character Details")

        OutlinedTextField(
            value = character.name,
            onValueChange = onNameChanged,
            label = { Text(text = "Character Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(height = 8.dp))

        OutlinedTextField(
            value = character.pronouns,
            onValueChange = onPronounsChanged,
            label = { Text(text = "Pronouns") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(height = 8.dp))

        var alignmentExpanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = alignmentExpanded,
            onExpandedChange = { alignmentExpanded = it },
        ) {
            OutlinedTextField(
                value = character.alignment?.displayName ?: "Select alignment...",
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Alignment") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = alignmentExpanded) },
                modifier = Modifier
                    .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth(),
            )
            ExposedDropdownMenu(
                expanded = alignmentExpanded,
                onDismissRequest = { alignmentExpanded = false },
            ) {
                Alignment.entries.forEach { alignment ->
                    DropdownMenuItem(
                        text = { Text(text = alignment.displayName) },
                        onClick = {
                            onAlignmentChanged(alignment)
                            alignmentExpanded = false
                        },
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(height = 16.dp))
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
                    onAppearanceChanged(newAppearance)
                },
                label = { Text(text = label) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(height = 4.dp))
        }

        Spacer(modifier = Modifier.height(height = 16.dp))
        SectionHeader(text = "Personality")

        OutlinedTextField(
            value = character.personalityTraits,
            onValueChange = onPersonalityTraitsChanged,
            label = { Text(text = "Personality Traits") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        OutlinedTextField(
            value = character.ideals,
            onValueChange = onIdealsChanged,
            label = { Text(text = "Ideals") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        OutlinedTextField(
            value = character.bonds,
            onValueChange = onBondsChanged,
            label = { Text(text = "Bonds") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        OutlinedTextField(
            value = character.flaws,
            onValueChange = onFlawsChanged,
            label = { Text(text = "Flaws") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
        )

        Spacer(modifier = Modifier.height(height = 16.dp))
        SectionHeader(text = "Backstory")

        OutlinedTextField(
            value = character.backstory,
            onValueChange = onBackstoryChanged,
            label = { Text(text = "Backstory") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4,
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        OutlinedTextField(
            value = character.alliesAndOrganizations,
            onValueChange = onAlliesChanged,
            label = { Text(text = "Allies & Organizations") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        OutlinedTextField(
            value = character.additionalNotes,
            onValueChange = onNotesChanged,
            label = { Text(text = "Additional Notes") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
        )
    }
}

@PreviewLightDark
@Composable
private fun DetailsStepPreview() {
    SessionZeroTheme {
        DetailsStep(character = PreviewData.sampleCharacter)
    }
}
