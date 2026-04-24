package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
    val focusRequesters = remember { List(size = 8) { FocusRequester() } }
    val focusManager = LocalFocusManager.current
    val appearance = character.appearance

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        SectionHeader(text = "Character Details")

        // Index 0: Character Name
        OutlinedTextField(
            value = character.name,
            onValueChange = onNameChanged,
            label = { Text(text = "Character Name") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester = focusRequesters[0]),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesters[1].requestFocus() },
            ),
        )
        Spacer(modifier = Modifier.height(height = 8.dp))

        // Index 1: Pronouns
        OutlinedTextField(
            value = character.pronouns,
            onValueChange = onPronounsChanged,
            label = { Text(text = "Pronouns") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester = focusRequesters[1]),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesters[2].requestFocus() },
            ),
        )
        Spacer(modifier = Modifier.height(height = 8.dp))

        // Alignment dropdown (readOnly, no focus chaining)
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

        // Index 2: Age (numeric)
        OutlinedTextField(
            value = appearance.age,
            onValueChange = { onAppearanceChanged(appearance.copy(age = it)) },
            label = { Text(text = "Age") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester = focusRequesters[2]),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesters[3].requestFocus() },
            ),
        )
        Spacer(modifier = Modifier.height(height = 4.dp))

        // Index 3: Height (numeric + unit toggle)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = appearance.height,
                onValueChange = { onAppearanceChanged(appearance.copy(height = it)) },
                label = { Text(text = "Height") },
                modifier = Modifier
                    .weight(weight = 1f)
                    .focusRequester(focusRequester = focusRequesters[3]),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesters[4].requestFocus() },
                ),
            )
            SingleChoiceSegmentedButtonRow {
                SegmentedButton(
                    selected = appearance.heightUnit == "m",
                    onClick = { onAppearanceChanged(appearance.copy(heightUnit = "m")) },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                ) { Text(text = "m") }
                SegmentedButton(
                    selected = appearance.heightUnit == "ft",
                    onClick = { onAppearanceChanged(appearance.copy(heightUnit = "ft")) },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                ) { Text(text = "ft") }
            }
        }
        Spacer(modifier = Modifier.height(height = 4.dp))

        // Index 4: Weight (numeric + unit toggle)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = appearance.weight,
                onValueChange = { onAppearanceChanged(appearance.copy(weight = it)) },
                label = { Text(text = "Weight") },
                modifier = Modifier
                    .weight(weight = 1f)
                    .focusRequester(focusRequester = focusRequesters[4]),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesters[5].requestFocus() },
                ),
            )
            SingleChoiceSegmentedButtonRow {
                SegmentedButton(
                    selected = appearance.weightUnit == "kg",
                    onClick = { onAppearanceChanged(appearance.copy(weightUnit = "kg")) },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                ) { Text(text = "kg") }
                SegmentedButton(
                    selected = appearance.weightUnit == "lbs",
                    onClick = { onAppearanceChanged(appearance.copy(weightUnit = "lbs")) },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                ) { Text(text = "lbs") }
            }
        }
        Spacer(modifier = Modifier.height(height = 4.dp))

        // Indices 5-7: Eyes, Skin, Hair (text fields with IME navigation)
        listOf(
            Triple(first = "Eyes", second = appearance.eyes, third = 5),
            Triple(first = "Skin", second = appearance.skin, third = 6),
            Triple(first = "Hair", second = appearance.hair, third = 7),
        ).forEach { (label, value, focusIndex) ->
            val isLast = focusIndex == 7
            OutlinedTextField(
                value = value,
                onValueChange = { newVal ->
                    val newAppearance = when (label) {
                        "Eyes" -> appearance.copy(eyes = newVal)
                        "Skin" -> appearance.copy(skin = newVal)
                        "Hair" -> appearance.copy(hair = newVal)
                        else -> appearance
                    }
                    onAppearanceChanged(newAppearance)
                },
                label = { Text(text = label) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester = focusRequesters[focusIndex]),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = if (isLast) ImeAction.Done else ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        if (!isLast) focusRequesters[focusIndex + 1].requestFocus()
                    },
                    onDone = { focusManager.clearFocus() },
                ),
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
