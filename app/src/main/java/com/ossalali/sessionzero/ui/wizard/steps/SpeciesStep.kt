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
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.SpeciesName
import com.ossalali.sessionzero.domain.rules.SpeciesData
import com.ossalali.sessionzero.ui.common.DndCard
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.common.SelectionGrid
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun SpeciesStep(
    character: Character,
    onSpeciesSelected: (SpeciesName) -> Unit = {},
    onLineageSelected: (String?) -> Unit = {},
) {
    val speciesDef = character.species?.let { name -> SpeciesData.ALL_SPECIES.find { it.name == name } }
    var userExpandedGrid by remember { mutableStateOf(false) }
    val showCollapsed = speciesDef != null && !userExpandedGrid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        SectionHeader(text = "Choose Your Species")

        AnimatedContent(
            targetState = showCollapsed,
            transitionSpec = {
                (fadeIn() + slideInVertically())
                    .togetherWith(fadeOut() + slideOutVertically())
            },
            label = "speciesGridCollapse",
        ) { collapsed ->
            if (collapsed && speciesDef != null) {
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
                                text = speciesDef.name.displayName,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "${speciesDef.size}, ${speciesDef.speed}ft" +
                                        if (speciesDef.darkvision > 0) ", Darkvision ${speciesDef.darkvision}ft" else "",
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
                    items = SpeciesData.ALL_SPECIES,
                    selectedItem = speciesDef,
                    onSelect = {
                        onSpeciesSelected(it.name)
                        userExpandedGrid = false
                    },
                    label = { it.name.displayName },
                    description = { "${it.size}, ${it.speed}ft" + if (it.darkvision > 0) ", Darkvision ${it.darkvision}ft" else "" },
                )
            }
        }

        if (speciesDef != null && speciesDef.lineageOptions.isNotEmpty()) {
            Spacer(modifier = Modifier.height(height = 16.dp))
            SectionHeader(text = "Lineage")

            Column {
                speciesDef.lineageOptions.forEach { option ->
                    FilterChip(
                        selected = character.speciesLineage == option.name,
                        onClick = { onLineageSelected(option.name) },
                        label = { Text(text = option.name) },
                        modifier = Modifier.padding(end = 8.dp, bottom = 4.dp),
                    )
                }
            }
        }

        if (speciesDef != null && speciesDef.traits.isNotEmpty()) {
            Spacer(modifier = Modifier.height(height = 16.dp))
            SectionHeader(text = "Traits")

            speciesDef.traits.forEach { trait ->
                Text(
                    text = trait.name,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(top = 8.dp),
                )
                Text(
                    text = trait.description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 2.dp),
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SpeciesStepSelectedPreview() {
    SessionZeroTheme {
        SpeciesStep(character = PreviewData.sampleCharacter)
    }
}

@PreviewLightDark
@Composable
private fun SpeciesStepEmptyPreview() {
    SessionZeroTheme {
        SpeciesStep(character = PreviewData.emptyCharacter)
    }
}
