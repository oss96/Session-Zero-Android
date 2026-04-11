package com.ossalali.sessionzero.domain.rules

object SpellSlotTables {

    // Full caster: Bard, Cleric, Druid, Sorcerer, Wizard
    // Index 0 = level 1, value = list of slots per spell level (1st, 2nd, 3rd, ...)
    val FULL_CASTER: Map<Int, List<Int>> = mapOf(
        1 to listOf(2),
        2 to listOf(3),
        3 to listOf(4, 2),
        4 to listOf(4, 3),
        5 to listOf(4, 3, 2),
        6 to listOf(4, 3, 3),
        7 to listOf(4, 3, 3, 1),
        8 to listOf(4, 3, 3, 2),
        9 to listOf(4, 3, 3, 3, 1),
        10 to listOf(4, 3, 3, 3, 2),
        11 to listOf(4, 3, 3, 3, 2, 1),
        12 to listOf(4, 3, 3, 3, 2, 1),
        13 to listOf(4, 3, 3, 3, 2, 1, 1),
        14 to listOf(4, 3, 3, 3, 2, 1, 1),
        15 to listOf(4, 3, 3, 3, 2, 1, 1, 1),
        16 to listOf(4, 3, 3, 3, 2, 1, 1, 1),
        17 to listOf(4, 3, 3, 3, 2, 1, 1, 1, 1),
        18 to listOf(4, 3, 3, 3, 3, 1, 1, 1, 1),
        19 to listOf(4, 3, 3, 3, 3, 2, 1, 1, 1),
        20 to listOf(4, 3, 3, 3, 3, 2, 2, 1, 1),
    )

    // Half caster: Paladin, Ranger
    val HALF_CASTER: Map<Int, List<Int>> = mapOf(
        1 to emptyList(),
        2 to listOf(2),
        3 to listOf(3),
        4 to listOf(3),
        5 to listOf(4, 2),
        6 to listOf(4, 2),
        7 to listOf(4, 3),
        8 to listOf(4, 3),
        9 to listOf(4, 3, 2),
        10 to listOf(4, 3, 2),
        11 to listOf(4, 3, 3),
        12 to listOf(4, 3, 3),
        13 to listOf(4, 3, 3, 1),
        14 to listOf(4, 3, 3, 1),
        15 to listOf(4, 3, 3, 2),
        16 to listOf(4, 3, 3, 2),
        17 to listOf(4, 3, 3, 3, 1),
        18 to listOf(4, 3, 3, 3, 1),
        19 to listOf(4, 3, 3, 3, 2),
        20 to listOf(4, 3, 3, 3, 2),
    )

    // Third caster: Eldritch Knight (Fighter), Arcane Trickster (Rogue)
    val THIRD_CASTER: Map<Int, List<Int>> = mapOf(
        1 to emptyList(),
        2 to emptyList(),
        3 to listOf(2),
        4 to listOf(3),
        5 to listOf(3),
        6 to listOf(3),
        7 to listOf(4, 2),
        8 to listOf(4, 2),
        9 to listOf(4, 2),
        10 to listOf(4, 3),
        11 to listOf(4, 3),
        12 to listOf(4, 3),
        13 to listOf(4, 3, 2),
        14 to listOf(4, 3, 2),
        15 to listOf(4, 3, 2),
        16 to listOf(4, 3, 3),
        17 to listOf(4, 3, 3),
        18 to listOf(4, 3, 3),
        19 to listOf(4, 3, 3, 1),
        20 to listOf(4, 3, 3, 1),
    )

    // Pact caster: Warlock (slots + level)
    data class PactSlots(val slots: Int, val slotLevel: Int)

    val PACT_CASTER: Map<Int, PactSlots> = mapOf(
        1 to PactSlots(1, 1),
        2 to PactSlots(2, 1),
        3 to PactSlots(2, 2),
        4 to PactSlots(2, 2),
        5 to PactSlots(2, 3),
        6 to PactSlots(2, 3),
        7 to PactSlots(2, 4),
        8 to PactSlots(2, 4),
        9 to PactSlots(2, 5),
        10 to PactSlots(2, 5),
        11 to PactSlots(3, 5),
        12 to PactSlots(3, 5),
        13 to PactSlots(3, 5),
        14 to PactSlots(3, 5),
        15 to PactSlots(3, 5),
        16 to PactSlots(3, 5),
        17 to PactSlots(4, 5),
        18 to PactSlots(4, 5),
        19 to PactSlots(4, 5),
        20 to PactSlots(4, 5),
    )

    fun getSpellSlots(
        type: com.ossalali.sessionzero.domain.model.SpellcastingType,
        level: Int,
    ): List<Int> = when (type) {
        com.ossalali.sessionzero.domain.model.SpellcastingType.FULL -> FULL_CASTER[level] ?: emptyList()
        com.ossalali.sessionzero.domain.model.SpellcastingType.HALF -> HALF_CASTER[level] ?: emptyList()
        com.ossalali.sessionzero.domain.model.SpellcastingType.THIRD -> THIRD_CASTER[level] ?: emptyList()
        com.ossalali.sessionzero.domain.model.SpellcastingType.PACT -> {
            val pact = PACT_CASTER[level] ?: return emptyList()
            List(pact.slotLevel) { i -> if (i < pact.slotLevel) pact.slots else 0 }
        }
    }
}
