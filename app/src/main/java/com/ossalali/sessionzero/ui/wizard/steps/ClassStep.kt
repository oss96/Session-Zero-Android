package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.ClassName
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.ui.common.DuskSectionHeader
import com.ossalali.sessionzero.ui.common.DuskTag
import com.ossalali.sessionzero.ui.common.DuskTagVariant
import com.ossalali.sessionzero.ui.common.MonoLabel
import com.ossalali.sessionzero.ui.common.SelectableCard
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassStep(
    character: Character,
    onClassSelected: (ClassName) -> Unit = {},
    onLevelChanged: (Int) -> Unit = {},
    onSubclassSelected: (String?) -> Unit = {},
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val classDef = character.className?.let { name -> ClassData.ALL_CLASSES.find { it.name == name } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        DuskSectionHeader(title = "Choose a class", kicker = "Calling")

        var userExpandedGrid by remember { mutableStateOf(value = false) }
        val showCollapsed = classDef != null && !userExpandedGrid

        AnimatedContent(
            targetState = showCollapsed,
            transitionSpec = {
                (fadeIn() + slideInVertically(initialOffsetY = { it / 6 }))
                    .togetherWith(fadeOut() + slideOutVertically(targetOffsetY = { it / 6 }))
            },
            label = "classGridCollapse",
        ) { collapsed ->
            if (collapsed && classDef != null) {
                // Collapsed summary card with Change button
                SelectableCard(contentPadding = 14) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(size = 40.dp)
                                    .clip(shape = RoundedCornerShape(size = 10.dp))
                                    .background(color = tokens.accentSoft),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = classDef.name.displayName.first().toString(),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = scheme.primary,
                                )
                            }
                            Spacer(modifier = Modifier.width(width = 10.dp))
                            Column(modifier = Modifier.weight(weight = 1f)) {
                                Text(
                                    text = classDef.name.displayName,
                                    style = MaterialTheme.typography.titleLarge,
                                )
                                MonoLabel(text = "Primary ${classDef.primaryAbility.joinToString(separator = "/") { it.name }}")
                            }
                            TextButton(onClick = { userExpandedGrid = true }) {
                                Text(text = "Change")
                            }
                        }
                        Spacer(modifier = Modifier.height(height = 10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(space = 6.dp)) {
                            DuskTag(text = "Hit die d${classDef.hitDie}", variant = DuskTagVariant.Accent)
                            DuskTag(text = "Save ${classDef.savingThrows.joinToString(separator = "/") { it.name }}")
                        }
                    }
                }
            } else {
                // Expanded 2-col grid of class cards
                Column {
                    val rows = ClassData.ALL_CLASSES.chunked(size = 2)
                    rows.forEach { row ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                        ) {
                            row.forEach { cls ->
                                val isActive = cls.name == character.className
                                SelectableCard(
                                    modifier = Modifier.weight(weight = 1f),
                                    selected = isActive,
                                    onClick = {
                                        onClassSelected(cls.name)
                                        userExpandedGrid = false
                                    },
                                    contentPadding = 10,
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(size = 36.dp)
                                                .clip(shape = RoundedCornerShape(size = 9.dp))
                                                .background(
                                                    color = if (isActive) scheme.primary.copy(alpha = 0.13f)
                                                    else tokens.surface2,
                                                ),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text(
                                                text = cls.name.displayName.first().toString(),
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isActive) scheme.primary else tokens.textDim,
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(width = 8.dp))
                                        Column(modifier = Modifier.weight(weight = 1f)) {
                                            Text(
                                                text = cls.name.displayName,
                                                style = MaterialTheme.typography.titleSmall,
                                                color = scheme.onSurface,
                                            )
                                            MonoLabel(text = "d${cls.hitDie}")
                                        }
                                    }
                                }
                            }
                            if (row.size == 1) Spacer(modifier = Modifier.weight(weight = 1f))
                        }
                    }
                }
            }
        }

        if (classDef != null) {
            Spacer(modifier = Modifier.height(height = 14.dp))

            // Level picker with gradient fill bar
            DuskSectionHeader(title = "Starting level", kicker = "Progress")
            SelectableCard(contentPadding = 14) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${character.level}",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = scheme.primary,
                        modifier = Modifier.width(width = 56.dp),
                    )
                    Spacer(modifier = Modifier.width(width = 12.dp))
                    Column(modifier = Modifier.weight(weight = 1f)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height = 6.dp)
                                .clip(shape = RoundedCornerShape(size = 3.dp))
                                .background(color = tokens.surface2),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(fraction = character.level / 20f)
                                    .height(height = 6.dp)
                                    .clip(shape = RoundedCornerShape(size = 3.dp))
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(tokens.accentDim, scheme.primary),
                                        ),
                                    ),
                            )
                        }
                        Slider(
                            value = character.level.toFloat(),
                            onValueChange = { onLevelChanged(it.toInt()) },
                            valueRange = 1f..20f,
                            steps = 18,
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            MonoLabel(text = "Level 1")
                            MonoLabel(text = "Level ${character.level}")
                            MonoLabel(text = "Level 20")
                        }
                    }
                }
            }

            // Subclass picker
            val subclassLevel = classDef.subclassLevel
            if (character.level >= subclassLevel && classDef.subclasses.isNotEmpty()) {
                Spacer(modifier = Modifier.height(height = 14.dp))
                DuskSectionHeader(title = "Subclass", kicker = "Specialization")

                var expanded by remember { mutableStateOf(value = false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                ) {
                    OutlinedTextField(
                        value = character.subclass ?: "Select subclass…",
                        onValueChange = {},
                        readOnly = true,
                        shape = RoundedCornerShape(size = 10.dp),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth(),
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        classDef.subclasses.forEach { sub ->
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

                val selectedSub = character.subclass?.let { name ->
                    classDef.subclasses.find { it.name == name }
                }
                if (selectedSub != null) {
                    val featuresAtLevel = selectedSub.features
                        .filter { it.key <= character.level }
                        .flatMap { it.value }
                    if (featuresAtLevel.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(height = 10.dp))
                        SelectableCard(contentPadding = 12) {
                            Column {
                                MonoLabel(text = "Subclass features")
                                Spacer(modifier = Modifier.height(height = 6.dp))
                                featuresAtLevel.forEach { feature ->
                                    Text(
                                        text = feature.name,
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                    Text(
                                        text = feature.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = tokens.textDim,
                                    )
                                    Spacer(modifier = Modifier.height(height = 6.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(height = 24.dp))
    }
}

@PreviewLightDark
@Composable
private fun ClassStepSelectedPreview() {
    SessionZeroTheme(dynamicColor = false) {
        ClassStep(character = PreviewData.sampleCharacter)
    }
}

@PreviewLightDark
@Composable
private fun ClassStepEmptyPreview() {
    SessionZeroTheme(dynamicColor = false) {
        ClassStep(character = PreviewData.emptyCharacter)
    }
}
