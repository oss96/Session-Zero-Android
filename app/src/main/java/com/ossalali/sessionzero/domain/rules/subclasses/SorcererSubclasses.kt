package com.ossalali.sessionzero.domain.rules.subclasses

import com.ossalali.sessionzero.domain.model.ClassFeature
import com.ossalali.sessionzero.domain.model.SubclassDefinition

object SorcererSubclasses {

    val ALL: List<SubclassDefinition> = listOf(
        // ── Aberrant ─────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Aberrant",
            description = "Draws on psionic power from an alien influence, gaining telepathic abilities and access to mind-themed spells.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Telepathic Speech",
                        description = "As a bonus action, choose a creature you can see within 30 feet; you and the creature can speak telepathically with each other while within a number of miles equal to your CHA modifier for a number of minutes equal to your Sorcerer level.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Psionic Spells",
                        description = "You gain additional spells at certain levels: Arms of Hadar and Dissonant Whispers at 1st, Calm Emotions and Detect Thoughts at 3rd, Hunger of Hadar and Sending at 5th, Evard's Black Tentacles and Summon Aberration at 7th, Rary's Telepathic Bond and Telekinesis at 9th.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Psionic Sorcery",
                        description = "When you cast any of your Psionic Spells, you can cast it by expending Sorcery Points equal to the spell's level instead of a spell slot; when you cast a spell this way, it requires no verbal or somatic components and no material components unless they are consumed.",
                        level = 6,
                    ),
                    ClassFeature(
                        name = "Psychic Defenses",
                        description = "You gain resistance to psychic damage and advantage on saving throws against being charmed or frightened.",
                        level = 6,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Revelation in Flesh",
                        description = "As a bonus action, spend 1 or more Sorcery Points to transform for 10 minutes; for each Sorcery Point spent choose one benefit: see invisible creatures within 60 feet, gain a flying speed equal to walking speed with hover, gain a swimming speed equal to twice walking speed plus water breathing, become slimy and can move through spaces as narrow as 1 inch without squeezing and spend 5 feet to escape grapple.",
                        level = 14,
                    ),
                ),
                18 to listOf(
                    ClassFeature(
                        name = "Warping Implosion",
                        description = "As an action, teleport up to 120 feet to an unoccupied space you can see; each creature within 30 feet of the space you left must make a STR save or take 3d10 force damage and be pulled toward the space you left; on a success, half damage and not pulled; once per long rest or spend 5 Sorcery Points.",
                        level = 18,
                    ),
                ),
            ),
        ),

        // ── Clockwork ────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Clockwork",
            description = "Channels the orderly magic of Mechanus, gaining the ability to negate advantage/disadvantage and access to order-themed spells.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Clockwork Magic",
                        description = "You gain additional spells at certain levels: Aid and Lesser Restoration at 3rd, Dispel Magic and Protection from Energy at 5th, Freedom of Movement and Summon Construct at 7th, Greater Restoration and Wall of Force at 9th; you can replace one of these with an abjuration or transmutation spell of the same level from the Sorcerer, Warlock, or Wizard list.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Restore Balance",
                        description = "When a creature you can see within 60 feet is about to make a d20 roll with advantage or disadvantage, you can use your reaction to prevent the roll from being affected by advantage or disadvantage; uses equal to proficiency bonus per long rest.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Bastion of Law",
                        description = "As an action, spend 1 to 5 Sorcery Points to create a ward on yourself or a creature within 30 feet; the ward has dice equal to points spent; when the warded creature takes damage, it can spend any number of ward dice and reduce the damage by the total rolled; the ward lasts until you finish a long rest or use this feature again.",
                        level = 6,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Trance of Order",
                        description = "As a bonus action, enter a state of clockwork for 1 minute; for the duration, attack rolls against you can't benefit from advantage, and you can treat a d20 roll of 9 or lower as a 10 for attack rolls, ability checks, and saving throws; once per long rest or spend 5 Sorcery Points.",
                        level = 14,
                    ),
                ),
                18 to listOf(
                    ClassFeature(
                        name = "Clockwork Cavalcade",
                        description = "As an action, summon spirits of order in a 30-foot cube originating from you; each creature of your choice in the area is affected: restore up to 100 HP, end all effects causing the blinded/deafened/paralyzed/poisoned/stunned conditions, repair all damaged objects fully; once per long rest or spend 7 Sorcery Points.",
                        level = 18,
                    ),
                ),
            ),
        ),

        // ── Draconic ─────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Draconic",
            description = "Possesses innate magic from draconic ancestry, gaining bonus HP, natural armor, and eventually elemental damage resistance.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Draconic Resilience",
                        description = "Your hit point maximum increases by 1 for each Sorcerer level; your AC when not wearing armor equals 13 + your DEX modifier.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Dragon Speech",
                        description = "You can speak, read, and write Draconic; you have advantage on CHA checks when interacting with dragons.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Elemental Affinity",
                        description = "When you cast a spell that deals damage of the type associated with your draconic ancestry, add your CHA modifier to one damage roll; you can also spend 1 Sorcery Point to gain resistance to that damage type for 1 hour.",
                        level = 6,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Dragon Wings",
                        description = "As a bonus action, you can sprout spectral dragon wings, gaining a flying speed equal to your walking speed; the wings last until you dismiss them; you can't manifest your wings while wearing armor unless it is made to accommodate them.",
                        level = 14,
                    ),
                ),
                18 to listOf(
                    ClassFeature(
                        name = "Draconic Presence",
                        description = "As an action, spend 5 Sorcery Points to channel the dread presence of your dragon ancestor; each creature of your choice within 60 feet must make a WIS save or be charmed or frightened for 1 minute; a creature can repeat the save at the end of each of its turns.",
                        level = 18,
                    ),
                ),
            ),
        ),

        // ── Wild Magic ───────────────────────────────────────────────────
        SubclassDefinition(
            name = "Wild Magic",
            description = "Innate magic stems from chaos, causing unpredictable magical surges that can produce a variety of random effects.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Wild Magic Surge",
                        description = "Once per turn, the DM can have you roll a d20 immediately after you cast a Sorcerer spell of 1st level or higher; if you roll a 1, you roll on the Wild Magic Surge table to create a random magical effect.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Tides of Chaos",
                        description = "You can gain advantage on one attack roll, ability check, or saving throw; once used, the DM can trigger a Wild Magic Surge before you regain the use of this feature; you regain use after a long rest or a Wild Magic Surge.",
                        level = 3,
                    ),
                ),
                6 to listOf(
                    ClassFeature(
                        name = "Bend Luck",
                        description = "When a creature you can see makes an attack roll, ability check, or saving throw, you can use your reaction and spend 2 Sorcery Points to roll 1d4 and add or subtract the number from the creature's roll.",
                        level = 6,
                    ),
                ),
                14 to listOf(
                    ClassFeature(
                        name = "Controlled Chaos",
                        description = "Whenever you roll on the Wild Magic Surge table, you can roll twice and use either result.",
                        level = 14,
                    ),
                ),
                18 to listOf(
                    ClassFeature(
                        name = "Spell Bombardment",
                        description = "Once per turn when you roll damage for a spell and roll the highest number possible on any of the dice, you can choose one of those dice and roll it again, adding the additional roll to the damage.",
                        level = 18,
                    ),
                ),
            ),
        ),
    )
}
