package com.ossalali.sessionzero.domain.rules.subclasses

import com.ossalali.sessionzero.domain.model.ClassFeature
import com.ossalali.sessionzero.domain.model.SubclassDefinition

object MonkSubclasses {

    val ALL: List<SubclassDefinition> = listOf(
        // ── Elements ──────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Elements",
            description = "Channels ki into elemental magic, gaining the ability to cast elemental spells and infuse attacks with elemental energy.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Elemental Attunement",
                        description = "You learn the Elementalism cantrip and one other cantrip from the following: Acid Splash, Fire Bolt, Gust, Ray of Frost, Shocking Grasp, or Thunderclap. WIS is your spellcasting ability. You can replace the second cantrip when you gain a Monk level.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Manipulate Elements",
                        description = "You can spend 2 Focus Points to cast one of the following spells: Burning Hands, Thunderwave, or Shatter depending on your level. WIS is your spellcasting ability.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Elemental Burst",
                        description = "As an action, spend 3 Focus Points to force each creature in a 20-foot radius within 120 feet to make a DEX save or take damage equal to three rolls of your Martial Arts die. Choose acid, cold, fire, lightning, or thunder damage. Half damage on a success.",
                        level = 6,
                    ),
                ),
                11 to listOf(
                    ClassFeature(
                        name = "Stride of the Elements",
                        description = "As a bonus action, you gain a flying speed or swimming speed equal to your walking speed for 10 minutes.",
                        level = 11,
                    ),
                ),
                17 to listOf(
                    ClassFeature(
                        name = "Elemental Epitome",
                        description = "When you use your Elemental Attunement, you can additionally gain one of the following benefits for 1 minute: resistance to acid, cold, fire, lightning, or thunder damage; a 10-foot aura that deals damage of your chosen type; or the ability to move through creatures and objects.",
                        level = 17,
                    ),
                ),
            ),
        ),

        // ── Mercy ─────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Mercy",
            description = "Manipulates the life force of others, gaining the ability to heal allies with a touch or inflict debilitating conditions on enemies.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Hand of Harm",
                        description = "When you hit a creature with an unarmed strike, you can spend 1 Focus Point to deal extra necrotic damage equal to one roll of your Martial Arts die plus your WIS modifier. You can also impose the poisoned condition until the end of your next turn.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Hand of Healing",
                        description = "As an action or when you use Flurry of Blows, you can spend 1 Focus Point to touch a creature and restore hit points equal to a roll of your Martial Arts die plus your WIS modifier. This removes the poisoned condition.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Physician's Touch",
                        description = "Your Hand of Healing can also cure the blinded, deafened, paralyzed, stunned, or poisoned condition. Your Hand of Harm can impose the poisoned condition without spending a Focus Point.",
                        level = 6,
                    ),
                ),
                11 to listOf(
                    ClassFeature(
                        name = "Flurry of Healing and Harm",
                        description = "When you use Flurry of Blows, you can replace one unarmed strike with Hand of Healing without spending a Focus Point. When you use Flurry of Blows and hit with both strikes, you can use Hand of Harm on one of those strikes without spending a Focus Point.",
                        level = 11,
                    ),
                ),
                17 to listOf(
                    ClassFeature(
                        name = "Hand of Ultimate Mercy",
                        description = "As an action, touch the corpse of a creature that died within the last 24 hours and expend 5 Focus Points. The creature returns to life with a number of hit points equal to 4d10 + your WIS modifier. Once per long rest.",
                        level = 17,
                    ),
                ),
            ),
        ),

        // ── Open Hand ─────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Open Hand",
            description = "Masters the art of unarmed combat, gaining enhanced Flurry of Blows that can knock enemies prone, push them, or prevent reactions.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Open Hand Technique",
                        description = "When you hit a creature with a Flurry of Blows attack, you can impose one of the following: the target must succeed on a DEX save or be knocked prone, the target must succeed on a STR save or be pushed up to 15 feet away, or the target can't take reactions until the end of your next turn.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Wholeness of Body",
                        description = "As a bonus action, you regain hit points equal to a roll of your Martial Arts die plus your WIS modifier. You can use this a number of times equal to your proficiency bonus per long rest.",
                        level = 6,
                    ),
                ),
                11 to listOf(
                    ClassFeature(
                        name = "Fleet Step",
                        description = "When you take a bonus action on your turn, you can also make an unarmed strike as part of that bonus action.",
                        level = 11,
                    ),
                ),
                17 to listOf(
                    ClassFeature(
                        name = "Quivering Palm",
                        description = "When you hit a creature with an unarmed strike, you can spend 4 Focus Points to start imperceptible vibrations that last for a number of days equal to your Monk level. As an action, you can end the vibrations, forcing the creature to make a CON save or drop to 0 HP. On a success, the creature takes 10d12 force damage.",
                        level = 17,
                    ),
                ),
            ),
        ),

        // ── Shadow ────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Shadow",
            description = "Harnesses the power of darkness, gaining the ability to teleport between shadows and cast darkness-related spells.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Shadow Arts",
                        description = "You can spend 2 Focus Points to cast Darkness, Darkvision, Pass Without Trace, or Silence. WIS is your spellcasting ability.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Shadow Sight",
                        description = "You gain darkvision to 60 feet. If you already have darkvision, its range increases by 60 feet.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Shadow Step",
                        description = "As a bonus action when you are in dim light or darkness, you can teleport up to 60 feet to an unoccupied space you can see that is also in dim light or darkness. You have advantage on the first melee attack you make before the end of your turn after teleporting.",
                        level = 6,
                    ),
                ),
                11 to listOf(
                    ClassFeature(
                        name = "Improved Shadow Step",
                        description = "You can use Shadow Step when you are in bright light too. When you teleport, you can expend 1 Focus Point to bring a willing creature within 5 feet. You can also use Shadow Step as a reaction when you are hit by an attack.",
                        level = 11,
                    ),
                ),
                17 to listOf(
                    ClassFeature(
                        name = "Cloak of Shadows",
                        description = "As an action, you become invisible in dim light or darkness. The invisibility lasts until you make an attack, cast a spell, or enter bright light. You can spend 3 Focus Points to maintain the invisibility for 1 minute without the bright-light restriction.",
                        level = 17,
                    ),
                ),
            ),
        ),
    )
}
