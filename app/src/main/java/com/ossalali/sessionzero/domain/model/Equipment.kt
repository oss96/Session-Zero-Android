package com.ossalali.sessionzero.domain.model

data class EquipmentItem(
    val name: String,
    val quantity: Int = 1,
    val description: String? = null,
)

data class Coins(
    val cp: Int = 0,
    val sp: Int = 0,
    val gp: Int = 0,
    val pp: Int = 0,
)

data class Weapon(
    val name: String,
    val attackBonus: String = "",
    val damage: String = "",
)

data class Armor(
    val name: String,
    val baseAC: Int,
    val addDex: Boolean = true,
    val maxDexBonus: Int? = null,
    val stealthDisadvantage: Boolean = false,
    val strengthRequirement: Int? = null,
    val category: ArmorCategory = ArmorCategory.LIGHT,
)

enum class ArmorCategory(val displayName: String) {
    LIGHT("Light"),
    MEDIUM("Medium"),
    HEAVY("Heavy"),
    SHIELD("Shield"),
}

data class EquipmentPackage(
    val name: String,
    val items: List<EquipmentItem>,
    val coins: Coins = Coins(),
)
