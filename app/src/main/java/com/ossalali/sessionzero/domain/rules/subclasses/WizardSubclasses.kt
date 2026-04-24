package com.ossalali.sessionzero.domain.rules.subclasses

import com.ossalali.sessionzero.domain.model.ClassFeature
import com.ossalali.sessionzero.domain.model.SubclassDefinition

object WizardSubclasses {

    val ALL: List<SubclassDefinition> = listOf(
        // ── Abjuration ────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Abjuration",
            description = "Specializes in protective magic, gaining the ability to create magical wards that absorb damage for you and your allies.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Arcane Ward",
                        description = "When you cast an Abjuration spell of 1st level or higher, you can create a magical ward on yourself. The ward has HP equal to twice your Wizard level + your INT modifier. Whenever you take damage, the ward takes the damage instead. If the ward is reduced to 0 HP, you take any remaining damage. When you cast an Abjuration spell of 1st level or higher while the ward is active, it regains HP equal to twice the spell's level.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Projected Ward",
                        description = "When a creature you can see within 30 feet takes damage, you can use your Reaction to have your Arcane Ward absorb the damage instead. If the ward is reduced to 0 HP, the protected creature takes any remaining damage.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Spell Breaker",
                        description = "When you cast Dispel Magic or Counterspell, you add your proficiency bonus to any ability check you make as part of the spell. When you successfully end a spell with Dispel Magic, the target regains HP equal to your Wizard level.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Spell Resistance",
                        description = "You have Advantage on saving throws against spells. You have resistance to the damage of spells.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Divination ────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Divination",
            description = "Masters the art of seeing the future, gaining Portent dice that can replace any attack roll, saving throw, or ability check.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Portent",
                        description = "After finishing a Long Rest, roll two d20s and record the numbers. You can replace any attack roll, saving throw, or ability check made by you or a creature you can see with one of these Portent rolls. You must choose to do so before the roll. Each Portent roll can be used only once.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Expert Divination",
                        description = "When you cast a Divination spell of 2nd level or higher using a spell slot, you regain one expended spell slot. The slot must be of a level lower than the spell you cast and can't be higher than 5th level.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "The Third Eye",
                        description = "As a Bonus Action, gain one of the following benefits until you are incapacitated or take a Short or Long Rest: Darkvision out to 120 feet, see into the Ethereal Plane within 60 feet, read any language, or see invisible creatures and objects within 10 feet. Uses equal to your proficiency bonus per Long Rest.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Greater Portent",
                        description = "You roll three d20s for your Portent feature instead of two.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Evocation ─────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Evocation",
            description = "Focuses on powerful offensive magic, gaining the ability to shape area spells around allies and maximize damage rolls.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Sculpt Spells",
                        description = "When you cast an Evocation spell that affects other creatures you can see, you can choose a number of them equal to 1 + the spell's level. The chosen creatures automatically succeed on their saving throw against the spell and take no damage if they would normally take half damage on a successful save.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Potent Cantrip",
                        description = "When a creature succeeds on a saving throw against your cantrip, the creature takes half the cantrip's damage but suffers no other effect.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Empowered Evocation",
                        description = "You can add your INT modifier to one damage roll of any Evocation spell you cast.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Overchannel",
                        description = "When you cast a Wizard spell of 1st through 5th level that deals damage, you can deal maximum damage with that spell. The first time you do so without damage, you take 2d12 necrotic damage per spell level. Each subsequent time before finishing a Long Rest, the necrotic damage increases by 1d12 per spell level. This damage ignores resistance and immunity.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Illusion ──────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Illusion",
            description = "Specializes in creating convincing illusions, gaining the ability to change illusions after casting and eventually make them partially real.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Improved Minor Illusion",
                        description = "You learn the Minor Illusion cantrip. When you cast it, you can create both a sound and an image with a single casting. You also have Advantage on saves against Illusion spells.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Malleable Illusions",
                        description = "When you cast an Illusion spell that has a duration of 1 minute or longer, you can use an Action to change the nature of the illusion, provided the change is within the spell's parameters.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Illusory Self",
                        description = "When a creature makes an attack roll against you, you can use your Reaction to create an illusory duplicate that takes the attack. The attack automatically misses you. The duplicate then vanishes. Uses equal to your proficiency bonus per Long Rest.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Illusory Reality",
                        description = "When you cast an Illusion spell of 1st level or higher, you can choose one inanimate, nonmagical object that is part of the illusion and make it real for 1 minute. The object can't deal damage or directly harm anyone. The object disappears if it leaves the spell's area.",
                        level = 14,
                    ),
                ),
            ),
        ),
    )
}
