package com.ossalali.sessionzero.domain.rules

import com.ossalali.sessionzero.domain.model.Armor
import com.ossalali.sessionzero.domain.model.ArmorCategory
import com.ossalali.sessionzero.domain.model.Weapon

object EquipmentData {

    val ALL_WEAPONS: List<Weapon> = listOf(
        // ── Simple Melee Weapons ──
        Weapon(name = "Club", damage = "1d4 bludgeoning"),
        Weapon(name = "Dagger", damage = "1d4 piercing"),
        Weapon(name = "Greatclub", damage = "1d8 bludgeoning"),
        Weapon(name = "Handaxe", damage = "1d6 slashing"),
        Weapon(name = "Javelin", damage = "1d6 piercing"),
        Weapon(name = "Light Hammer", damage = "1d4 bludgeoning"),
        Weapon(name = "Mace", damage = "1d6 bludgeoning"),
        Weapon(name = "Quarterstaff", damage = "1d6 bludgeoning"),
        Weapon(name = "Sickle", damage = "1d4 slashing"),
        Weapon(name = "Spear", damage = "1d6 piercing"),

        // ── Simple Ranged Weapons ──
        Weapon(name = "Light Crossbow", damage = "1d8 piercing"),
        Weapon(name = "Dart", damage = "1d4 piercing"),
        Weapon(name = "Shortbow", damage = "1d6 piercing"),
        Weapon(name = "Sling", damage = "1d4 bludgeoning"),

        // ── Martial Melee Weapons ──
        Weapon(name = "Battleaxe", damage = "1d8 slashing"),
        Weapon(name = "Flail", damage = "1d8 bludgeoning"),
        Weapon(name = "Glaive", damage = "1d10 slashing"),
        Weapon(name = "Greataxe", damage = "1d12 slashing"),
        Weapon(name = "Greatsword", damage = "2d6 slashing"),
        Weapon(name = "Halberd", damage = "1d10 slashing"),
        Weapon(name = "Lance", damage = "1d10 piercing"),
        Weapon(name = "Longsword", damage = "1d8 slashing"),
        Weapon(name = "Maul", damage = "2d6 bludgeoning"),
        Weapon(name = "Morningstar", damage = "1d8 piercing"),
        Weapon(name = "Pike", damage = "1d10 piercing"),
        Weapon(name = "Rapier", damage = "1d8 piercing"),
        Weapon(name = "Scimitar", damage = "1d6 slashing"),
        Weapon(name = "Shortsword", damage = "1d6 piercing"),
        Weapon(name = "Trident", damage = "1d8 piercing"),
        Weapon(name = "War Pick", damage = "1d8 piercing"),
        Weapon(name = "Warhammer", damage = "1d8 bludgeoning"),
        Weapon(name = "Whip", damage = "1d4 slashing"),

        // ── Martial Ranged Weapons ──
        Weapon(name = "Blowgun", damage = "1 piercing"),
        Weapon(name = "Hand Crossbow", damage = "1d6 piercing"),
        Weapon(name = "Heavy Crossbow", damage = "1d10 piercing"),
        Weapon(name = "Longbow", damage = "1d8 piercing"),
        Weapon(name = "Musket", damage = "1d12 piercing"),
        Weapon(name = "Pistol", damage = "1d10 piercing"),
    )

    val ALL_ARMOR: List<Armor> = listOf(
        // ── Light Armor ──
        Armor(
            name = "Padded",
            baseAC = 11,
            addDex = true,
            stealthDisadvantage = true,
            category = ArmorCategory.LIGHT,
        ),
        Armor(
            name = "Leather",
            baseAC = 11,
            addDex = true,
            category = ArmorCategory.LIGHT,
        ),
        Armor(
            name = "Studded Leather",
            baseAC = 12,
            addDex = true,
            category = ArmorCategory.LIGHT,
        ),

        // ── Medium Armor ──
        Armor(
            name = "Hide",
            baseAC = 12,
            addDex = true,
            maxDexBonus = 2,
            category = ArmorCategory.MEDIUM,
        ),
        Armor(
            name = "Chain Shirt",
            baseAC = 13,
            addDex = true,
            maxDexBonus = 2,
            category = ArmorCategory.MEDIUM,
        ),
        Armor(
            name = "Scale Mail",
            baseAC = 14,
            addDex = true,
            maxDexBonus = 2,
            stealthDisadvantage = true,
            category = ArmorCategory.MEDIUM,
        ),
        Armor(
            name = "Breastplate",
            baseAC = 14,
            addDex = true,
            maxDexBonus = 2,
            category = ArmorCategory.MEDIUM,
        ),
        Armor(
            name = "Half Plate",
            baseAC = 15,
            addDex = true,
            maxDexBonus = 2,
            stealthDisadvantage = true,
            category = ArmorCategory.MEDIUM,
        ),

        // ── Heavy Armor ──
        Armor(
            name = "Ring Mail",
            baseAC = 14,
            addDex = false,
            stealthDisadvantage = true,
            category = ArmorCategory.HEAVY,
        ),
        Armor(
            name = "Chain Mail",
            baseAC = 16,
            addDex = false,
            stealthDisadvantage = true,
            strengthRequirement = 13,
            category = ArmorCategory.HEAVY,
        ),
        Armor(
            name = "Splint",
            baseAC = 17,
            addDex = false,
            stealthDisadvantage = true,
            strengthRequirement = 15,
            category = ArmorCategory.HEAVY,
        ),
        Armor(
            name = "Plate",
            baseAC = 18,
            addDex = false,
            stealthDisadvantage = true,
            strengthRequirement = 15,
            category = ArmorCategory.HEAVY,
        ),

        // ── Shield ──
        Armor(
            name = "Shield",
            baseAC = 2,
            addDex = false,
            category = ArmorCategory.SHIELD,
        ),
    )
}
