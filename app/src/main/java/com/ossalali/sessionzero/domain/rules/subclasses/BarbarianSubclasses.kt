package com.ossalali.sessionzero.domain.rules.subclasses

import com.ossalali.sessionzero.domain.model.ClassFeature
import com.ossalali.sessionzero.domain.model.SubclassDefinition

object BarbarianSubclasses {

    val ALL: List<SubclassDefinition> = listOf(
        // ── Berserker ──────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Berserker",
            description = "Channels rage into a violent frenzy, gaining extra attacks and immunity to charm and fear while raging.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Frenzy",
                        description = "While raging, you can make a single weapon attack as a Bonus Action on each of your turns.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Mindless Rage",
                        description = "You can't be charmed or frightened while raging. If you are charmed or frightened when you enter your rage, the effect is suspended for the duration of the rage.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Retaliation",
                        description = "When you take damage from a creature that is within 5 feet of you, you can use your Reaction to make a melee attack against that creature.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Intimidating Presence",
                        description = "As an Action, you can frighten a creature within 30 feet. The target must succeed on a WIS saving throw or be frightened until the end of your next turn.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Wild Heart ─────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Wild Heart",
            description = "Draws power from the animal spirits of nature, gaining a Beast Companion feature and nature-themed rage abilities.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Animal Speaker",
                        description = "You can cast Beast Sense and Speak with Animals as rituals.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Rage of the Wilds",
                        description = "When you enter your rage, you choose a type: Bear (resistance to all damage except Psychic), Eagle (Dash as a Bonus Action and opportunity attacks against you have Disadvantage), or Wolf (allies have Advantage on melee attack rolls against hostile creatures within 5 feet of you).",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Aspect of the Wilds",
                        description = "You gain one of the following: Owl (Darkvision 60 ft. and Advantage on Perception checks in dim light or darkness), Panther (climbing speed equal to your walking speed), or Salmon (swimming speed equal to your walking speed).",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Natural Speaker",
                        description = "You can cast Commune with Nature as a ritual.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Power of the Wilds",
                        description = "While raging, you gain enhanced beast powers: Bear (use Reaction to knock a Large or smaller creature prone when you hit it), Eagle (gain a flying speed equal to your walking speed), or Wolf (knock creatures prone as a Bonus Action after hitting them with a melee attack).",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── World Tree ─────────────────────────────────────────────────────
        SubclassDefinition(
            name = "World Tree",
            description = "Taps into the magic of the World Tree to teleport allies and create protective barriers while raging.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Vitality of the Tree",
                        description = "At the start of each of your turns while raging, you gain temporary Hit Points equal to your CON modifier plus your proficiency bonus, provided you have no temporary Hit Points.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Branches of the Tree",
                        description = "While raging, as a Bonus Action you can teleport a willing creature you can see within 30 feet to an unoccupied space within 5 feet of you, or vice versa. The teleported creature gains temporary Hit Points equal to half your Barbarian level.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Battering Roots",
                        description = "While raging, the ground within 15 feet of you is difficult terrain for your enemies. When you push a creature with an attack, you can push it an extra 5 feet.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Travel Along the Tree",
                        description = "As a Bonus Action while raging, you can teleport up to 60 feet to an unoccupied space you can see. You can bring one willing creature within 5 feet of you along.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Zealot ─────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Zealot",
            description = "Fueled by divine fury, deals extra radiant or necrotic damage and can be resurrected without material components.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Divine Fury",
                        description = "The first creature you hit on each of your turns while raging takes extra 1d6 plus half your Barbarian level in radiant or necrotic damage (your choice).",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Warrior of the Gods",
                        description = "If a spell would have the sole effect of restoring you to life, the caster does not need material components to cast it on you.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Fanatical Focus",
                        description = "If you fail a saving throw while raging, you can reroll it and must use the new roll. You can use this ability once per rage.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Zealous Presence",
                        description = "As a Bonus Action, up to 10 creatures of your choice within 60 feet gain Advantage on attack rolls and saving throws until the start of your next turn. Once per Long Rest.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Rage Beyond Death",
                        description = "While raging, having 0 Hit Points doesn't knock you unconscious. You still make death saving throws, and you die only if you would die while not at 0 HP or if your rage ends while you are at 0 HP.",
                        level = 14,
                    ),
                ),
            ),
        ),
    )
}
