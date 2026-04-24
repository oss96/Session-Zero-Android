package com.ossalali.sessionzero.domain.rules.subclasses

import com.ossalali.sessionzero.domain.model.ClassFeature
import com.ossalali.sessionzero.domain.model.SubclassDefinition

object RangerSubclasses {

    val ALL: List<SubclassDefinition> = listOf(
        // ── Beast Master ─────────────────────────────────────────────────
        SubclassDefinition(
            name = "Beast Master",
            description = "Forges a mystical bond with a beast companion that fights alongside you, gaining commands to direct it in battle.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Primal Companion",
                        description = "You magically summon a primal beast—choose Beast of the Land, Beast of the Sea, or Beast of the Sky; the beast is friendly and obeys your commands; in combat, it acts during your turn; you can use a bonus action to command it to take the Attack, Dash, Disengage, Dodge, or Help action; if you are incapacitated, the beast acts on its own; the beast's attacks count as magical; the beast vanishes if you die; you can resummon by expending a spell slot of 1st level or higher.",
                        level = 3,
                    ),
                ),
                7 to listOf(
                    ClassFeature(
                        name = "Exceptional Training",
                        description = "When you use a bonus action to command your beast, you can also use it to take the Dash, Disengage, Dodge, or Help action yourself; additionally, your beast's attacks count as magical for overcoming resistance.",
                        level = 7,
                    ),
                ),
                11 to listOf(
                    ClassFeature(
                        name = "Bestial Fury",
                        description = "When you command your beast to take the Attack action, the beast can make two attacks; additionally, when you cast a spell that targets only you, the spell also affects your beast if it is within 30 feet.",
                        level = 11,
                    ),
                ),
                15 to listOf(
                    ClassFeature(
                        name = "Share Spells",
                        description = "When you cast a spell targeting yourself, you can also affect your beast companion if it's within 30 feet of you; your beast's proficiency bonus increases to match your own.",
                        level = 15,
                    ),
                ),
            ),
        ),

        // ── Fey Wanderer ────────────────────────────────────────────────
        SubclassDefinition(
            name = "Fey Wanderer",
            description = "Draws on fey magic to beguile enemies, gaining psychic damage on attacks and the ability to add WIS to CHA checks.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Dreadful Strikes",
                        description = "Your weapons are imbued with fey magic; once per turn when you hit a creature with a weapon, you can deal an extra 1d4 psychic damage; this increases to 1d6 at level 11.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Otherworldly Glamour",
                        description = "You can add your WIS modifier to any CHA check you make; you also gain proficiency in one of these skills: Deception, Performance, or Persuasion.",
                        level = 3,
                    ),
                ),
                7 to listOf(
                    ClassFeature(
                        name = "Beguiling Twist",
                        description = "When you or a creature you can see within 120 feet succeeds on a save against being charmed or frightened, you can use your reaction to force a different creature within 120 feet to make a WIS save; on a failure, the creature is charmed or frightened by you for 1 minute.",
                        level = 7,
                    ),
                ),
                11 to listOf(
                    ClassFeature(
                        name = "Fey Reinforcements",
                        description = "You can cast Summon Fey without a material component; you can also cast Misty Step without expending a spell slot a number of times equal to your WIS modifier per long rest.",
                        level = 11,
                    ),
                ),
                15 to listOf(
                    ClassFeature(
                        name = "Misty Wanderer",
                        description = "When you cast Misty Step, you can bring along one willing creature within 5 feet; that creature teleports to an unoccupied space within 5 feet of your destination.",
                        level = 15,
                    ),
                ),
            ),
        ),

        // ── Gloom Stalker ───────────────────────────────────────────────
        SubclassDefinition(
            name = "Gloom Stalker",
            description = "Ambushes foes in darkness, gaining bonus damage on first-round attacks, darkvision, and invisibility to creatures relying on darkvision.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Dread Ambusher",
                        description = "At the start of your first turn of each combat, your walking speed increases by 10 feet until end of turn; if you take the Attack action, you can make one additional weapon attack; if that extra attack hits, the target takes an extra 1d8 damage of the weapon's type.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Umbral Sight",
                        description = "You gain darkvision out to 60 feet; if you already have darkvision, its range increases by 60 feet; you are invisible to any creature that relies on darkvision to see you in the darkness.",
                        level = 3,
                    ),
                ),
                7 to listOf(
                    ClassFeature(
                        name = "Iron Mind",
                        description = "You gain proficiency in WIS saving throws; if you already have this proficiency, you gain proficiency in INT or CHA saving throws instead.",
                        level = 7,
                    ),
                ),
                11 to listOf(
                    ClassFeature(
                        name = "Stalker's Flurry",
                        description = "When you miss with a weapon attack, you can make another weapon attack as part of the same action; you can use this ability a number of times equal to your WIS modifier per long rest.",
                        level = 11,
                    ),
                ),
                15 to listOf(
                    ClassFeature(
                        name = "Shadowy Dodge",
                        description = "When a creature makes an attack roll against you and doesn't have advantage, you can use your reaction to impose disadvantage on the attack roll; you must use this before knowing whether the attack hits or misses.",
                        level = 15,
                    ),
                ),
            ),
        ),

        // ── Hunter ──────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Hunter",
            description = "Trains to take on the most dangerous threats, gaining abilities to deal extra damage or defend against multiple attackers.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Hunter's Prey",
                        description = "Choose one: Colossus Slayer—once per turn deal extra 1d8 damage to a creature below its HP max; Giant Killer—when a Large or larger creature within 5 feet attacks you, use reaction to attack it; Horde Breaker—once per turn make an additional attack against a different creature within 5 feet of original target.",
                        level = 3,
                    ),
                ),
                7 to listOf(
                    ClassFeature(
                        name = "Defensive Tactics",
                        description = "Choose one: Escape the Horde—opportunity attacks against you have disadvantage; Multiattack Defense—when a creature hits you, gain +4 AC against subsequent attacks from that creature until the start of your next turn; Steel Will—you have advantage on saving throws against being frightened.",
                        level = 7,
                    ),
                ),
                11 to listOf(
                    ClassFeature(
                        name = "Multiattack",
                        description = "Choose one: Volley—as an action, make a ranged attack against any number of creatures within 10 feet of a point you can see within your weapon's range, with separate attack rolls; Whirlwind Attack—as an action, make a melee attack against any number of creatures within 5 feet, with separate attack rolls.",
                        level = 11,
                    ),
                ),
                15 to listOf(
                    ClassFeature(
                        name = "Superior Hunter's Defense",
                        description = "Choose one: Evasion—when subjected to a DEX save for half damage, take no damage on success and half on failure; Stand Against the Tide—when a hostile creature misses you with a melee attack, you can use your reaction to force it to repeat the attack against another creature within 5 feet; Uncanny Dodge—when an attacker you can see hits you, use reaction to halve the damage.",
                        level = 15,
                    ),
                ),
            ),
        ),
    )
}
