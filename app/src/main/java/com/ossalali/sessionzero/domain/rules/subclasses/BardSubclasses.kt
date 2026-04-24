package com.ossalali.sessionzero.domain.rules.subclasses

import com.ossalali.sessionzero.domain.model.ClassFeature
import com.ossalali.sessionzero.domain.model.SubclassDefinition

object BardSubclasses {

    val ALL: List<SubclassDefinition> = listOf(
        // ── Dance ──────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Dance",
            description = "Uses graceful movement to bolster allies and confound enemies, gaining benefits while moving on the battlefield.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Dazzling Footwork",
                        description = "While not wearing armor or using a Shield, your AC equals 10 + DEX modifier + CHA modifier. When you use Bardic Inspiration, you gain a bonus to speed and your movement doesn't provoke opportunity attacks for the rest of that turn.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Inspiring Movement",
                        description = "When an enemy you can see ends its turn within 5 feet of you, you can use your Reaction to move up to half your speed. You can also allow an ally within 30 feet to use their Reaction to move up to half their speed.",
                        level = 6,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Tandem Footwork",
                        description = "When you roll initiative, you and allies within 30 feet each gain a bonus to initiative equal to a roll of your Bardic Inspiration die.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Glamour ────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Glamour",
            description = "Weaves fey magic into performances that charm and enthrall audiences, granting temporary hit points and commanding movement.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Beguiling Magic",
                        description = "You always have Charm Person and Mirror Image prepared. When a target succeeds on a saving throw against one of your spells, the target doesn't know you tried to cast on them.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Mantle of Inspiration",
                        description = "As a Bonus Action, expend a Bardic Inspiration die and choose a number of allies within 60 feet equal to your CHA modifier. Each gains temporary Hit Points equal to twice your Bard level and can use a Reaction to move up to their speed without provoking opportunity attacks.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Mantle of Majesty",
                        description = "As a Bonus Action, cast Command without expending a spell slot for 1 minute while concentrating. Any creature charmed by you automatically fails the saving throw. Once per Long Rest.",
                        level = 6,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Unbreakable Majesty",
                        description = "As a Bonus Action, assume a magically majestic presence for 1 minute. The first time each turn a creature attacks you, the attacker must make a CHA saving throw or the attack misses and the attacker is frightened until the end of its next turn. Once per Long Rest.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Lore ───────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Lore",
            description = "Pursues knowledge from every source, gaining additional skill proficiencies and the ability to use Bardic Inspiration to debuff enemies.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Bonus Proficiencies",
                        description = "You gain proficiency in three skills of your choice.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Cutting Words",
                        description = "When a creature you can see within 60 feet makes an attack roll, ability check, or damage roll, you can use your Reaction to expend a Bardic Inspiration die and subtract the number rolled from the creature's roll.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Magical Discoveries",
                        description = "You learn two spells from any spell list. These count as Bard spells for you. You can replace one of these spells when you gain a Bard level.",
                        level = 6,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Peerless Skill",
                        description = "When you make an ability check or attack roll and fail, you can expend a Bardic Inspiration die and add the number rolled to your check or attack, potentially turning the failure into a success.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Valor ──────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Valor",
            description = "Inspires bravery on the battlefield, gaining medium armor and shield proficiency and granting allies combat benefits with Bardic Inspiration.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Combat Inspiration",
                        description = "A creature with your Bardic Inspiration die can add it to a weapon damage roll or to their AC against one attack as a Reaction.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Martial Training",
                        description = "You gain proficiency with medium armor, Shields, and martial weapons.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Extra Attack",
                        description = "You can attack twice instead of once when you take the Attack action on your turn.",
                        level = 6,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Battle Magic",
                        description = "When you cast a Bard spell that has a casting time of one Action, you can make one weapon attack as a Bonus Action.",
                        level = 14,
                    ),
                ),
            ),
        ),
    )
}
