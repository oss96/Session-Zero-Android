package com.ossalali.sessionzero.domain.rules.subclasses

import com.ossalali.sessionzero.domain.model.ClassFeature
import com.ossalali.sessionzero.domain.model.SubclassDefinition

object WarlockSubclasses {

    val ALL: List<SubclassDefinition> = listOf(
        // ── Archfey ───────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Archfey",
            description = "Your patron is a lord or lady of the fey, granting you the power to charm and frighten foes with fey magic.",
            features = mapOf(
                1 to listOf(
                    ClassFeature(
                        name = "Steps of the Fey",
                        description = "As a Bonus Action, you can teleport up to 30 feet to an unoccupied space you can see. Uses equal to your CHA modifier per Long Rest. When you teleport, choose one effect: Refreshing Step—one creature you can see within 10 feet of your destination gains temporary HP equal to 1d10 + your CHA modifier, or Taunting Step—creatures within 5 feet of the space you left must succeed on a WIS save or have Disadvantage on attack rolls against creatures other than you until the start of your next turn.",
                        level = 1,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Misty Escape",
                        description = "When you take damage, you can use your Reaction to turn invisible and teleport up to 60 feet to an unoccupied space you can see. You remain invisible until the start of your next turn or until you attack, cast a spell, or deal damage. Once per Long Rest or restore by spending a spell slot.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Beguiling Defenses",
                        description = "You are immune to being charmed. When another creature attempts to charm you, you can use your Reaction to attempt to turn the charm back on it. The creature must make a WIS save against your spell save DC or be charmed by you for 1 minute or until it takes damage.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Bewitching Magic",
                        description = "When a creature fails a save against one of your spells, you can impose one additional effect: the creature's speed is reduced to 0 for 1 minute, the creature takes 4d10 psychic damage, or the creature is frightened of you for 1 minute. The creature can repeat the save at the end of each of its turns. Once per Long Rest or restore by spending a spell slot.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Celestial ─────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Celestial",
            description = "Your patron is a powerful being of the Upper Planes, granting you healing abilities and radiant/fire damage bonuses.",
            features = mapOf(
                1 to listOf(
                    ClassFeature(
                        name = "Healing Light",
                        description = "You gain a pool of d6s equal to 1 + your Warlock level that you can use to heal. As a Bonus Action, choose a creature within 60 feet and spend up to your CHA modifier dice from the pool. The creature regains HP equal to the total rolled. You regain all dice after a Long Rest.",
                        level = 1,
                    ),
                    ClassFeature(
                        name = "Celestial Spells",
                        description = "You gain bonus spells at certain Warlock levels: Cure Wounds and Guiding Bolt at 1st, Flaming Sphere and Lesser Restoration at 3rd, Daylight and Revivify at 5th, Guardian of Faith and Wall of Fire at 7th, Greater Restoration and Summon Celestial at 9th.",
                        level = 1,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Radiant Soul",
                        description = "You gain resistance to radiant damage. When you cast a spell that deals radiant or fire damage, you can add your CHA modifier to one radiant or fire damage roll of that spell.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Celestial Resilience",
                        description = "You and up to five creatures you choose gain temporary HP when you finish a Short or Long Rest. You gain Warlock level + CHA modifier temporary HP. Each chosen creature gains half your Warlock level + CHA modifier temporary HP.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Searing Vengeance",
                        description = "When you would make a death saving throw at the start of your turn, you can instead spring to your feet with a burst of radiant energy. You regain HP equal to half your max HP. Each creature of your choice within 30 feet takes 2d8 + CHA modifier radiant damage and is blinded until end of your current turn. Once per Long Rest.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Fiend ─────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Fiend",
            description = "Your patron is a fiend from the Lower Planes, granting you temporary hit points when you reduce a hostile creature to 0 HP.",
            features = mapOf(
                1 to listOf(
                    ClassFeature(
                        name = "Dark One's Blessing",
                        description = "When you reduce a hostile creature to 0 HP, you gain temporary hit points equal to your CHA modifier + your Warlock level.",
                        level = 1,
                    ),
                    ClassFeature(
                        name = "Fiend Spells",
                        description = "You gain bonus spells at certain Warlock levels: Burning Hands and Command at 1st, Blindness/Deafness and Scorching Ray at 3rd, Fireball and Stinking Cloud at 5th, Fire Shield and Wall of Fire at 7th, Geas and Insect Plague at 9th.",
                        level = 1,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Dark One's Own Luck",
                        description = "When you make an ability check or saving throw, you can add a d10 to the roll after seeing it but before knowing the result. Once per Short or Long Rest.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Fiendish Resilience",
                        description = "At the end of a Short or Long Rest, choose a damage type other than Force. You gain resistance to that damage type until you choose a different type. Damage from magical weapons or silver weapons ignores this resistance.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Hurl Through Hell",
                        description = "When you hit a creature with an attack, you can instantly transport it through the Lower Planes. The creature disappears and hurtles through a nightmarish landscape. At the end of your next turn, it returns to the space it previously occupied or the nearest unoccupied space. If the creature is not a Fiend, it takes 10d10 psychic damage as it reels from the experience. Once per Long Rest.",
                        level = 14,
                    ),
                ),
            ),
        ),

        // ── Great Old One ─────────────────────────────────────────────────
        SubclassDefinition(
            name = "Great Old One",
            description = "Your patron is a mysterious entity from beyond the stars, granting you telepathy and the ability to impose psychic effects on enemies.",
            features = mapOf(
                1 to listOf(
                    ClassFeature(
                        name = "Awakened Mind",
                        description = "You can speak telepathically with any creature you can see within 30 feet. The creature understands you if it knows at least one language. You can allow the creature to telepathically respond.",
                        level = 1,
                    ),
                    ClassFeature(
                        name = "Psychic Spells",
                        description = "You gain bonus spells at certain Warlock levels: Dissonant Whispers and Tasha's Hideous Laughter at 1st, Detect Thoughts and Phantasmal Force at 3rd, Clairvoyance and Hunger of Hadar at 5th, Confusion and Summon Aberration at 7th, Modify Memory and Telekinesis at 9th.",
                        level = 1,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Psychic Spells",
                        description = "You gain additional psychic abilities. When you deal psychic damage to a creature, you can also impose Disadvantage on the creature's next attack roll before the end of its next turn.",
                        level = 6,
                    ),
                    ClassFeature(
                        name = "Clairvoyant Combatant",
                        description = "When a creature you can see within 60 feet makes an attack roll against you, you can use your Reaction to impose Disadvantage on that roll. If the attack misses, you can make a spell attack roll against the creature. On a hit, it takes psychic damage equal to your Warlock level.",
                        level = 6,
                    ),
                ),
                10 to listOf(
                    ClassFeature(
                        name = "Thought Shield",
                        description = "Your thoughts can't be read by telepathy or other means unless you allow it. You also have resistance to psychic damage. When a creature deals psychic damage to you, that creature takes the same amount of psychic damage.",
                        level = 10,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Eldritch Master",
                        description = "You can create a psychic link with a creature you can see within 120 feet. The creature must make an INT save. On a failure, the creature is stunned. At the end of each of its turns, the creature can repeat the save. On a success, the creature takes 4d10 psychic damage. The link lasts for 1 minute or until you break concentration. Once per Long Rest.",
                        level = 14,
                    ),
                ),
            ),
        ),
    )
}
