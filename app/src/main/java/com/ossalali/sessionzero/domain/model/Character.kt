package com.ossalali.sessionzero.domain.model

import java.util.UUID

data class Character(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val pronouns: String = "",
    val level: Int = 1,
    val xp: Int = 0,
    val alignment: Alignment? = null,
    val className: ClassName? = null,
    val subclass: String? = null,
    val species: SpeciesName? = null,
    val speciesLineage: String? = null,
    val background: BackgroundName? = null,
    val abilityScoreBonuses: Map<AbilityName, Int> = emptyMap(),
    val originFeat: String? = null,
    val baseStr: Int = 10,
    val baseDex: Int = 10,
    val baseCon: Int = 10,
    val baseInt: Int = 10,
    val baseWis: Int = 10,
    val baseCha: Int = 10,
    val abilityScoreMethod: String? = null,
    val skillProficiencies: List<SkillName> = emptyList(),
    val equipmentChoice: String? = null,
    val coins: Coins = Coins(),
    val equipment: List<EquipmentItem> = emptyList(),
    val weapons: List<Weapon> = emptyList(),
    val personalityTraits: String = "",
    val ideals: String = "",
    val bonds: String = "",
    val flaws: String = "",
    val appearance: Appearance = Appearance(),
    val backstory: String = "",
    val alliesAndOrganizations: String = "",
    val additionalNotes: String = "",
    val portraitUrl: String = "",
    val feats: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val knownCantrips: List<String> = emptyList(),
    val preparedSpells: List<String> = emptyList(),
    val hpOverride: Int? = null,
    val acOverride: Int? = null,
    val spellSaveDCOverride: Int? = null,
    val createdAt: String = "",
    val updatedAt: String = "",
) {
    val baseAbilityScores: AbilityScores
        get() = AbilityScores(
            str = baseStr,
            dex = baseDex,
            con = baseCon,
            int = baseInt,
            wis = baseWis,
            cha = baseCha,
        )

    val totalAbilityScores: AbilityScores
        get() {
            var scores = baseAbilityScores
            for ((ability, bonus) in abilityScoreBonuses) {
                scores = scores.with(ability, scores[ability] + bonus)
            }
            return scores
        }

    companion object {
        fun empty(): Character = Character()
    }
}
