package com.ossalali.sessionzero.domain.rules

import com.ossalali.sessionzero.domain.model.SpeciesDefinition
import com.ossalali.sessionzero.domain.model.SpeciesName

object SpeciesData {

    fun forSpecies(name: SpeciesName): SpeciesDefinition = ALL_SPECIES.first { it.name == name }

    val ALL_SPECIES: List<SpeciesDefinition> = listOf(
        // Populated in Phase 3
    )
}
