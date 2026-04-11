package com.ossalali.sessionzero.domain.model

data class BackgroundDefinition(
    val name: BackgroundName,
    val description: String,
    val skillProficiencies: List<SkillName>,
    val toolProficiency: String? = null,
    val originFeat: String,
    val abilityBonusOptions: AbilityBonusOptions = AbilityBonusOptions(),
)

data class AbilityBonusOptions(
    val option1: Pair<Int, Int> = Pair(2, 1),
    val option2: Triple<Int, Int, Int> = Triple(1, 1, 1),
)
