package com.ossalali.sessionzero.domain.model

enum class AbilityName {
    STR, DEX, CON, INT, WIS, CHA
}

enum class SkillName(val displayName: String, val ability: AbilityName) {
    ACROBATICS("Acrobatics", AbilityName.DEX),
    ANIMAL_HANDLING("Animal Handling", AbilityName.WIS),
    ARCANA("Arcana", AbilityName.INT),
    ATHLETICS("Athletics", AbilityName.STR),
    DECEPTION("Deception", AbilityName.CHA),
    HISTORY("History", AbilityName.INT),
    INSIGHT("Insight", AbilityName.WIS),
    INTIMIDATION("Intimidation", AbilityName.CHA),
    INVESTIGATION("Investigation", AbilityName.INT),
    MEDICINE("Medicine", AbilityName.WIS),
    NATURE("Nature", AbilityName.INT),
    PERCEPTION("Perception", AbilityName.WIS),
    PERFORMANCE("Performance", AbilityName.CHA),
    PERSUASION("Persuasion", AbilityName.CHA),
    RELIGION("Religion", AbilityName.INT),
    SLEIGHT_OF_HAND("Sleight of Hand", AbilityName.DEX),
    STEALTH("Stealth", AbilityName.DEX),
    SURVIVAL("Survival", AbilityName.WIS),
}

enum class ClassName(val displayName: String) {
    BARBARIAN("Barbarian"),
    BARD("Bard"),
    CLERIC("Cleric"),
    DRUID("Druid"),
    FIGHTER("Fighter"),
    MONK("Monk"),
    PALADIN("Paladin"),
    RANGER("Ranger"),
    ROGUE("Rogue"),
    SORCERER("Sorcerer"),
    WARLOCK("Warlock"),
    WIZARD("Wizard"),
}

enum class SpeciesName(val displayName: String) {
    AASIMAR("Aasimar"),
    DRAGONBORN("Dragonborn"),
    DWARF("Dwarf"),
    ELF("Elf"),
    GNOME("Gnome"),
    GOLIATH("Goliath"),
    HALFLING("Halfling"),
    HUMAN("Human"),
    ORC("Orc"),
    TIEFLING("Tiefling"),
}

enum class BackgroundName(val displayName: String) {
    ACOLYTE("Acolyte"),
    ARTISAN("Artisan"),
    CHARLATAN("Charlatan"),
    CRIMINAL("Criminal"),
    ENTERTAINER("Entertainer"),
    FARMER("Farmer"),
    GUARD("Guard"),
    GUIDE("Guide"),
    HERMIT("Hermit"),
    MERCHANT("Merchant"),
    NOBLE("Noble"),
    SAGE("Sage"),
    SAILOR("Sailor"),
    SCRIBE("Scribe"),
    SOLDIER("Soldier"),
    WAYFARER("Wayfarer"),
}

enum class Alignment(val displayName: String) {
    LAWFUL_GOOD("Lawful Good"),
    NEUTRAL_GOOD("Neutral Good"),
    CHAOTIC_GOOD("Chaotic Good"),
    LAWFUL_NEUTRAL("Lawful Neutral"),
    TRUE_NEUTRAL("True Neutral"),
    CHAOTIC_NEUTRAL("Chaotic Neutral"),
    LAWFUL_EVIL("Lawful Evil"),
    NEUTRAL_EVIL("Neutral Evil"),
    CHAOTIC_EVIL("Chaotic Evil"),
}
