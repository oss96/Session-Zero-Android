package com.ossalali.sessionzero.ui.wizard.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.rules.SpeciesData
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.common.SelectionGrid
import com.ossalali.sessionzero.ui.wizard.WizardViewModel

@Composable
fun SpeciesStep(
    character: Character,
    viewModel: WizardViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        SectionHeader(text = "Choose Your Species")

        SelectionGrid(
            items = SpeciesData.ALL_SPECIES,
            selectedItem = character.species?.let { name -> SpeciesData.ALL_SPECIES.find { it.name == name } },
            onSelect = { viewModel.setSpecies(it.name) },
            label = { it.name.displayName },
            description = { "${it.size}, ${it.speed}ft" + if (it.darkvision > 0) ", Darkvision ${it.darkvision}ft" else "" },
        )

        val speciesDef = character.species?.let { name -> SpeciesData.ALL_SPECIES.find { it.name == name } }

        if (speciesDef != null && speciesDef.lineageOptions.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            SectionHeader(text = "Lineage")

            Column {
                speciesDef.lineageOptions.forEach { option ->
                    FilterChip(
                        selected = character.speciesLineage == option.name,
                        onClick = { viewModel.setSpeciesLineage(option.name) },
                        label = { Text(option.name) },
                        modifier = Modifier.padding(end = 8.dp, bottom = 4.dp),
                    )
                }
            }
        }

        if (speciesDef != null && speciesDef.traits.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            SectionHeader(text = "Traits")

            speciesDef.traits.forEach { trait ->
                Text(
                    text = trait.name,
                    style = androidx.compose.material3.MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(top = 8.dp),
                )
                Text(
                    text = trait.description,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 2.dp),
                )
            }
        }
    }
}
