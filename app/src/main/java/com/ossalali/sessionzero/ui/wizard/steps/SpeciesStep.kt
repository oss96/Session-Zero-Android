package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.SpeciesName
import com.ossalali.sessionzero.domain.rules.SpeciesData
import com.ossalali.sessionzero.ui.common.DuskSectionHeader
import com.ossalali.sessionzero.ui.common.DuskTag
import com.ossalali.sessionzero.ui.common.DuskTagVariant
import com.ossalali.sessionzero.ui.common.DuskTextField
import com.ossalali.sessionzero.ui.common.MonoLabel
import com.ossalali.sessionzero.ui.common.SelectableCard
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun SpeciesStep(
    character: Character,
    onSpeciesSelected: (SpeciesName) -> Unit = {},
    onLineageSelected: (String?) -> Unit = {},
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val speciesDef = character.species?.let { name -> SpeciesData.ALL_SPECIES.find { it.name == name } }
    var query by remember { mutableStateOf(value = "") }

    val filtered = remember(query) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) SpeciesData.ALL_SPECIES
        else SpeciesData.ALL_SPECIES.filter { it.name.displayName.lowercase().contains(other = q) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        DuskSectionHeader(title = "Choose a species", kicker = "Origin")

        var userExpandedGrid by remember { mutableStateOf(value = false) }
        val showCollapsed = speciesDef != null && !userExpandedGrid

        AnimatedContent(
            targetState = showCollapsed,
            transitionSpec = {
                (fadeIn() + slideInVertically(initialOffsetY = { it / 6 }))
                    .togetherWith(fadeOut() + slideOutVertically(targetOffsetY = { it / 6 }))
            },
            label = "speciesGridCollapse",
        ) { collapsed ->
            if (collapsed && speciesDef != null) {
                // Collapsed summary card with Change button
                SelectableCard(contentPadding = 14) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(size = 40.dp)
                                .clip(shape = RoundedCornerShape(size = 10.dp))
                                .background(color = tokens.accentSoft),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = speciesDef.name.displayName.first().toString(),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = scheme.primary,
                            )
                        }
                        Spacer(modifier = Modifier.width(width = 10.dp))
                        Column(modifier = Modifier.weight(weight = 1f)) {
                            Text(
                                text = speciesDef.name.displayName,
                                style = MaterialTheme.typography.titleLarge,
                            )
                            val vision = if (speciesDef.darkvision > 0) "Darkvision ${speciesDef.darkvision}ft" else "—"
                            MonoLabel(text = "${speciesDef.size} · ${speciesDef.speed}ft · $vision")
                        }
                        TextButton(onClick = { userExpandedGrid = true }) {
                            Text(text = "Change")
                        }
                    }
                }
            } else {
                // Expanded search + single-column list
                Column {
                    DuskTextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = "Search ${SpeciesData.ALL_SPECIES.size} species…",
                        trailingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        },
                    )
                    Spacer(modifier = Modifier.height(height = 10.dp))

                    filtered.forEach { species ->
                        val isActive = species.name == character.species
                        SelectableCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 6.dp),
                            selected = isActive,
                            onClick = {
                                onSpeciesSelected(species.name)
                                userExpandedGrid = false
                            },
                            cornerRadius = 12,
                            contentPadding = 10,
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(size = 32.dp)
                                        .clip(shape = RoundedCornerShape(size = 8.dp))
                                        .background(color = tokens.surface2),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = species.name.displayName.first().toString(),
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isActive) scheme.primary else tokens.textDim,
                                    )
                                }
                                Spacer(modifier = Modifier.width(width = 10.dp))
                                Column(modifier = Modifier.weight(weight = 1f)) {
                                    Text(
                                        text = species.name.displayName,
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                    val vision = if (species.darkvision > 0) "Darkvision ${species.darkvision}ft" else "—"
                                    MonoLabel(text = "${species.size} · ${species.speed}ft · $vision")
                                }
                                if (isActive) {
                                    Box(
                                        modifier = Modifier
                                            .size(size = 22.dp)
                                            .clip(shape = CircleShape)
                                            .background(color = scheme.primary),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = scheme.onPrimary,
                                            modifier = Modifier.size(size = 13.dp),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (speciesDef != null && speciesDef.lineageOptions.isNotEmpty()) {
            Spacer(modifier = Modifier.height(height = 10.dp))
            DuskSectionHeader(title = "Lineage", kicker = "Heritage")
            Column {
                speciesDef.lineageOptions.forEach { option ->
                    SelectableCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        selected = character.speciesLineage == option.name,
                        onClick = { onLineageSelected(option.name) },
                        cornerRadius = 12,
                        contentPadding = 10,
                    ) {
                        Column {
                            Text(
                                text = option.name,
                                style = MaterialTheme.typography.titleSmall,
                            )
                            Text(
                                text = option.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = tokens.textDim,
                            )
                        }
                    }
                }
            }
        }

        if (speciesDef != null && speciesDef.traits.isNotEmpty()) {
            Spacer(modifier = Modifier.height(height = 10.dp))
            DuskSectionHeader(title = "Traits", kicker = "Gifts")
            SelectableCard(contentPadding = 12) {
                Column {
                    Row {
                        speciesDef.languages.forEach { lang ->
                            DuskTag(
                                text = lang,
                                variant = DuskTagVariant.Accent,
                                modifier = Modifier.padding(end = 6.dp),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(height = 8.dp))
                    speciesDef.traits.forEach { trait ->
                        Text(
                            text = trait.name,
                            style = MaterialTheme.typography.titleSmall,
                        )
                        Text(
                            text = trait.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = tokens.textDim,
                        )
                        Spacer(modifier = Modifier.height(height = 6.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(height = 24.dp))
    }
}

@PreviewLightDark
@Composable
private fun SpeciesStepSelectedPreview() {
    SessionZeroTheme(dynamicColor = false) {
        SpeciesStep(character = PreviewData.sampleCharacter)
    }
}

@PreviewLightDark
@Composable
private fun SpeciesStepEmptyPreview() {
    SessionZeroTheme(dynamicColor = false) {
        SpeciesStep(character = PreviewData.emptyCharacter)
    }
}
