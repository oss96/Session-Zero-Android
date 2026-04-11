package com.ossalali.sessionzero.domain.rules

import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.AbilityScores
import com.ossalali.sessionzero.domain.model.Armor
import com.ossalali.sessionzero.domain.model.ArmorCategory
import kotlin.math.floor
import kotlin.math.min

object GameRules {

    fun abilityModifier(score: Int): Int =
        floor((score - 10) / 2.0).toInt()

    fun proficiencyBonus(level: Int): Int = when {
        level <= 4 -> 2
        level <= 8 -> 3
        level <= 12 -> 4
        level <= 16 -> 5
        else -> 6
    }

    fun calculateMaxHP(
        hitDie: Int,
        level: Int,
        conModifier: Int,
    ): Int {
        val firstLevelHP = hitDie + conModifier
        val subsequentLevelHP = (level - 1) * (hitDie / 2 + 1 + conModifier)
        return maxOf(1, firstLevelHP + subsequentLevelHP)
    }

    fun calculateAC(
        dexModifier: Int,
        equippedArmor: Armor? = null,
        hasShield: Boolean = false,
    ): Int {
        val baseAC = if (equippedArmor != null) {
            val dexBonus = if (equippedArmor.addDex) {
                if (equippedArmor.maxDexBonus != null) {
                    min(dexModifier, equippedArmor.maxDexBonus)
                } else {
                    dexModifier
                }
            } else {
                0
            }
            equippedArmor.baseAC + dexBonus
        } else {
            10 + dexModifier
        }
        return baseAC + if (hasShield) 2 else 0
    }

    fun savingThrowBonus(
        abilityScores: AbilityScores,
        ability: AbilityName,
        proficientSaves: List<AbilityName>,
        proficiencyBonus: Int,
    ): Int {
        val mod = abilityModifier(abilityScores[ability])
        return if (ability in proficientSaves) mod + proficiencyBonus else mod
    }

    fun skillBonus(
        abilityScore: Int,
        isProficient: Boolean,
        proficiencyBonus: Int,
    ): Int {
        val mod = abilityModifier(abilityScore)
        return if (isProficient) mod + proficiencyBonus else mod
    }

    fun passiveScore(skillBonus: Int): Int = 10 + skillBonus
}
