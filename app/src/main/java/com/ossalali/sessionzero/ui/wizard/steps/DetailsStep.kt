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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Alignment as DndAlignment
import com.ossalali.sessionzero.domain.model.Appearance
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.ui.common.DuskSectionHeader
import com.ossalali.sessionzero.ui.common.DuskSegmentedControl
import com.ossalali.sessionzero.ui.common.DuskTextField
import com.ossalali.sessionzero.ui.common.MonoLabel
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsStep(
    character: Character,
    onNameChanged: (String) -> Unit = {},
    onPronounsChanged: (String) -> Unit = {},
    onAlignmentChanged: (DndAlignment?) -> Unit = {},
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
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        DuskSectionHeader(title = "Details", kicker = "Identity")

        DuskTextField(
            label = "Character name",
            value = character.name,
            onValueChange = onNameChanged,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesters[1].requestFocus() },
            ),
        )
        Spacer(modifier = Modifier.height(height = 10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(space = 8.dp)) {
            DuskTextField(
                modifier = Modifier.weight(weight = 1f),
                label = "Pronouns",
                value = character.pronouns,
                onValueChange = onPronounsChanged,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
            // Alignment dropdown
            Column(modifier = Modifier.weight(weight = 1f)) {
                MonoLabel(text = "Alignment")
                Spacer(modifier = Modifier.height(height = 4.dp))
                var alignmentExpanded by remember { mutableStateOf(value = false) }
                ExposedDropdownMenuBox(
                    expanded = alignmentExpanded,
                    onExpandedChange = { alignmentExpanded = it },
                ) {
                    OutlinedTextField(
                        value = character.alignment?.displayName ?: "Select…",
                        onValueChange = {},
                        readOnly = true,
                        singleLine = true,
                        shape = RoundedCornerShape(size = 10.dp),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = alignmentExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth(),
                    )
                    ExposedDropdownMenu(
                        expanded = alignmentExpanded,
                        onDismissRequest = { alignmentExpanded = false },
                    ) {
                        DndAlignment.entries.forEach { alignment ->
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
            }
        }

        Spacer(modifier = Modifier.height(height = 16.dp))
        DuskSectionHeader(title = "Appearance", kicker = "What they look like")

        DuskTextField(
            label = "Age",
            value = appearance.age,
            onValueChange = { onAppearanceChanged(appearance.copy(age = it)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
        )
        Spacer(modifier = Modifier.height(height = 8.dp))

        // Height
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            DuskTextField(
                modifier = Modifier.weight(weight = 1f),
                label = "Height",
                value = appearance.height,
                onValueChange = { onAppearanceChanged(appearance.copy(height = it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
            )
            Column(modifier = Modifier.width(width = 140.dp)) {
                MonoLabel(text = "Unit")
                Spacer(modifier = Modifier.height(height = 4.dp))
                DuskSegmentedControl(
                    options = listOf("m", "ft"),
                    selectedIndex = if (appearance.heightUnit == "ft") 1 else 0,
                    onSelect = {
                        onAppearanceChanged(
                            appearance.copy(heightUnit = if (it == 0) "m" else "ft"),
                        )
                    },
                )
            }
        }
        Spacer(modifier = Modifier.height(height = 8.dp))

        // Weight
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            DuskTextField(
                modifier = Modifier.weight(weight = 1f),
                label = "Weight",
                value = appearance.weight,
                onValueChange = { onAppearanceChanged(appearance.copy(weight = it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
            )
            Column(modifier = Modifier.width(width = 140.dp)) {
                MonoLabel(text = "Unit")
                Spacer(modifier = Modifier.height(height = 4.dp))
                DuskSegmentedControl(
                    options = listOf("kg", "lbs"),
                    selectedIndex = if (appearance.weightUnit == "lbs") 1 else 0,
                    onSelect = {
                        onAppearanceChanged(
                            appearance.copy(weightUnit = if (it == 0) "kg" else "lbs"),
                        )
                    },
                )
            }
        }
        Spacer(modifier = Modifier.height(height = 8.dp))

        DuskTextField(
            label = "Eyes",
            value = appearance.eyes,
            onValueChange = { onAppearanceChanged(appearance.copy(eyes = it)) },
        )
        Spacer(modifier = Modifier.height(height = 8.dp))

        DuskTextField(
            label = "Skin",
            value = appearance.skin,
            onValueChange = { onAppearanceChanged(appearance.copy(skin = it)) },
        )
        Spacer(modifier = Modifier.height(height = 8.dp))

        DuskTextField(
            label = "Hair",
            value = appearance.hair,
            onValueChange = { onAppearanceChanged(appearance.copy(hair = it)) },
        )

        Spacer(modifier = Modifier.height(height = 16.dp))
        DuskSectionHeader(title = "Personality", kicker = "Who you are")

        DuskTextField(
            label = "Traits",
            value = character.personalityTraits,
            onValueChange = onPersonalityTraitsChanged,
            singleLine = false,
            minLines = 2,
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        DuskTextField(
            label = "Ideals",
            value = character.ideals,
            onValueChange = onIdealsChanged,
            singleLine = false,
            minLines = 2,
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        DuskTextField(
            label = "Bonds",
            value = character.bonds,
            onValueChange = onBondsChanged,
            singleLine = false,
            minLines = 2,
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        DuskTextField(
            label = "Flaws",
            value = character.flaws,
            onValueChange = onFlawsChanged,
            singleLine = false,
            minLines = 2,
        )

        Spacer(modifier = Modifier.height(height = 16.dp))
        DuskSectionHeader(title = "Backstory", kicker = "Tell their story")

        DuskTextField(
            label = "Backstory",
            value = character.backstory,
            onValueChange = onBackstoryChanged,
            singleLine = false,
            minLines = 4,
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        DuskTextField(
            label = "Allies & Organizations",
            value = character.alliesAndOrganizations,
            onValueChange = onAlliesChanged,
            singleLine = false,
            minLines = 2,
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        DuskTextField(
            label = "Additional notes",
            value = character.additionalNotes,
            onValueChange = onNotesChanged,
            singleLine = false,
            minLines = 2,
        )

        Spacer(modifier = Modifier.height(height = 24.dp))
    }
}

@PreviewLightDark
@Composable
private fun DetailsStepPreview() {
    SessionZeroTheme(dynamicColor = false) {
        DetailsStep(character = PreviewData.sampleCharacter)
    }
}
