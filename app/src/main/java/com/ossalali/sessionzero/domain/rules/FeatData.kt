package com.ossalali.sessionzero.domain.rules

data class FeatDefinition(
    val name: String,
    val description: String,
    val prerequisite: String? = null,
)

object FeatData {

    val ORIGIN_FEATS: List<FeatDefinition> = listOf(
        // Populated in Phase 3
    )
}
