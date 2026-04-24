package com.ossalali.sessionzero.domain.rules.subclasses

import com.ossalali.sessionzero.domain.model.ClassFeature
import com.ossalali.sessionzero.domain.model.SubclassDefinition

object DruidSubclasses {

    val ALL: List<SubclassDefinition> = listOf(
        // ── Land ───────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Land",
            description = "Draws on the magic of the environment, gaining bonus spells based on a chosen terrain and recovering spell slots during short rests.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Land's Aid",
                        description = "As a Magic action, expend a use of Wild Shape to choose a point within 60 feet. Each creature of your choice in a 10-foot-radius sphere centered on that point must make a CON save or take 2d6 damage and be restrained until the end of your next turn. On success, half damage and not restrained.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Natural Recovery",
                        description = "During a short rest, you can recover expended spell slots with a combined level equal to half your Druid level rounded up. Once per Long Rest.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Natural Stride",
                        description = "Moving through nonmagical difficult terrain costs no extra movement. You can also pass through nonmagical plants without being slowed or taking damage from thorns.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Nature's Ward",
                        description = "You are immune to the poisoned condition and resistant to poison damage. You can't be charmed or frightened by Elementals or Fey.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Nature's Sanctuary",
                        description = "Creatures of the natural world sense your connection. When a Beast, Elemental, Fey, or Plant creature attacks you, it must make a WIS save against your spell save DC. On failure, the creature must choose a different target or the attack misses. On success, the creature is immune for 24 hours.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Moon ───────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Moon",
            description = "Specializes in Wild Shape combat, gaining improved beast forms with more hit points and the ability to transform into more powerful creatures.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Combat Wild Shape",
                        description = "You can expend a spell slot to regain hit points while in Wild Shape form equal to 1d8 per level of the spell slot expended.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Circle Forms",
                        description = "You can use Wild Shape to transform into creatures with a CR as high as 1. This increases as you gain levels: CR 2 at level 6, CR 3 at level 9, CR 4 at level 12, CR 5 at level 15, CR 6 at level 18.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Improved Circle Forms",
                        description = "Your attacks in Wild Shape count as magical for the purpose of overcoming resistance and immunity. Your CR limit increases to 2.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Moonlight Step",
                        description = "You can teleport up to 30 feet to an unoccupied space you can see as a Bonus Action. You regain this use after expending a spell slot of 2nd level or higher as part of casting a spell. Also gain resistance to radiant damage in Wild Shape.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Thousand Forms",
                        description = "You can cast Alter Self at will without expending a spell slot.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Sea ────────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Sea",
            description = "Channels the power of the ocean, gaining aquatic Wild Shape forms and water-themed magical abilities.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Wrath of the Sea",
                        description = "As a Bonus Action, you can crash a wave of spectral water at a creature you can see within 30 feet. The target makes a CON save against your spell save DC or takes 1d6 cold damage and is pushed 5 feet. The damage increases to 2d6 at level 10.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Aquatic Affinity",
                        description = "You gain a swimming speed equal to your walking speed. You can breathe underwater.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Aquatic Shapes",
                        description = "Your Wild Shape can take the form of aquatic creatures. Your Wild Shape attacks deal an extra 1d6 cold damage.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Stormborn",
                        description = "You gain resistance to cold and lightning damage. You gain a flying speed equal to your walking speed when outdoors.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Oceanic Gift",
                        description = "While you are in Wild Shape, allies within 10 feet of you gain the benefit of Water Breathing. When you cast a spell that deals cold damage, one target takes extra cold damage equal to your WIS modifier.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Stars ──────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Stars",
            description = "Draws on the power of starlight, gaining a starry form that can heal allies, attack with radiant bolts, or maintain concentration more easily.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Star Map",
                        description = "You can cast Guidance without expending a spell slot. You always have Guiding Bolt prepared. You can cast Guiding Bolt without expending a spell slot a number of times equal to your proficiency bonus per Long Rest.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Starry Form",
                        description = "As a Bonus Action, expend a use of Wild Shape to take a starry form for 10 minutes. Choose one: Archer — make a ranged spell attack dealing 1d8 + WIS radiant damage as a Bonus Action; Chalice — when you cast a healing spell, another creature within 30 feet regains 1d8 + WIS HP; or Dragon — treat any roll of 9 or lower as a 10 on concentration checks and Intelligence/Wisdom checks.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Cosmic Omen",
                        description = "After a Long Rest, consult your Star Map. If even roll, gain Weal — when an ally within 30 feet makes an attack, save, or check, use Reaction to add 1d6. If odd, gain Woe — when an enemy you can see within 30 feet makes the same, use Reaction to subtract 1d6. Uses equal to proficiency bonus per Long Rest.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Twinkling Constellations",
                        description = "Your Starry Form improves: Archer damage becomes 2d8 + WIS, Chalice healing becomes 2d8 + WIS, Dragon grants a flying speed of 20 feet and hover. You can change your starry form at the start of each turn.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Full of Stars",
                        description = "While in Starry Form, you become partially incorporeal, granting resistance to bludgeoning, piercing, and slashing damage.",
                        level = 14,
                    ),
                ),
            ),
        ),
    )
}
