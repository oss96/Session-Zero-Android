package com.ossalali.sessionzero.domain.model

data class DerivedStats(
    val maxHP: Int = 0,
    val armorClass: Int = 10,
    val initiative: Int = 0,
    val speed: Int = 30,
    val proficiencyBonus: Int = 2,
    val hitDice: String = "1d10",
    val spellSaveDC: Int? = null,
    val spellAttackBonus: Int? = null,
    val passivePerception: Int = 10,
    val passiveInvestigation: Int = 10,
    val passiveInsight: Int = 10,
    val savingThrows: Map<AbilityName, Int> = emptyMap(),
    val skillBonuses: Map<SkillName, Int> = emptyMap(),
    val abilityModifiers: Map<AbilityName, Int> = emptyMap(),
)
