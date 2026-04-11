package com.ossalali.sessionzero.domain.model

data class AbilityScores(
    val str: Int = 10,
    val dex: Int = 10,
    val con: Int = 10,
    val int: Int = 10,
    val wis: Int = 10,
    val cha: Int = 10,
) {
    operator fun get(ability: AbilityName): Int = when (ability) {
        AbilityName.STR -> str
        AbilityName.DEX -> dex
        AbilityName.CON -> con
        AbilityName.INT -> int
        AbilityName.WIS -> wis
        AbilityName.CHA -> cha
    }

    fun with(ability: AbilityName, value: Int): AbilityScores = when (ability) {
        AbilityName.STR -> copy(str = value)
        AbilityName.DEX -> copy(dex = value)
        AbilityName.CON -> copy(con = value)
        AbilityName.INT -> copy(int = value)
        AbilityName.WIS -> copy(wis = value)
        AbilityName.CHA -> copy(cha = value)
    }

    companion object {
        val ABILITIES = AbilityName.entries

        val ABILITY_LABELS = mapOf(
            AbilityName.STR to "Strength",
            AbilityName.DEX to "Dexterity",
            AbilityName.CON to "Constitution",
            AbilityName.INT to "Intelligence",
            AbilityName.WIS to "Wisdom",
            AbilityName.CHA to "Charisma",
        )

        val SKILL_ABILITY_MAP: Map<SkillName, AbilityName> =
            SkillName.entries.associateWith { it.ability }

        val SKILLS_BY_ABILITY: Map<AbilityName, List<SkillName>> =
            SkillName.entries.groupBy { it.ability }
    }
}
