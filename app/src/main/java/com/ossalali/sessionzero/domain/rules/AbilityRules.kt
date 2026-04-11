package com.ossalali.sessionzero.domain.rules

object AbilityRules {

    val STANDARD_ARRAY = listOf(15, 14, 13, 12, 10, 8)

    val POINT_BUY_COSTS = mapOf(
        8 to 0,
        9 to 1,
        10 to 2,
        11 to 3,
        12 to 4,
        13 to 5,
        14 to 7,
        15 to 9,
    )

    const val POINT_BUY_TOTAL = 27
    const val POINT_BUY_MIN = 8
    const val POINT_BUY_MAX = 15

    fun pointBuyCost(score: Int): Int = POINT_BUY_COSTS[score] ?: 0

    fun totalPointBuyCost(scores: List<Int>): Int =
        scores.sumOf { pointBuyCost(it) }

    fun remainingPoints(scores: List<Int>): Int =
        POINT_BUY_TOTAL - totalPointBuyCost(scores)

    fun canIncrease(currentScore: Int, remainingPoints: Int): Boolean {
        if (currentScore >= POINT_BUY_MAX) return false
        val nextCost = pointBuyCost(currentScore + 1) - pointBuyCost(currentScore)
        return nextCost <= remainingPoints
    }

    fun canDecrease(currentScore: Int): Boolean = currentScore > POINT_BUY_MIN

    fun rollAbilityScore(): Int {
        val rolls = (1..4).map { (1..6).random() }.sorted()
        return rolls.drop(1).sum()
    }
}
