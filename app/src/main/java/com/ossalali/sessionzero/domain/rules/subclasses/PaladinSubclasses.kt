package com.ossalali.sessionzero.domain.rules.subclasses

import com.ossalali.sessionzero.domain.model.ClassFeature
import com.ossalali.sessionzero.domain.model.SubclassDefinition

object PaladinSubclasses {

    val ALL: List<SubclassDefinition> = listOf(
        // ── Devotion ──────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Devotion",
            description = "Upholds the ideals of justice, virtue, and order, gaining powers to protect the innocent and smite the wicked with holy light.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Sacred Weapon",
                        description = "Channel Divinity: as a bonus action, imbue your weapon with positive energy for 10 minutes; add your CHA modifier to attack rolls; the weapon emits bright light in 20 feet; the weapon counts as magical.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Holy Rebuke",
                        description = "Channel Divinity: when a creature within 5 feet hits you with an attack, you can use your reaction to deal radiant damage equal to 2d8 + your CHA modifier.",
                        level = 3,
                    ),
                ),
                7 to listOf(
                    ClassFeature(
                        name = "Aura of Devotion",
                        description = "You and friendly creatures within 10 feet can't be charmed while you are conscious; at 18th level, the range increases to 30 feet.",
                        level = 7,
                    ),
                ),
                15 to listOf(
                    ClassFeature(
                        name = "Smite of Protection",
                        description = "When you hit a creature with Divine Smite, your allies within 30 feet each gain temporary HP equal to 1d8 + your CHA modifier.",
                        level = 15,
                    ),
                ),
                20 to listOf(
                    ClassFeature(
                        name = "Holy Nimbus",
                        description = "As a bonus action, you emanate an aura of sunlight for 10 minutes; you emit bright light in a 30-foot radius and dim light 30 feet beyond that; whenever an enemy starts its turn in the bright light, it takes 10 radiant damage; you also gain advantage on saves against spells cast by fiends and undead; once per long rest.",
                        level = 20,
                    ),
                ),
            ),
        ),

        // ── Glory ─────────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Glory",
            description = "Trains relentlessly to hone body and spirit, inspiring allies to feats of valor and gaining supernatural athleticism.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Inspiring Smite",
                        description = "Channel Divinity: immediately after you deal damage with Divine Smite, you can distribute temporary HP to creatures within 30 feet; total temporary HP equals 2d8 + your Paladin level divided as you choose.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Peerless Athlete",
                        description = "Channel Divinity: as a bonus action, you gain advantage on Athletics and Acrobatics checks for 10 minutes; your carrying capacity and jumping distance double.",
                        level = 3,
                    ),
                ),
                7 to listOf(
                    ClassFeature(
                        name = "Aura of Alacrity",
                        description = "Your walking speed increases by 10 feet; allies within 5 feet also gain a speed increase of 10 feet while within the aura; at 18th level, the range increases to 10 feet.",
                        level = 7,
                    ),
                ),
                15 to listOf(
                    ClassFeature(
                        name = "Glorious Defense",
                        description = "When you or an ally within 10 feet is hit by an attack roll, you can use your reaction to add your CHA modifier to the target's AC against that attack; if the attack misses, you can make one weapon attack against the attacker if it's within your weapon's range; uses equal to CHA modifier per long rest.",
                        level = 15,
                    ),
                ),
                20 to listOf(
                    ClassFeature(
                        name = "Living Legend",
                        description = "As a bonus action, you gain the following benefits for 10 minutes: you have advantage on CHA checks, once per turn when you miss with a weapon attack you can cause it to hit instead, and you can reroll a failed saving throw and use the new result; once per long rest or expend a 5th-level spell slot.",
                        level = 20,
                    ),
                ),
            ),
        ),

        // ── Ancients ─────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Ancients",
            description = "Swears an oath to protect the light of the world, gaining nature-themed spells and the ability to restrain foes with ensnaring vines.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Nature's Wrath",
                        description = "Channel Divinity: as an action, cause spectral vines to spring from the ground and restrain a creature you can see within 15 feet; the target must make a STR or DEX save or be restrained; it repeats the save at end of each turn.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Healing Light",
                        description = "Channel Divinity: as a bonus action, emit a burst of healing light; each creature of your choice within 30 feet regains HP equal to 1d6 + half your Paladin level; you also end one condition on each target: blinded, deafened, or poisoned.",
                        level = 3,
                    ),
                ),
                7 to listOf(
                    ClassFeature(
                        name = "Aura of Warding",
                        description = "You and friendly creatures within 10 feet have resistance to damage from spells; at 18th level, the range increases to 30 feet.",
                        level = 7,
                    ),
                ),
                15 to listOf(
                    ClassFeature(
                        name = "Undying Sentinel",
                        description = "When you are reduced to 0 HP and not killed outright, you can choose to drop to 1 HP instead; once per long rest; additionally, you suffer none of the drawbacks of old age and can't be aged magically.",
                        level = 15,
                    ),
                ),
                20 to listOf(
                    ClassFeature(
                        name = "Elder Champion",
                        description = "As a bonus action, transform into a force of nature for 10 minutes; you regain 10 HP at the start of each turn; you can cast Paladin spells with a casting time of one action as a bonus action; enemies within 10 feet have disadvantage on saves against your spells and Channel Divinity; once per long rest.",
                        level = 20,
                    ),
                ),
            ),
        ),

        // ── Vengeance ────────────────────────────────────────────────────
        SubclassDefinition(
            name = "Vengeance",
            description = "Devotes themselves to punishing wrongdoers, gaining abilities to pursue and strike down enemies of justice without mercy.",
            features = mapOf(
                3 to listOf(
                    ClassFeature(
                        name = "Vow of Enmity",
                        description = "Channel Divinity: as a bonus action, choose a creature within 30 feet; you gain advantage on attack rolls against that creature for 1 minute or until it drops to 0 HP or falls unconscious.",
                        level = 3,
                    ),
                    ClassFeature(
                        name = "Abjure Enemy",
                        description = "Channel Divinity: as an action, choose a creature within 60 feet; it must make a WIS save or be frightened for 1 minute; if the creature is a fiend or undead, it has disadvantage on this save; the creature's speed is 0 while frightened.",
                        level = 3,
                    ),
                ),
                7 to listOf(
                    ClassFeature(
                        name = "Relentless Avenger",
                        description = "When you hit a creature with an opportunity attack, you can move up to half your speed immediately after as part of the same reaction; this movement doesn't provoke opportunity attacks.",
                        level = 7,
                    ),
                ),
                15 to listOf(
                    ClassFeature(
                        name = "Soul of Vengeance",
                        description = "When a creature under your Vow of Enmity makes an attack, you can use your reaction to make a melee weapon attack against that creature if it is within range.",
                        level = 15,
                    ),
                ),
                20 to listOf(
                    ClassFeature(
                        name = "Avenging Angel",
                        description = "As a bonus action, manifest angelic features for 10 minutes: you gain a flying speed of 60 feet, and you emanate a menacing aura in 30 feet; when an enemy enters or starts its turn there, it must make a WIS save or be frightened for 1 minute; once per long rest.",
                        level = 20,
                    ),
                ),
            ),
        ),
    )
}
