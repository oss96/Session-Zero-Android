package com.ossalali.sessionzero.domain.rules

data class FeatDefinition(
    val name: String,
    val description: String,
    val prerequisite: String? = null,
)

object FeatData {

    val ORIGIN_FEATS: List<FeatDefinition> = listOf(
        FeatDefinition(
            name = "Alert",
            description = "Always on the lookout for danger. You gain +2 to Initiative and can't be Surprised.",
        ),
        FeatDefinition(
            name = "Crafter",
            description = "You are adept at crafting things. You gain proficiency with 3 Artisan's Tools of your choice and can craft items at a 20% discount.",
        ),
        FeatDefinition(
            name = "Healer",
            description = "You have the training and intuition to administer first aid. Using a Healer's Kit, you can restore 2d6+2+target's level HP (once per rest).",
        ),
        FeatDefinition(
            name = "Lucky",
            description = "You have inexplicable luck. You gain 3 Luck Points per long rest; spend one to gain Advantage on a d20 roll or impose Disadvantage on an attack against you.",
        ),
        FeatDefinition(
            name = "Magic Initiate (Cleric)",
            description = "You learn 2 Cleric cantrips and one 1st-level Cleric spell (cast once per long rest or with spell slots). WIS is your spellcasting ability.",
        ),
        FeatDefinition(
            name = "Magic Initiate (Druid)",
            description = "You learn 2 Druid cantrips and one 1st-level Druid spell (cast once per long rest or with spell slots). WIS is your spellcasting ability.",
        ),
        FeatDefinition(
            name = "Magic Initiate (Wizard)",
            description = "You learn 2 Wizard cantrips and one 1st-level Wizard spell (cast once per long rest or with spell slots). INT is your spellcasting ability.",
        ),
        FeatDefinition(
            name = "Musician",
            description = "You are a practiced musician. You gain proficiency with 3 Musical Instruments and can play to give allies Heroic Inspiration after a Short/Long Rest.",
        ),
        FeatDefinition(
            name = "Savage Attacker",
            description = "You've trained to deal particularly damaging strikes. Once per turn when you hit with a weapon, you can roll damage dice twice and use the higher result.",
        ),
        FeatDefinition(
            name = "Skilled",
            description = "You gain proficiency in any combination of 3 Skills or Tools of your choice.",
        ),
        FeatDefinition(
            name = "Tavern Brawler",
            description = "Accustomed to brawling. Your Unarmed Strikes deal 1d4+STR, you can push 5ft on hit, and you gain proficiency with Improvised Weapons.",
        ),
        FeatDefinition(
            name = "Tough",
            description = "You are more resilient than normal. Your HP maximum increases by 2 for every level you have.",
        ),
    )
}
