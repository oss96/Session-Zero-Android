package com.ossalali.sessionzero.domain.model

data class ClassDefinition(
    val name: ClassName,
    val hitDie: Int,
    val primaryAbility: List<AbilityName>,
    val savingThrows: List<AbilityName>,
    val skillChoices: List<SkillName>,
    val numSkillChoices: Int,
    val armorProficiencies: List<String>,
    val weaponProficiencies: List<String>,
    val toolProficiencies: List<String> = emptyList(),
    val features: Map<Int, List<ClassFeature>> = emptyMap(),
    val subclasses: List<SubclassDefinition> = emptyList(),
    val subclassLevel: Int = 3,
    val spellcasting: SpellcastingProfile? = null,
    val equipmentPackages: List<EquipmentPackage> = emptyList(),
    val startingGold: String = "",
)

data class ClassFeature(
    val name: String,
    val description: String,
    val level: Int,
)

data class SubclassDefinition(
    val name: String,
    val description: String,
    val features: Map<Int, List<ClassFeature>> = emptyMap(),
    val spellcasting: SpellcastingProfile? = null,
)

data class SpellcastingProfile(
    val ability: AbilityName,
    val type: SpellcastingType,
    val cantripsKnown: Map<Int, Int> = emptyMap(),
    val spellsKnown: Map<Int, Int> = emptyMap(),
    val spellsPrepared: Boolean = false,
    val ritual: Boolean = false,
)

enum class SpellcastingType {
    FULL,
    HALF,
    THIRD,
    PACT,
}
