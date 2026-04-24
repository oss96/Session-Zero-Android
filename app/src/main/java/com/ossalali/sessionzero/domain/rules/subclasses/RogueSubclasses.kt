package com.ossalali.sessionzero.domain.rules.subclasses

import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.ClassFeature
import com.ossalali.sessionzero.domain.model.SpellcastingProfile
import com.ossalali.sessionzero.domain.model.SpellcastingType
import com.ossalali.sessionzero.domain.model.SubclassDefinition

object RogueSubclasses {

    val ALL: List<SubclassDefinition> = listOf(
        // ── Arcane Trickster ─────────────────────────────────────────────
        SubclassDefinition(
            name = "Arcane Trickster",
            description = "Augments stealth and agility with spellcasting, gaining Wizard spells focused on Enchantment and Illusion to enhance trickery.",
            spellcasting = SpellcastingProfile(
                ability = AbilityName.INT,
                type = SpellcastingType.THIRD,
                cantripsKnown = mapOf(3 to 3, 10 to 4),
                spellsKnown = mapOf(
                    3 to 3, 4 to 4, 7 to 5, 8 to 6, 10 to 7,
                    11 to 8, 13 to 9, 14 to 10, 16 to 11, 19 to 12, 20 to 13,
                ),
            ),
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Spellcasting",
                        description = "You learn cantrips and spells from the Wizard spell list; INT is your spellcasting ability; the spells must be from the Enchantment or Illusion schools, though some spells can be from any school.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Mage Hand Legerdemain",
                        description = "When you cast Mage Hand, you can make the hand invisible; you can use it to stow or retrieve objects, pick locks, or disarm traps at range; you can use the bonus action to control the hand.",
                        level = 3,
                    ),
                ),
                9 to listOf(
                    ClassFeature(
                        name = "Magical Ambush",
                        description = "If you are hidden from a creature when you cast a spell on it, the creature has disadvantage on any saving throw it makes against the spell on that turn.",
                        level = 9,
                    ),
                ),
                13 to listOf(
                    ClassFeature(
                        name = "Versatile Trickster",
                        description = "As a bonus action, you can designate a creature within 5 feet of your Mage Hand; you have advantage on attack rolls against that creature until the end of your turn.",
                        level = 13,
                    ),
                ),
                17 to listOf(
                    ClassFeature(
                        name = "Spell Thief",
                        description = "When a creature casts a spell that targets you or includes you in its area, you can use your reaction to force the creature to make a saving throw against your spell save DC; on a failure, you negate the spell's effect on you and steal the spell if it is of a level you can cast; you can cast the stolen spell once within 8 hours without expending a spell slot; once per long rest.",
                        level = 17,
                    ),
                ),
            ),
        ),

        // ── Assassin ─────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Assassin",
            description = "Masters the art of death, gaining bonus damage against surprised creatures and proficiency with the Disguise Kit and Poisoner's Kit.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Assassinate",
                        description = "You have advantage on attack rolls against creatures that haven't taken a turn in combat yet; if you hit a creature that is surprised, it takes extra damage equal to your Sneak Attack damage.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Assassin's Tools",
                        description = "You gain proficiency with the Disguise Kit and Poisoner's Kit.",
                        level = 3,
                    ),
                ),
                9 to listOf(
                    ClassFeature(
                        name = "Infiltration Expertise",
                        description = "You can spend 25 GP and 7 days to create a false identity, including documentation, a history, and an established acquaintance; your disguise is so convincing that others believe you to be that person unless given a clear reason to think otherwise.",
                        level = 9,
                    ),
                ),
                13 to listOf(
                    ClassFeature(
                        name = "Envenom Weapons",
                        description = "As a bonus action, apply poison to a weapon or piece of ammunition; the next creature hit by the poisoned item takes 2d6 poison damage and must succeed on a CON save against your Sneak Attack DC or be poisoned for 1 minute; you can use this a number of times equal to your proficiency bonus per long rest.",
                        level = 13,
                    ),
                ),
                17 to listOf(
                    ClassFeature(
                        name = "Death Strike",
                        description = "When you hit a surprised creature, it must make a CON save against a DC equal to 8 + your DEX modifier + your proficiency bonus; on a failure, the attack deals double damage.",
                        level = 17,
                    ),
                ),
            ),
        ),

        // ── Soulknife ────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Soulknife",
            description = "Manifests psionic blades of psychic energy, gaining telepathic communication and enhanced skill checks with Psionic Energy dice.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Psionic Power",
                        description = "You have a pool of Psionic Energy dice, which are d6s; number equals twice your proficiency bonus; regain all on long rest.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Psi-Bolstered Knack",
                        description = "When you fail an ability check using a skill or tool you are proficient with, you can roll a Psionic Energy die and add it to the check, potentially turning failure into success; only expended if the check succeeds.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Psychic Whispers",
                        description = "As an action, choose a number of creatures you can see equal to your proficiency bonus; you establish telepathic communication with each for a number of hours equal to a Psionic Energy die roll; once free per long rest or expend a Psionic Energy die.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Psychic Blades",
                        description = "You can manifest a blade of psychic energy from your free hand; it counts as a simple melee weapon with the Finesse and Thrown properties, 60-foot range; it deals 1d6 psychic damage; after you attack with this blade, you can make a bonus action attack with a second blade dealing 1d4 psychic damage; both blades vanish after hitting or missing.",
                        level = 3,
                    ),
                ),
                9 to listOf(
                    ClassFeature(
                        name = "Soul Blades",
                        description = "Homing Strikes: when you miss with your Psychic Blade, you can roll a Psionic Energy die and add the number to the attack roll; only expended if the attack hits. Psychic Teleportation: as a bonus action, roll a Psionic Energy die and throw a Psychic Blade to an unoccupied space up to 10 times the number rolled in feet away, then teleport to that space.",
                        level = 9,
                    ),
                ),
                13 to listOf(
                    ClassFeature(
                        name = "Psychic Veil",
                        description = "As an action, become invisible along with anything you are wearing or carrying for 1 hour or until you deal damage or force a creature to make a save; once free per long rest or expend a Psionic Energy die.",
                        level = 13,
                    ),
                ),
                17 to listOf(
                    ClassFeature(
                        name = "Rend Mind",
                        description = "When you deal Sneak Attack damage with your Psychic Blades, you can force the target to make a WIS save against a DC equal to 8 + your proficiency bonus + your DEX modifier; on a failure, the target is stunned for 1 minute and can repeat the save at end of each of its turns; once free per long rest or expend three Psionic Energy dice.",
                        level = 17,
                    ),
                ),
            ),
        ),

        // ── Thief ────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Thief",
            description = "Hones the arts of burglary and treasure hunting, gaining the ability to use objects as a Bonus Action and climb unusually fast.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Fast Hands",
                        description = "You can use the Bonus Action granted by Cunning Action to make a Sleight of Hand check, use Thieves' Tools to disarm a trap or open a lock, or take the Use an Object action.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Second-Story Work",
                        description = "Climbing no longer costs you extra movement; when you make a running jump, the distance you cover increases by a number of feet equal to your DEX modifier.",
                        level = 3,
                    ),
                ),
                9 to listOf(
                    ClassFeature(
                        name = "Supreme Sneak",
                        description = "You have advantage on Stealth checks if you move no more than half your speed on the same turn.",
                        level = 9,
                    ),
                ),
                13 to listOf(
                    ClassFeature(
                        name = "Use Magic Device",
                        description = "You can use any magic item regardless of class, race, or level requirements; you can also attune to up to four magic items at once instead of the normal limit of three.",
                        level = 13,
                    ),
                ),
                17 to listOf(
                    ClassFeature(
                        name = "Thief's Reflexes",
                        description = "You can take two turns during the first round of combat; you take your first turn at your normal initiative and your second turn at your initiative minus 10.",
                        level = 17,
                    ),
                ),
            ),
        ),
    )
}
