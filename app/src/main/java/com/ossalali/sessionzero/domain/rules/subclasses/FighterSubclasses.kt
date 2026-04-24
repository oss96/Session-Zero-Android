package com.ossalali.sessionzero.domain.rules.subclasses

import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.ClassFeature
import com.ossalali.sessionzero.domain.model.SpellcastingProfile
import com.ossalali.sessionzero.domain.model.SpellcastingType
import com.ossalali.sessionzero.domain.model.SubclassDefinition

object FighterSubclasses {

    val ALL: List<SubclassDefinition> = listOf(
        // ── Battle Master ─────────────────────────────────────────────────
        SubclassDefinition(
            name = "Battle Master",
            description = "Employs martial techniques called maneuvers, gaining superiority dice to fuel special combat moves like Riposte, Trip Attack, and Disarming Attack.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Combat Superiority",
                        description = "You learn three maneuvers from the maneuver list and gain four d8 Superiority Dice that fuel your maneuvers. You regain all expended Superiority Dice on a short or long rest.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Student of War",
                        description = "You gain proficiency with one type of artisan's tools of your choice.",
                        level = 3,
                    ),
                ),
                7 to listOf(
                    ClassFeature(
                        name = "Know Your Enemy",
                        description = "As a bonus action, choose a creature you can see within 30 feet. You learn whether the creature is your equal, superior, or inferior in two of the following: STR, DEX, CON, AC, current HP, total class levels, or Fighter class levels.",
                        level = 7,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Improved Combat Superiority",
                        description = "Your Superiority Dice become d10s. You also learn two additional maneuvers.",
                        level = 10,
                    ),
                ),
                15 to listOf(
                    ClassFeature(
                        name = "Relentless",
                        description = "When you roll initiative and have no Superiority Dice remaining, you regain one Superiority Die.",
                        level = 15,
                    ),
                ),
                18 to listOf(
                    ClassFeature(
                        name = "Ultimate Combat Superiority",
                        description = "Your Superiority Dice become d12s. You also learn two additional maneuvers.",
                        level = 18,
                    ),
                ),
            ),
        ),

        // ── Champion ──────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Champion",
            description = "Focuses on raw physical power and athletic prowess, improving critical hit range and gaining remarkable athleticism.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Improved Critical",
                        description = "Your weapon attacks score a critical hit on a roll of 19 or 20.",
                        level = 3,
                    ),
                ),
                7 to listOf(
                    ClassFeature(
                        name = "Remarkable Athlete",
                        description = "Thanks to your training, you have Advantage on Initiative rolls and Strength (Athletics) checks.",
                        level = 7,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Heroic Warrior",
                        description = "When you have no uses of Indomitable remaining, you can use your Second Wind to regain a use of Indomitable instead of regaining HP.",
                        level = 10,
                    ),
                ),
                15 to listOf(
                    ClassFeature(
                        name = "Superior Critical",
                        description = "Your weapon attacks score a critical hit on a roll of 18, 19, or 20.",
                        level = 15,
                    ),
                ),
                18 to listOf(
                    ClassFeature(
                        name = "Survivor",
                        description = "At the start of each of your turns, you regain hit points equal to 5 + your CON modifier if you have no more than half your HP remaining and you have at least 1 HP.",
                        level = 18,
                    ),
                ),
            ),
        ),

        // ── Eldritch Knight ───────────────────────────────────────────────
        SubclassDefinition(
            name = "Eldritch Knight",
            description = "Combines martial prowess with arcane magic, gaining access to Wizard spells with a focus on Abjuration and Evocation.",
            spellcasting = SpellcastingProfile(
                ability = AbilityName.INT,
                type = SpellcastingType.THIRD,
                cantripsKnown = mapOf(3 to 2, 10 to 3),
                spellsKnown = mapOf(
                    3 to 3, 4 to 4, 7 to 5, 8 to 6, 10 to 7,
                    11 to 8, 13 to 9, 14 to 10, 16 to 11, 19 to 12, 20 to 13,
                ),
            ),
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Spellcasting",
                        description = "You learn cantrips and spells from the Wizard spell list, focusing on Abjuration and Evocation. INT is your spellcasting ability.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "War Bond",
                        description = "You learn a ritual to bond with a weapon over 1 hour. You can't be disarmed of a bonded weapon and can summon it to your hand as a bonus action. You can bond with up to two weapons.",
                        level = 3,
                    ),
                ),
                7 to listOf(
                    ClassFeature(
                        name = "War Magic",
                        description = "When you cast a cantrip as your action, you can make one weapon attack as a bonus action.",
                        level = 7,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Eldritch Strike",
                        description = "When you hit a creature with a weapon attack, the creature has disadvantage on the next saving throw it makes against a spell you cast before the end of your next turn.",
                        level = 10,
                    ),
                ),
                15 to listOf(
                    ClassFeature(
                        name = "Arcane Charge",
                        description = "When you use Action Surge, you can teleport up to 30 feet to an unoccupied space you can see. You can teleport before or after the additional action.",
                        level = 15,
                    ),
                ),
                18 to listOf(
                    ClassFeature(
                        name = "Improved War Magic",
                        description = "When you cast a spell as your action, you can make one weapon attack as a bonus action.",
                        level = 18,
                    ),
                ),
            ),
        ),

        // ── Psi Warrior ──────────────────────────────────────────────────
        SubclassDefinition(
            name = "Psi Warrior",
            description = "Awakens psionic power within, using telekinetic force to protect allies and strike enemies with psychic blades.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Psionic Power",
                        description = "You have a pool of Psionic Energy dice, which are d6s. The number equals twice your proficiency bonus. You regain all expended dice on a long rest.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Protective Field",
                        description = "When you or a creature within 30 feet takes damage, you can use your reaction to expend a Psionic Energy die and reduce the damage by the amount rolled plus your INT modifier.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Psionic Strike",
                        description = "Once per turn when you hit with a weapon attack, you can expend a Psionic Energy die to deal extra force damage equal to the die roll plus your INT modifier.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Telekinetic Movement",
                        description = "As an action, move a Large or smaller loose object or willing creature up to 30 feet to an unoccupied space. If the target is Tiny, you can move it to or from your hand. Once free per short rest, or spend a Psionic Energy die.",
                        level = 3,
                    ),
                ),
                7 to listOf(
                    ClassFeature(
                        name = "Telekinetic Adept",
                        description = "Psi-Powered Leap: as a bonus action, gain a flying speed equal to twice your walking speed until end of turn; once free per short rest or spend a Psionic Energy die. Telekinetic Thrust: when you deal damage with Psionic Strike, you can force the target to make a STR save or be knocked prone or pushed 10 feet.",
                        level = 7,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Guarded Mind",
                        description = "You have resistance to psychic damage. If you start your turn charmed or frightened, you can expend a Psionic Energy die to end every effect causing those conditions.",
                        level = 10,
                    ),
                ),
                15 to listOf(
                    ClassFeature(
                        name = "Bulwark of Force",
                        description = "As a bonus action, choose up to your INT modifier creatures within 30 feet including yourself. Each gains half cover for 1 minute or until you're incapacitated. Once free per long rest or spend a Psionic Energy die.",
                        level = 15,
                    ),
                ),
                18 to listOf(
                    ClassFeature(
                        name = "Telekinetic Master",
                        description = "You can cast Telekinesis without expending a spell slot or components, using INT as your spellcasting ability. When cast this way, the spell's duration is concentration up to 1 minute. Once per long rest or spend a Psionic Energy die.",
                        level = 18,
                    ),
                ),
            ),
        ),
    )
}
