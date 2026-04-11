package com.ossalali.sessionzero.domain.usecase

import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.AbilityScores
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.model.SkillName
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.domain.rules.GameRules
import com.ossalali.sessionzero.domain.rules.SpeciesData
import javax.inject.Inject

class ComputeDerivedStatsUseCase @Inject constructor() {

    operator fun invoke(character: Character): DerivedStats {
        val scores = character.totalAbilityScores
        val profBonus = GameRules.proficiencyBonus(character.level)
        val classDef = character.className?.let { ClassData.forClass(it) }
        val speciesDef = character.species?.let { SpeciesData.forSpecies(it) }

        // Ability modifiers
        val abilityMods = AbilityName.entries.associateWith { ability ->
            GameRules.abilityModifier(scores[ability])
        }

        val conMod = abilityMods[AbilityName.CON] ?: 0
        val dexMod = abilityMods[AbilityName.DEX] ?: 0

        // HP
        val hitDie = classDef?.hitDie ?: 10
        val maxHP = character.hpOverride ?: GameRules.calculateMaxHP(hitDie, character.level, conMod)

        // AC
        val ac = character.acOverride ?: GameRules.calculateAC(dexMod)

        // Initiative
        val initiative = dexMod

        // Speed
        val speed = speciesDef?.speed ?: 30

        // Hit dice
        val hitDice = "${character.level}d$hitDie"

        // Saving throws
        val proficientSaves = classDef?.savingThrows ?: emptyList()
        val savingThrows = AbilityName.entries.associateWith { ability ->
            GameRules.savingThrowBonus(scores, ability, proficientSaves, profBonus)
        }

        // Skill bonuses
        val skillBonuses = SkillName.entries.associateWith { skill ->
            val abilityScore = scores[skill.ability]
            val isProficient = skill in character.skillProficiencies
            GameRules.skillBonus(abilityScore, isProficient, profBonus)
        }

        // Passive scores
        val passivePerception = GameRules.passiveScore(
            skillBonuses[SkillName.PERCEPTION] ?: 0
        )
        val passiveInvestigation = GameRules.passiveScore(
            skillBonuses[SkillName.INVESTIGATION] ?: 0
        )
        val passiveInsight = GameRules.passiveScore(
            skillBonuses[SkillName.INSIGHT] ?: 0
        )

        // Spellcasting
        val spellcasting = classDef?.spellcasting
        val spellSaveDC = if (spellcasting != null) {
            character.spellSaveDCOverride
                ?: (8 + profBonus + (abilityMods[spellcasting.ability] ?: 0))
        } else null

        val spellAttackBonus = if (spellcasting != null) {
            profBonus + (abilityMods[spellcasting.ability] ?: 0)
        } else null

        return DerivedStats(
            maxHP = maxHP,
            armorClass = ac,
            initiative = initiative,
            speed = speed,
            proficiencyBonus = profBonus,
            hitDice = hitDice,
            spellSaveDC = spellSaveDC,
            spellAttackBonus = spellAttackBonus,
            passivePerception = passivePerception,
            passiveInvestigation = passiveInvestigation,
            passiveInsight = passiveInsight,
            savingThrows = savingThrows,
            skillBonuses = skillBonuses,
            abilityModifiers = abilityMods,
        )
    }
}
