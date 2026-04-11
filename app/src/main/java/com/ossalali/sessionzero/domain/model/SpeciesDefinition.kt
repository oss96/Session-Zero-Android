package com.ossalali.sessionzero.domain.model

data class SpeciesDefinition(
    val name: SpeciesName,
    val speed: Int = 30,
    val size: String = "Medium",
    val traits: List<SpeciesTrait> = emptyList(),
    val languages: List<String> = listOf("Common"),
    val lineageOptions: List<LineageOption> = emptyList(),
    val darkvision: Int = 0,
)

data class SpeciesTrait(
    val name: String,
    val description: String,
)

data class LineageOption(
    val name: String,
    val description: String,
)
