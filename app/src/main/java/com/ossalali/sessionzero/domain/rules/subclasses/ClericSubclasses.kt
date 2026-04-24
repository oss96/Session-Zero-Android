package com.ossalali.sessionzero.domain.rules.subclasses

import com.ossalali.sessionzero.domain.model.ClassFeature
import com.ossalali.sessionzero.domain.model.SubclassDefinition

object ClericSubclasses {

    val ALL: List<SubclassDefinition> = listOf(
        // ── Life ───────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Life",
            description = "Focuses on healing magic, granting bonus healing to restoration spells and eventually heavy armor proficiency.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Disciple of Life",
                        description = "When you cast a healing spell of 1st level or higher, the creature regains extra HP equal to 2 + the spell's level.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Preserve Life",
                        description = "Channel Divinity: as an action, restore HP equal to 5x your Cleric level divided among creatures within 30 feet. Can't restore a creature above half its HP max.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Blessed Healer",
                        description = "When you cast a healing spell of 1st level or higher that restores HP to another creature, you regain HP equal to 2 + the spell's level.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Divine Intervention",
                        description = "You can call on your deity to intervene. Describe the assistance you seek, and the DM determines success. Once per Long Rest; if it fails you can try again after a Long Rest.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Supreme Healing",
                        description = "When you would roll dice to determine HP restored by a spell, you instead use the highest number possible for each die.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Light ──────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Light",
            description = "Harnesses radiant energy and fire to burn away darkness, gaining potent offensive spells and a protective Warding Flare.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Warding Flare",
                        description = "When attacked by a creature within 30 feet that you can see, use your Reaction to impose Disadvantage on the attack roll. Uses equal to WIS modifier per Long Rest.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Radiance of the Dawn",
                        description = "Channel Divinity: as an action, dispel magical darkness within 30 feet. Each hostile creature within 30 feet makes a CON save or takes 2d10 + Cleric level radiant damage, half on success.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Improved Warding Flare",
                        description = "You can also use Warding Flare when a creature you can see within 30 feet attacks a creature other than you.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Divine Intervention",
                        description = "You can call on your deity to intervene. Describe the assistance you seek, and the DM determines success. Once per Long Rest.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Corona of Light",
                        description = "As an action, activate an aura of sunlight for 1 minute. You emit bright light in 60 feet and dim light 30 feet beyond that. Enemies in bright light have Disadvantage on saves against fire and radiant spells.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Trickery ──────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Trickery",
            description = "Uses illusion and deception in service of their deity, gaining stealth-related abilities and a duplicitous blessing.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Blessing of the Trickster",
                        description = "As an action, give yourself or a willing creature Advantage on Stealth checks for 1 hour. Lasts until you use this feature again.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Invoke Duplicity",
                        description = "Channel Divinity: as a Bonus Action, create an illusory duplicate within 30 feet for 1 minute with concentration. You can move it 30 feet on your turn. You have Advantage on attacks when you and the duplicate are within 5 feet of the target. You can cast spells as though you were in the duplicate's space.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Trickster's Transposition",
                        description = "You can teleport to swap places with your duplicate as a Bonus Action. You can do this a number of times equal to your WIS modifier per Long Rest.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Divine Intervention",
                        description = "You can call on your deity to intervene. Describe the assistance you seek, and the DM determines success. Once per Long Rest.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Improved Duplicity",
                        description = "Your duplicate can distract creatures near it. Creatures within 5 feet of the duplicate have Disadvantage on saving throws against your spells. When you and your duplicate are both within 5 feet of a creature, you have Advantage on attack rolls against it.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── War ────────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "War",
            description = "Inspires allies on the battlefield, granting bonus attacks through divine power and eventually heavy armor and martial weapon proficiency.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "War Priest",
                        description = "When you take the Attack action, you can make one weapon attack as a Bonus Action. Uses equal to WIS modifier per Long Rest.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Guided Strike",
                        description = "Channel Divinity: when you or a creature within 30 feet makes an attack roll, you can use your Reaction to grant a +10 bonus to the roll after seeing it but before knowing if it hits.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "War God's Blessing",
                        description = "You can use your Guided Strike Channel Divinity to aid an ally within 30 feet. When that creature makes an attack roll, you can use your Reaction to grant them the +10 bonus.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Divine Intervention",
                        description = "You can call on your deity to intervene. Describe the assistance you seek, and the DM determines success. Once per Long Rest.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Avatar of Battle",
                        description = "You gain resistance to bludgeoning, piercing, and slashing damage from nonmagical weapons.",
                        level = 14,
                    ),
                ),
            ),
        ),
    )
}
