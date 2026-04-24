package com.ossalali.sessionzero.ui.preview

import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.Alignment
import com.ossalali.sessionzero.domain.model.Appearance
import com.ossalali.sessionzero.domain.model.BackgroundName
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.ClassName
import com.ossalali.sessionzero.domain.model.Coins
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.model.EquipmentItem
import com.ossalali.sessionzero.domain.model.SkillName
import com.ossalali.sessionzero.domain.model.SpeciesName
import com.ossalali.sessionzero.domain.model.Weapon

object PreviewData {
    val sampleCharacter = Character(
        id = "preview-1",
        name = "Thorn Ironforge",
        pronouns = "he/him",
        level = 5,
        xp = 6500,
        alignment = Alignment.CHAOTIC_GOOD,
        className = ClassName.FIGHTER,
        subclass = "Champion",
        species = SpeciesName.DWARF,
        speciesLineage = null,
        background = BackgroundName.SOLDIER,
        abilityScoreBonuses = mapOf(AbilityName.STR to 2, AbilityName.CON to 1),
        originFeat = "Savage Attacker",
        baseStr = 16,
        baseDex = 14,
        baseCon = 15,
        baseInt = 10,
        baseWis = 12,
        baseCha = 8,
        abilityScoreMethod = "standardArray",
        skillProficiencies = listOf(
            SkillName.ATHLETICS, SkillName.INTIMIDATION,
            SkillName.PERCEPTION, SkillName.SURVIVAL,
        ),
        equipmentChoice = "package",
        coins = Coins(cp = 0, sp = 5, gp = 15, pp = 0),
        equipment = listOf(
            EquipmentItem("Chain Mail", 1),
            EquipmentItem("Shield", 1),
            EquipmentItem("Longsword", 1),
            EquipmentItem("Handaxe", 2),
            EquipmentItem("Explorer's Pack", 1),
        ),
        weapons = listOf(
            Weapon("Longsword", "+6", "1d8+4 slashing"),
            Weapon("Handaxe", "+6", "1d6+4 slashing"),
        ),
        personalityTraits = "I can stare down a hell hound without flinching.",
        ideals = "Our lot is to lay down our lives in defense of others.",
        bonds = "I fight for those who cannot fight for themselves.",
        flaws = "I have little respect for anyone who is not a proven warrior.",
        appearance = Appearance(
            age = "85",
            height = "137",
            heightUnit = "m",
            weight = "82",
            weightUnit = "kg",
            eyes = "Amber",
            skin = "Tan",
            hair = "Black, braided",
        ),
        backstory = "A veteran of the border wars, Thorn left military service after a pyrrhic victory that cost him his regiment.",
        alliesAndOrganizations = "The Iron Wolves mercenary company",
        feats = listOf("Savage Attacker"),
        languages = listOf("Common", "Dwarvish"),
        createdAt = "2026-04-10T12:00:00Z",
        updatedAt = "2026-04-11T08:30:00Z",
    )

    val sampleDerivedStats = DerivedStats(
        maxHP = 44,
        armorClass = 18,
        initiative = 2,
        speed = 30,
        proficiencyBonus = 3,
        hitDice = "5d10",
        passivePerception = 14,
        passiveInvestigation = 10,
        passiveInsight = 11,
        savingThrows = mapOf(
            AbilityName.STR to 7, AbilityName.DEX to 2,
            AbilityName.CON to 5, AbilityName.INT to 0,
            AbilityName.WIS to 1, AbilityName.CHA to -1,
        ),
        skillBonuses = mapOf(
            SkillName.ACROBATICS to 2, SkillName.ANIMAL_HANDLING to 1,
            SkillName.ARCANA to 0, SkillName.ATHLETICS to 7,
            SkillName.DECEPTION to -1, SkillName.HISTORY to 0,
            SkillName.INSIGHT to 1, SkillName.INTIMIDATION to 2,
            SkillName.INVESTIGATION to 0, SkillName.MEDICINE to 1,
            SkillName.NATURE to 0, SkillName.PERCEPTION to 4,
            SkillName.PERFORMANCE to -1, SkillName.PERSUASION to -1,
            SkillName.RELIGION to 0, SkillName.SLEIGHT_OF_HAND to 2,
            SkillName.STEALTH to 2, SkillName.SURVIVAL to 4,
        ),
        abilityModifiers = mapOf(
            AbilityName.STR to 4, AbilityName.DEX to 2,
            AbilityName.CON to 2, AbilityName.INT to 0,
            AbilityName.WIS to 1, AbilityName.CHA to -1,
        ),
    )

    val sampleCasterCharacter = sampleCharacter.copy(
        id = "preview-2",
        name = "Elara Nightwhisper",
        pronouns = "she/her",
        level = 7,
        className = ClassName.WIZARD,
        subclass = "Evocation",
        species = SpeciesName.ELF,
        speciesLineage = "High Elf",
        background = BackgroundName.SAGE,
        alignment = Alignment.NEUTRAL_GOOD,
        baseStr = 8,
        baseDex = 14,
        baseCon = 13,
        baseInt = 16,
        baseWis = 12,
        baseCha = 10,
        abilityScoreBonuses = mapOf(AbilityName.INT to 2, AbilityName.DEX to 1),
        originFeat = "Magic Initiate (Wizard)",
        skillProficiencies = listOf(SkillName.ARCANA, SkillName.HISTORY, SkillName.INVESTIGATION),
        knownCantrips = listOf("Fire Bolt", "Prestidigitation", "Mage Hand", "Light"),
        preparedSpells = listOf("Shield", "Magic Missile", "Fireball", "Counterspell", "Fly"),
        weapons = listOf(Weapon("Quarterstaff", "+1", "1d6-1 bludgeoning")),
        equipment = listOf(
            EquipmentItem("Spellbook", 1),
            EquipmentItem("Component Pouch", 1),
            EquipmentItem("Scholar's Pack", 1),
        ),
    )

    val sampleCasterDerivedStats = DerivedStats(
        maxHP = 38,
        armorClass = 12,
        initiative = 3,
        speed = 30,
        proficiencyBonus = 3,
        hitDice = "7d6",
        spellSaveDC = 16,
        spellAttackBonus = 8,
        passivePerception = 11,
        passiveInvestigation = 16,
        passiveInsight = 11,
        savingThrows = mapOf(
            AbilityName.STR to -1, AbilityName.DEX to 3,
            AbilityName.CON to 1, AbilityName.INT to 8,
            AbilityName.WIS to 4, AbilityName.CHA to 0,
        ),
        skillBonuses = SkillName.entries.associateWith { 0 },
        abilityModifiers = mapOf(
            AbilityName.STR to -1, AbilityName.DEX to 3,
            AbilityName.CON to 1, AbilityName.INT to 5,
            AbilityName.WIS to 1, AbilityName.CHA to 0,
        ),
    )

    val emptyCharacter = Character.empty()

    val emptyDerivedStats = DerivedStats()
}
