package com.ossalali.sessionzero.domain.rules

import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.ClassDefinition
import com.ossalali.sessionzero.domain.model.ClassFeature
import com.ossalali.sessionzero.domain.model.ClassName
import com.ossalali.sessionzero.domain.model.Coins
import com.ossalali.sessionzero.domain.model.EquipmentItem
import com.ossalali.sessionzero.domain.model.EquipmentPackage
import com.ossalali.sessionzero.domain.model.SkillName
import com.ossalali.sessionzero.domain.model.SpellcastingProfile
import com.ossalali.sessionzero.domain.model.SpellcastingType
import com.ossalali.sessionzero.domain.model.SubclassDefinition

object ClassData {

    fun forClass(name: ClassName): ClassDefinition = ALL_CLASSES.first { it.name == name }

    val ALL_CLASSES: List<ClassDefinition> = listOf(
        // ── Barbarian ──────────────────────────────────────────────────────
        ClassDefinition(
            name = ClassName.BARBARIAN,
            hitDie = 12,
            primaryAbility = listOf(AbilityName.STR),
            savingThrows = listOf(AbilityName.STR, AbilityName.CON),
            skillChoices = listOf(
                SkillName.ANIMAL_HANDLING, SkillName.ATHLETICS, SkillName.INTIMIDATION,
                SkillName.NATURE, SkillName.PERCEPTION, SkillName.SURVIVAL,
            ),
            numSkillChoices = 2,
            armorProficiencies = listOf("Light Armor", "Medium Armor", "Shields"),
            weaponProficiencies = listOf("Simple Weapons", "Martial Weapons"),
            features = mapOf(
                1 to listOf(
                    ClassFeature("Rage", "Enter a fury that grants bonus damage on STR-based melee attacks, resistance to Bludgeoning/Piercing/Slashing damage, and advantage on STR checks and saves.", 1),
                    ClassFeature("Unarmored Defense", "While not wearing armor, your AC equals 10 + DEX modifier + CON modifier. You can use a Shield and still gain this benefit.", 1),
                    ClassFeature("Weapon Mastery", "You gain the mastery property of two kinds of Simple or Martial Melee weapons of your choice.", 1),
                ),
                2 to listOf(
                    ClassFeature("Danger Sense", "You have advantage on DEX saving throws unless you are Incapacitated.", 2),
                    ClassFeature("Reckless Attack", "When you make your first attack on your turn, you can choose to attack recklessly, gaining advantage on all STR-based melee attack rolls that turn, but attack rolls against you have advantage until your next turn.", 2),
                ),
                3 to listOf(
                    ClassFeature("Barbarian Subclass", "You gain a Barbarian subclass of your choice.", 3),
                    ClassFeature("Primal Knowledge", "You gain proficiency in one skill from the Barbarian skill list.", 3),
                ),
            ),
            subclasses = listOf(
                SubclassDefinition("Berserker", "Channels rage into a violent frenzy, gaining extra attacks and immunity to charm and fear while raging."),
                SubclassDefinition("Wild Heart", "Draws power from the animal spirits of nature, gaining a Beast Companion feature and nature-themed rage abilities."),
                SubclassDefinition("World Tree", "Taps into the magic of the World Tree to teleport allies and create protective barriers while raging."),
                SubclassDefinition("Zealot", "Fueled by divine fury, deals extra radiant or necrotic damage and can be resurrected without material components."),
            ),
            subclassLevel = 3,
            equipmentPackages = listOf(
                EquipmentPackage(
                    name = "Package A",
                    items = listOf(
                        EquipmentItem("Greataxe"),
                        EquipmentItem("Handaxe", 4),
                        EquipmentItem("Explorer's Pack"),
                        EquipmentItem("Javelin", 6),
                    ),
                    coins = Coins(gp = 15),
                ),
                EquipmentPackage(
                    name = "Package B",
                    items = emptyList(),
                    coins = Coins(gp = 75),
                ),
            ),
            startingGold = "75 GP",
        ),

        // ── Bard ───────────────────────────────────────────────────────────
        ClassDefinition(
            name = ClassName.BARD,
            hitDie = 8,
            primaryAbility = listOf(AbilityName.CHA),
            savingThrows = listOf(AbilityName.DEX, AbilityName.CHA),
            skillChoices = SkillName.entries,
            numSkillChoices = 3,
            armorProficiencies = listOf("Light Armor"),
            weaponProficiencies = listOf("Simple Weapons"),
            toolProficiencies = listOf("Three Musical Instruments of your choice"),
            features = mapOf(
                1 to listOf(
                    ClassFeature("Bardic Inspiration", "You can inspire others with your performance. As a Bonus Action, grant an ally within 60 feet a Bardic Inspiration die (d6) they can add to one ability check, attack roll, or saving throw.", 1),
                    ClassFeature("Spellcasting", "You have learned to cast spells through your musical performances. CHA is your spellcasting ability for your Bard spells.", 1),
                ),
                2 to listOf(
                    ClassFeature("Expertise", "Choose two of your skill proficiencies. Your proficiency bonus is doubled for any ability check you make that uses either of those skills.", 2),
                    ClassFeature("Jack of All Trades", "You can add half your proficiency bonus, rounded down, to any ability check you make that doesn't already include your proficiency bonus.", 2),
                ),
                3 to listOf(
                    ClassFeature("Bard Subclass", "You gain a Bard subclass of your choice.", 3),
                ),
            ),
            subclasses = listOf(
                SubclassDefinition("Dance", "Uses graceful movement to bolster allies and confound enemies, gaining benefits while moving on the battlefield."),
                SubclassDefinition("Glamour", "Weaves fey magic into performances that charm and enthrall audiences, granting temporary hit points and commanding movement."),
                SubclassDefinition("Lore", "Pursues knowledge from every source, gaining additional skill proficiencies and the ability to use Bardic Inspiration to debuff enemies."),
                SubclassDefinition("Valor", "Inspires bravery on the battlefield, gaining medium armor and shield proficiency and granting allies combat benefits with Bardic Inspiration."),
            ),
            subclassLevel = 3,
            spellcasting = SpellcastingProfile(
                ability = AbilityName.CHA,
                type = SpellcastingType.FULL,
                cantripsKnown = mapOf(1 to 2, 4 to 3, 10 to 4),
                spellsPrepared = true,
            ),
            equipmentPackages = listOf(
                EquipmentPackage(
                    name = "Package A",
                    items = listOf(
                        EquipmentItem("Leather Armor"),
                        EquipmentItem("Dagger", 2),
                        EquipmentItem("Musical Instrument of your choice"),
                        EquipmentItem("Entertainer's Pack"),
                    ),
                    coins = Coins(gp = 19),
                ),
                EquipmentPackage(
                    name = "Package B",
                    items = emptyList(),
                    coins = Coins(gp = 100),
                ),
            ),
            startingGold = "100 GP",
        ),

        // ── Cleric ─────────────────────────────────────────────────────────
        ClassDefinition(
            name = ClassName.CLERIC,
            hitDie = 8,
            primaryAbility = listOf(AbilityName.WIS),
            savingThrows = listOf(AbilityName.WIS, AbilityName.CHA),
            skillChoices = listOf(
                SkillName.HISTORY, SkillName.INSIGHT, SkillName.MEDICINE,
                SkillName.PERSUASION, SkillName.RELIGION,
            ),
            numSkillChoices = 2,
            armorProficiencies = listOf("Light Armor", "Medium Armor", "Shields"),
            weaponProficiencies = listOf("Simple Weapons"),
            features = mapOf(
                1 to listOf(
                    ClassFeature("Spellcasting", "You have learned to cast spells through prayer and meditation. WIS is your spellcasting ability. You can prepare spells from the Cleric spell list and can perform rituals.", 1),
                    ClassFeature("Channel Divinity", "You can channel divine energy to fuel magical effects. You start with the Turn Undead effect, which forces undead creatures to flee.", 1),
                ),
                2 to listOf(
                    ClassFeature("Holy Order", "You have dedicated yourself to one of three sacred roles: Holy Order of the Protector (martial weapon and heavy armor proficiency), the Scholar (extra skill), or the Thaumaturge (extra cantrip).", 2),
                ),
                3 to listOf(
                    ClassFeature("Cleric Subclass", "You gain a Cleric subclass of your choice.", 3),
                ),
            ),
            subclasses = listOf(
                SubclassDefinition("Life", "Focuses on healing magic, granting bonus healing to restoration spells and eventually heavy armor proficiency."),
                SubclassDefinition("Light", "Harnesses radiant energy and fire to burn away darkness, gaining potent offensive spells and a protective Warding Flare."),
                SubclassDefinition("Trickery", "Uses illusion and deception in service of their deity, gaining stealth-related abilities and a duplicitous blessing."),
                SubclassDefinition("War", "Inspires allies on the battlefield, granting bonus attacks through divine power and eventually heavy armor and martial weapon proficiency."),
            ),
            subclassLevel = 3,
            spellcasting = SpellcastingProfile(
                ability = AbilityName.WIS,
                type = SpellcastingType.FULL,
                cantripsKnown = mapOf(1 to 3, 4 to 4, 10 to 5),
                spellsPrepared = true,
                ritual = true,
            ),
            equipmentPackages = listOf(
                EquipmentPackage(
                    name = "Package A",
                    items = listOf(
                        EquipmentItem("Chain Shirt"),
                        EquipmentItem("Shield"),
                        EquipmentItem("Mace"),
                        EquipmentItem("Holy Symbol"),
                        EquipmentItem("Priest's Pack"),
                    ),
                    coins = Coins(gp = 7),
                ),
                EquipmentPackage(
                    name = "Package B",
                    items = emptyList(),
                    coins = Coins(gp = 110),
                ),
            ),
            startingGold = "110 GP",
        ),

        // ── Druid ──────────────────────────────────────────────────────────
        ClassDefinition(
            name = ClassName.DRUID,
            hitDie = 8,
            primaryAbility = listOf(AbilityName.WIS),
            savingThrows = listOf(AbilityName.INT, AbilityName.WIS),
            skillChoices = listOf(
                SkillName.ARCANA, SkillName.ANIMAL_HANDLING, SkillName.INSIGHT,
                SkillName.MEDICINE, SkillName.NATURE, SkillName.PERCEPTION,
                SkillName.RELIGION, SkillName.SURVIVAL,
            ),
            numSkillChoices = 2,
            armorProficiencies = listOf("Light Armor", "Shields"),
            weaponProficiencies = listOf("Simple Weapons"),
            toolProficiencies = listOf("Herbalism Kit"),
            features = mapOf(
                1 to listOf(
                    ClassFeature("Spellcasting", "You have learned to cast spells through studying the mystical forces of nature. WIS is your spellcasting ability. You prepare spells from the Druid list and can cast rituals.", 1),
                    ClassFeature("Druidic", "You know Druidic, the secret language of druids. You can use it to leave hidden messages and automatically spot such messages left by other druids.", 1),
                    ClassFeature("Primal Order", "You have dedicated yourself to one of two sacred roles: Magician (extra cantrip) or Warden (martial weapon proficiency and medium armor proficiency).", 1),
                ),
                2 to listOf(
                    ClassFeature("Wild Shape", "You can use a Bonus Action to magically assume the shape of a beast that you have seen before. You can transform a number of times equal to your proficiency bonus.", 2),
                    ClassFeature("Wild Companion", "You can expend a use of Wild Shape to cast Find Familiar without material components.", 2),
                ),
                3 to listOf(
                    ClassFeature("Druid Subclass", "You gain a Druid subclass of your choice.", 3),
                ),
            ),
            subclasses = listOf(
                SubclassDefinition("Land", "Draws on the magic of the environment, gaining bonus spells based on a chosen terrain and recovering spell slots during short rests."),
                SubclassDefinition("Moon", "Specializes in Wild Shape combat, gaining improved beast forms with more hit points and the ability to transform into more powerful creatures."),
                SubclassDefinition("Sea", "Channels the power of the ocean, gaining aquatic Wild Shape forms and water-themed magical abilities."),
                SubclassDefinition("Stars", "Draws on the power of starlight, gaining a starry form that can heal allies, attack with radiant bolts, or maintain concentration more easily."),
            ),
            subclassLevel = 3,
            spellcasting = SpellcastingProfile(
                ability = AbilityName.WIS,
                type = SpellcastingType.FULL,
                cantripsKnown = mapOf(1 to 2, 4 to 3, 10 to 4),
                spellsPrepared = true,
                ritual = true,
            ),
            equipmentPackages = listOf(
                EquipmentPackage(
                    name = "Package A",
                    items = listOf(
                        EquipmentItem("Leather Armor"),
                        EquipmentItem("Shield"),
                        EquipmentItem("Sickle"),
                        EquipmentItem("Druidic Focus"),
                        EquipmentItem("Explorer's Pack"),
                        EquipmentItem("Herbalism Kit"),
                    ),
                    coins = Coins(gp = 9),
                ),
                EquipmentPackage(
                    name = "Package B",
                    items = emptyList(),
                    coins = Coins(gp = 50),
                ),
            ),
            startingGold = "50 GP",
        ),

        // ── Fighter ────────────────────────────────────────────────────────
        ClassDefinition(
            name = ClassName.FIGHTER,
            hitDie = 10,
            primaryAbility = listOf(AbilityName.STR, AbilityName.DEX),
            savingThrows = listOf(AbilityName.STR, AbilityName.CON),
            skillChoices = listOf(
                SkillName.ACROBATICS, SkillName.ANIMAL_HANDLING, SkillName.ATHLETICS,
                SkillName.HISTORY, SkillName.INSIGHT, SkillName.INTIMIDATION,
                SkillName.PERCEPTION, SkillName.SURVIVAL,
            ),
            numSkillChoices = 2,
            armorProficiencies = listOf("Light Armor", "Medium Armor", "Heavy Armor", "Shields"),
            weaponProficiencies = listOf("Simple Weapons", "Martial Weapons"),
            features = mapOf(
                1 to listOf(
                    ClassFeature("Fighting Style", "You adopt a particular style of fighting as your specialty, gaining a bonus related to your chosen style such as Archery, Defense, Dueling, Great Weapon Fighting, Protection, or Two-Weapon Fighting.", 1),
                    ClassFeature("Second Wind", "You have a limited well of stamina. On your turn, you can use a Bonus Action to regain hit points equal to 1d10 + your Fighter level.", 1),
                    ClassFeature("Weapon Mastery", "You gain the mastery property of three kinds of Simple or Martial weapons of your choice.", 1),
                ),
                2 to listOf(
                    ClassFeature("Action Surge", "You can push yourself beyond your normal limits for a moment. On your turn, you can take one additional action. Once used, you must finish a Short or Long Rest to use it again.", 2),
                    ClassFeature("Tactical Mind", "You have a tactical mind that aids you outside of combat. When you fail an ability check, you can expend a use of Second Wind to add a d10 to the roll.", 2),
                ),
                3 to listOf(
                    ClassFeature("Fighter Subclass", "You gain a Fighter subclass of your choice.", 3),
                ),
            ),
            subclasses = listOf(
                SubclassDefinition("Battle Master", "Employs martial techniques called maneuvers, gaining superiority dice to fuel special combat moves like Riposte, Trip Attack, and Disarming Attack."),
                SubclassDefinition("Champion", "Focuses on raw physical power and athletic prowess, improving critical hit range and gaining remarkable athleticism."),
                SubclassDefinition("Eldritch Knight", "Combines martial prowess with arcane magic, gaining access to Wizard spells with a focus on Abjuration and Evocation."),
                SubclassDefinition("Psi Warrior", "Awakens psionic power within, using telekinetic force to protect allies and strike enemies with psychic blades."),
            ),
            subclassLevel = 3,
            equipmentPackages = listOf(
                EquipmentPackage(
                    name = "Package A",
                    items = listOf(
                        EquipmentItem("Chain Mail"),
                        EquipmentItem("Greatsword"),
                        EquipmentItem("Flail"),
                        EquipmentItem("Light Crossbow"),
                        EquipmentItem("Bolts", 20),
                        EquipmentItem("Dungeoneer's Pack"),
                    ),
                    coins = Coins(gp = 4),
                ),
                EquipmentPackage(
                    name = "Package B",
                    items = emptyList(),
                    coins = Coins(gp = 150),
                ),
            ),
            startingGold = "150 GP",
        ),

        // ── Monk ───────────────────────────────────────────────────────────
        ClassDefinition(
            name = ClassName.MONK,
            hitDie = 8,
            primaryAbility = listOf(AbilityName.DEX, AbilityName.WIS),
            savingThrows = listOf(AbilityName.STR, AbilityName.DEX),
            skillChoices = listOf(
                SkillName.ACROBATICS, SkillName.ATHLETICS, SkillName.HISTORY,
                SkillName.INSIGHT, SkillName.RELIGION, SkillName.STEALTH,
            ),
            numSkillChoices = 2,
            armorProficiencies = emptyList(),
            weaponProficiencies = listOf("Simple Weapons", "Martial Weapons that have the Light property"),
            features = mapOf(
                1 to listOf(
                    ClassFeature("Martial Arts", "Your practice of martial arts grants you a special fighting style. You can use DEX instead of STR for unarmed strikes and Monk weapons, and you roll a Martial Arts die (d6) for damage.", 1),
                    ClassFeature("Unarmored Defense", "While not wearing armor and not wielding a Shield, your AC equals 10 + DEX modifier + WIS modifier.", 1),
                ),
                2 to listOf(
                    ClassFeature("Monk's Focus", "Your training allows you to harness mystical energy called Focus Points. You have a number of Focus Points equal to your Monk level, used to fuel abilities like Flurry of Blows, Patient Defense, and Step of the Wind.", 2),
                    ClassFeature("Unarmored Movement", "Your speed increases by 10 feet while you are not wearing armor or wielding a Shield.", 2),
                    ClassFeature("Uncanny Metabolism", "When you roll Initiative, you can regain all expended Focus Points. You also regain a number of Hit Points equal to your Martial Arts die + Monk level.", 2),
                ),
                3 to listOf(
                    ClassFeature("Monk Subclass", "You gain a Monk subclass of your choice.", 3),
                    ClassFeature("Deflect Attacks", "You can use your Reaction to deflect melee and ranged attacks, reducing the damage taken. If damage is reduced to 0, you can spend a Focus Point to redirect the attack.", 3),
                ),
            ),
            subclasses = listOf(
                SubclassDefinition("Elements", "Channels ki into elemental magic, gaining the ability to cast elemental spells and infuse attacks with elemental energy."),
                SubclassDefinition("Mercy", "Manipulates the life force of others, gaining the ability to heal allies with a touch or inflict debilitating conditions on enemies."),
                SubclassDefinition("Open Hand", "Masters the art of unarmed combat, gaining enhanced Flurry of Blows that can knock enemies prone, push them, or prevent reactions."),
                SubclassDefinition("Shadow", "Harnesses the power of darkness, gaining the ability to teleport between shadows and cast darkness-related spells."),
            ),
            subclassLevel = 3,
            equipmentPackages = listOf(
                EquipmentPackage(
                    name = "Package A",
                    items = listOf(
                        EquipmentItem("Spear"),
                        EquipmentItem("Dart", 5),
                        EquipmentItem("Explorer's Pack"),
                    ),
                    coins = Coins(gp = 11),
                ),
                EquipmentPackage(
                    name = "Package B",
                    items = emptyList(),
                    coins = Coins(gp = 50),
                ),
            ),
            startingGold = "50 GP",
        ),

        // ── Paladin ────────────────────────────────────────────────────────
        ClassDefinition(
            name = ClassName.PALADIN,
            hitDie = 10,
            primaryAbility = listOf(AbilityName.STR, AbilityName.CHA),
            savingThrows = listOf(AbilityName.WIS, AbilityName.CHA),
            skillChoices = listOf(
                SkillName.ATHLETICS, SkillName.INSIGHT, SkillName.INTIMIDATION,
                SkillName.MEDICINE, SkillName.PERSUASION, SkillName.RELIGION,
            ),
            numSkillChoices = 2,
            armorProficiencies = listOf("Light Armor", "Medium Armor", "Heavy Armor", "Shields"),
            weaponProficiencies = listOf("Simple Weapons", "Martial Weapons"),
            features = mapOf(
                1 to listOf(
                    ClassFeature("Lay on Hands", "Your blessed touch can heal wounds. You have a pool of healing power equal to 5 x your Paladin level. You can also expend charges to cure diseases or neutralize poisons.", 1),
                    ClassFeature("Spellcasting", "You have learned to cast spells through prayer and devotion. CHA is your spellcasting ability. You prepare spells from the Paladin spell list.", 1),
                    ClassFeature("Weapon Mastery", "You gain the mastery property of two kinds of weapons of your choice.", 1),
                ),
                2 to listOf(
                    ClassFeature("Fighting Style", "You adopt a particular style of fighting as your specialty, such as Defense, Dueling, Great Weapon Fighting, or Protection.", 2),
                    ClassFeature("Channel Divinity", "You can channel divine energy to fuel magical effects. You gain the Divine Sense effect to locate celestials, fiends, and undead within 60 feet.", 2),
                ),
                3 to listOf(
                    ClassFeature("Paladin Subclass", "You gain a Paladin subclass of your choice, representing your Sacred Oath.", 3),
                ),
            ),
            subclasses = listOf(
                SubclassDefinition("Devotion", "Upholds the ideals of justice, virtue, and order, gaining powers to protect the innocent and smite the wicked with holy light."),
                SubclassDefinition("Glory", "Trains relentlessly to hone body and spirit, inspiring allies to feats of valor and gaining supernatural athleticism."),
                SubclassDefinition("Ancients", "Swears an oath to protect the light of the world, gaining nature-themed spells and the ability to restrain foes with ensnaring vines."),
                SubclassDefinition("Vengeance", "Devotes themselves to punishing wrongdoers, gaining abilities to pursue and strike down enemies of justice without mercy."),
            ),
            subclassLevel = 3,
            spellcasting = SpellcastingProfile(
                ability = AbilityName.CHA,
                type = SpellcastingType.HALF,
                spellsPrepared = true,
            ),
            equipmentPackages = listOf(
                EquipmentPackage(
                    name = "Package A",
                    items = listOf(
                        EquipmentItem("Chain Mail"),
                        EquipmentItem("Shield"),
                        EquipmentItem("Longsword"),
                        EquipmentItem("Javelin", 6),
                        EquipmentItem("Holy Symbol"),
                        EquipmentItem("Priest's Pack"),
                    ),
                    coins = Coins(gp = 9),
                ),
                EquipmentPackage(
                    name = "Package B",
                    items = emptyList(),
                    coins = Coins(gp = 150),
                ),
            ),
            startingGold = "150 GP",
        ),

        // ── Ranger ─────────────────────────────────────────────────────────
        ClassDefinition(
            name = ClassName.RANGER,
            hitDie = 10,
            primaryAbility = listOf(AbilityName.DEX, AbilityName.WIS),
            savingThrows = listOf(AbilityName.STR, AbilityName.DEX),
            skillChoices = listOf(
                SkillName.ANIMAL_HANDLING, SkillName.ATHLETICS, SkillName.INSIGHT,
                SkillName.INVESTIGATION, SkillName.NATURE, SkillName.PERCEPTION,
                SkillName.STEALTH, SkillName.SURVIVAL,
            ),
            numSkillChoices = 3,
            armorProficiencies = listOf("Light Armor", "Medium Armor", "Shields"),
            weaponProficiencies = listOf("Simple Weapons", "Martial Weapons"),
            features = mapOf(
                1 to listOf(
                    ClassFeature("Favored Enemy", "You always have the Hunter's Mark spell prepared. You can cast it a number of times equal to your WIS modifier without expending a spell slot.", 1),
                    ClassFeature("Spellcasting", "You have learned to cast spells through your connection to nature. WIS is your spellcasting ability. You prepare spells from the Ranger spell list.", 1),
                    ClassFeature("Weapon Mastery", "You gain the mastery property of two kinds of Simple or Martial weapons of your choice.", 1),
                ),
                2 to listOf(
                    ClassFeature("Deft Explorer", "You gain expertise in one skill you are proficient with. Your walking speed also increases by 10 feet if you are not wearing Heavy Armor.", 2),
                    ClassFeature("Fighting Style", "You adopt a particular style of fighting as your specialty, such as Archery, Defense, Dueling, or Two-Weapon Fighting.", 2),
                ),
                3 to listOf(
                    ClassFeature("Ranger Subclass", "You gain a Ranger subclass of your choice.", 3),
                ),
            ),
            subclasses = listOf(
                SubclassDefinition("Beast Master", "Forges a mystical bond with a beast companion that fights alongside you, gaining commands to direct it in battle."),
                SubclassDefinition("Fey Wanderer", "Draws on fey magic to beguile enemies, gaining psychic damage on attacks and the ability to add WIS to CHA checks."),
                SubclassDefinition("Gloom Stalker", "Ambushes foes in darkness, gaining bonus damage on first-round attacks, darkvision, and invisibility to creatures relying on darkvision."),
                SubclassDefinition("Hunter", "Trains to take on the most dangerous threats, gaining abilities to deal extra damage or defend against multiple attackers."),
            ),
            subclassLevel = 3,
            spellcasting = SpellcastingProfile(
                ability = AbilityName.WIS,
                type = SpellcastingType.HALF,
                spellsPrepared = true,
            ),
            equipmentPackages = listOf(
                EquipmentPackage(
                    name = "Package A",
                    items = listOf(
                        EquipmentItem("Studded Leather Armor"),
                        EquipmentItem("Scimitar", 2),
                        EquipmentItem("Longbow"),
                        EquipmentItem("Arrows", 20),
                        EquipmentItem("Quiver"),
                        EquipmentItem("Druidic Focus"),
                        EquipmentItem("Explorer's Pack"),
                    ),
                    coins = Coins(gp = 7),
                ),
                EquipmentPackage(
                    name = "Package B",
                    items = emptyList(),
                    coins = Coins(gp = 150),
                ),
            ),
            startingGold = "150 GP",
        ),

        // ── Rogue ──────────────────────────────────────────────────────────
        ClassDefinition(
            name = ClassName.ROGUE,
            hitDie = 8,
            primaryAbility = listOf(AbilityName.DEX),
            savingThrows = listOf(AbilityName.DEX, AbilityName.INT),
            skillChoices = listOf(
                SkillName.ACROBATICS, SkillName.ATHLETICS, SkillName.DECEPTION,
                SkillName.INSIGHT, SkillName.INTIMIDATION, SkillName.INVESTIGATION,
                SkillName.PERCEPTION, SkillName.PERFORMANCE, SkillName.PERSUASION,
                SkillName.SLEIGHT_OF_HAND, SkillName.STEALTH,
            ),
            numSkillChoices = 4,
            armorProficiencies = listOf("Light Armor"),
            weaponProficiencies = listOf("Simple Weapons", "Martial Weapons that have the Finesse or Light property"),
            toolProficiencies = listOf("Thieves' Tools"),
            features = mapOf(
                1 to listOf(
                    ClassFeature("Expertise", "Choose two of your skill proficiencies or one skill proficiency and Thieves' Tools. Your proficiency bonus is doubled for any ability check you make that uses either of those choices.", 1),
                    ClassFeature("Sneak Attack", "Once per turn, you can deal extra damage (1d6) to one creature you hit with an attack if you have advantage or an ally is within 5 feet of the target. The attack must use a Finesse or Ranged weapon.", 1),
                    ClassFeature("Thieves' Cant", "You have learned thieves' cant, a secret mix of dialect, jargon, and code that allows you to hide messages in seemingly normal conversation.", 1),
                    ClassFeature("Weapon Mastery", "You gain the mastery property of two kinds of Simple or Martial weapons of your choice that have the Finesse or Light property.", 1),
                ),
                2 to listOf(
                    ClassFeature("Cunning Action", "Your quick thinking and agility allow you to act swiftly. On each of your turns, you can use a Bonus Action to take the Dash, Disengage, or Hide action.", 2),
                ),
                3 to listOf(
                    ClassFeature("Rogue Subclass", "You gain a Rogue subclass of your choice.", 3),
                    ClassFeature("Steady Aim", "As a Bonus Action, you give yourself advantage on your next attack roll on the current turn if you haven't moved during this turn. Your speed becomes 0 until the end of the turn.", 3),
                ),
            ),
            subclasses = listOf(
                SubclassDefinition("Arcane Trickster", "Augments stealth and agility with spellcasting, gaining Wizard spells focused on Enchantment and Illusion to enhance trickery."),
                SubclassDefinition("Assassin", "Masters the art of death, gaining bonus damage against surprised creatures and proficiency with the Disguise Kit and Poisoner's Kit."),
                SubclassDefinition("Soulknife", "Manifests psionic blades of psychic energy, gaining telepathic communication and enhanced skill checks with Psionic Energy dice."),
                SubclassDefinition("Thief", "Hones the arts of burglary and treasure hunting, gaining the ability to use objects as a Bonus Action and climb unusually fast."),
            ),
            subclassLevel = 3,
            equipmentPackages = listOf(
                EquipmentPackage(
                    name = "Package A",
                    items = listOf(
                        EquipmentItem("Leather Armor"),
                        EquipmentItem("Shortsword", 2),
                        EquipmentItem("Shortbow"),
                        EquipmentItem("Arrows", 20),
                        EquipmentItem("Quiver"),
                        EquipmentItem("Thieves' Tools"),
                        EquipmentItem("Burglar's Pack"),
                        EquipmentItem("Dagger", 2),
                    ),
                    coins = Coins(gp = 16),
                ),
                EquipmentPackage(
                    name = "Package B",
                    items = emptyList(),
                    coins = Coins(gp = 110),
                ),
            ),
            startingGold = "110 GP",
        ),

        // ── Sorcerer ──────────────────────────────────────────────────────
        ClassDefinition(
            name = ClassName.SORCERER,
            hitDie = 6,
            primaryAbility = listOf(AbilityName.CON, AbilityName.CHA),
            savingThrows = listOf(AbilityName.CON, AbilityName.CHA),
            skillChoices = listOf(
                SkillName.ARCANA, SkillName.DECEPTION, SkillName.INSIGHT,
                SkillName.INTIMIDATION, SkillName.PERSUASION, SkillName.RELIGION,
            ),
            numSkillChoices = 2,
            armorProficiencies = emptyList(),
            weaponProficiencies = listOf("Simple Weapons"),
            features = mapOf(
                1 to listOf(
                    ClassFeature("Spellcasting", "An event in your past or your bloodline left a mark of arcane magic on you. CHA is your spellcasting ability for Sorcerer spells.", 1),
                    ClassFeature("Innate Sorcery", "As a Bonus Action, you can tap into your innate magic, gaining a bonus to the spell save DC and spell attack modifier of your Sorcerer spells for 1 minute.", 1),
                ),
                2 to listOf(
                    ClassFeature("Font of Magic", "You gain Sorcery Points equal to your Sorcerer level. You can use these points to create spell slots or fuel Metamagic options.", 2),
                    ClassFeature("Metamagic", "You gain the ability to twist your spells to suit your needs. You learn two Metamagic options such as Careful Spell, Distant Spell, Extended Spell, or Twinned Spell.", 2),
                ),
                3 to listOf(
                    ClassFeature("Sorcerer Subclass", "You gain a Sorcerer subclass of your choice.", 3),
                ),
            ),
            subclasses = listOf(
                SubclassDefinition("Aberrant", "Draws on psionic power from an alien influence, gaining telepathic abilities and access to mind-themed spells."),
                SubclassDefinition("Clockwork", "Channels the orderly magic of Mechanus, gaining the ability to negate advantage/disadvantage and access to order-themed spells."),
                SubclassDefinition("Draconic", "Possesses innate magic from draconic ancestry, gaining bonus HP, natural armor, and eventually elemental damage resistance."),
                SubclassDefinition("Wild Magic", "Innate magic stems from chaos, causing unpredictable magical surges that can produce a variety of random effects."),
            ),
            subclassLevel = 3,
            spellcasting = SpellcastingProfile(
                ability = AbilityName.CHA,
                type = SpellcastingType.FULL,
                cantripsKnown = mapOf(1 to 4, 4 to 5, 10 to 6),
                spellsPrepared = true,
            ),
            equipmentPackages = listOf(
                EquipmentPackage(
                    name = "Package A",
                    items = listOf(
                        EquipmentItem("Spear"),
                        EquipmentItem("Arcane Focus"),
                        EquipmentItem("Dungeoneer's Pack"),
                        EquipmentItem("Dagger", 2),
                    ),
                    coins = Coins(gp = 28),
                ),
                EquipmentPackage(
                    name = "Package B",
                    items = emptyList(),
                    coins = Coins(gp = 50),
                ),
            ),
            startingGold = "50 GP",
        ),

        // ── Warlock ────────────────────────────────────────────────────────
        ClassDefinition(
            name = ClassName.WARLOCK,
            hitDie = 8,
            primaryAbility = listOf(AbilityName.CHA),
            savingThrows = listOf(AbilityName.WIS, AbilityName.CHA),
            skillChoices = listOf(
                SkillName.ARCANA, SkillName.DECEPTION, SkillName.HISTORY,
                SkillName.INTIMIDATION, SkillName.INVESTIGATION, SkillName.NATURE,
                SkillName.RELIGION,
            ),
            numSkillChoices = 2,
            armorProficiencies = listOf("Light Armor"),
            weaponProficiencies = listOf("Simple Weapons"),
            features = mapOf(
                1 to listOf(
                    ClassFeature("Pact Magic", "Your arcane research and your patron's magic have given you facility with spells. CHA is your spellcasting ability. You have a limited number of spell slots that recharge on a Short or Long Rest.", 1),
                    ClassFeature("Eldritch Invocations", "Fragments of forbidden knowledge have granted you a magical insight. You gain two Eldritch Invocations of your choice, which enhance your abilities.", 1),
                    ClassFeature("Warlock Subclass", "You gain a Warlock subclass of your choice, representing your otherworldly patron.", 1),
                ),
                2 to listOf(
                    ClassFeature("Magical Cunning", "If all your Pact Magic spell slots are expended, you can perform a ritual for 1 minute to regain half your spell slots (rounded up).", 2),
                ),
                3 to listOf(
                    ClassFeature("Pact Boon", "Your patron grants you a special gift: Pact of the Blade (a magical weapon), Pact of the Chain (an improved familiar), Pact of the Tome (a Book of Shadows with extra cantrips).", 3),
                ),
            ),
            subclasses = listOf(
                SubclassDefinition("Archfey", "Your patron is a lord or lady of the fey, granting you the power to charm and frighten foes with fey magic."),
                SubclassDefinition("Celestial", "Your patron is a powerful being of the Upper Planes, granting you healing abilities and radiant/fire damage bonuses."),
                SubclassDefinition("Fiend", "Your patron is a fiend from the Lower Planes, granting you temporary hit points when you reduce a hostile creature to 0 HP."),
                SubclassDefinition("Great Old One", "Your patron is a mysterious entity from beyond the stars, granting you telepathy and the ability to impose psychic effects on enemies."),
            ),
            subclassLevel = 1,
            spellcasting = SpellcastingProfile(
                ability = AbilityName.CHA,
                type = SpellcastingType.PACT,
                cantripsKnown = mapOf(1 to 2, 4 to 3, 10 to 4),
                spellsKnown = mapOf(
                    1 to 2, 2 to 3, 3 to 4, 4 to 5, 5 to 6,
                    6 to 7, 7 to 8, 8 to 9, 9 to 10,
                    10 to 10, 11 to 11, 12 to 11, 13 to 12,
                    14 to 12, 15 to 13, 16 to 13, 17 to 14,
                    18 to 14, 19 to 15, 20 to 15,
                ),
            ),
            equipmentPackages = listOf(
                EquipmentPackage(
                    name = "Package A",
                    items = listOf(
                        EquipmentItem("Leather Armor"),
                        EquipmentItem("Sickle"),
                        EquipmentItem("Arcane Focus"),
                        EquipmentItem("Scholar's Pack"),
                        EquipmentItem("Dagger", 2),
                    ),
                    coins = Coins(gp = 15),
                ),
                EquipmentPackage(
                    name = "Package B",
                    items = emptyList(),
                    coins = Coins(gp = 100),
                ),
            ),
            startingGold = "100 GP",
        ),

        // ── Wizard ─────────────────────────────────────────────────────────
        ClassDefinition(
            name = ClassName.WIZARD,
            hitDie = 6,
            primaryAbility = listOf(AbilityName.INT),
            savingThrows = listOf(AbilityName.INT, AbilityName.WIS),
            skillChoices = listOf(
                SkillName.ARCANA, SkillName.HISTORY, SkillName.INSIGHT,
                SkillName.INVESTIGATION, SkillName.MEDICINE, SkillName.RELIGION,
            ),
            numSkillChoices = 2,
            armorProficiencies = emptyList(),
            weaponProficiencies = listOf("Simple Weapons"),
            features = mapOf(
                1 to listOf(
                    ClassFeature("Spellcasting", "As a student of arcane magic, you possess a spellbook containing spells. INT is your spellcasting ability. You prepare spells from your spellbook and can cast rituals.", 1),
                    ClassFeature("Arcane Recovery", "You have learned to regain some magical energy by studying your spellbook. Once per day during a Short Rest, you can recover expended spell slots with a combined level equal to no more than half your Wizard level (rounded up).", 1),
                    ClassFeature("Ritual Adept", "You can cast any spell in your spellbook as a ritual if it has the Ritual tag, without needing to have it prepared.", 1),
                ),
                2 to listOf(
                    ClassFeature("Scholar", "You gain expertise in one of the following skills you are proficient in: Arcana, History, Investigation, Medicine, Nature, or Religion.", 2),
                ),
                3 to listOf(
                    ClassFeature("Wizard Subclass", "You gain a Wizard subclass of your choice.", 3),
                ),
            ),
            subclasses = listOf(
                SubclassDefinition("Abjuration", "Specializes in protective magic, gaining the ability to create magical wards that absorb damage for you and your allies."),
                SubclassDefinition("Divination", "Masters the art of seeing the future, gaining Portent dice that can replace any attack roll, saving throw, or ability check."),
                SubclassDefinition("Evocation", "Focuses on powerful offensive magic, gaining the ability to shape area spells around allies and maximize damage rolls."),
                SubclassDefinition("Illusion", "Specializes in creating convincing illusions, gaining the ability to change illusions after casting and eventually make them partially real."),
            ),
            subclassLevel = 3,
            spellcasting = SpellcastingProfile(
                ability = AbilityName.INT,
                type = SpellcastingType.FULL,
                cantripsKnown = mapOf(1 to 3, 4 to 4, 10 to 5),
                spellsPrepared = true,
                ritual = true,
            ),
            equipmentPackages = listOf(
                EquipmentPackage(
                    name = "Package A",
                    items = listOf(
                        EquipmentItem("Quarterstaff"),
                        EquipmentItem("Arcane Focus"),
                        EquipmentItem("Spellbook"),
                        EquipmentItem("Scholar's Pack"),
                        EquipmentItem("Robe"),
                    ),
                    coins = Coins(gp = 5),
                ),
                EquipmentPackage(
                    name = "Package B",
                    items = emptyList(),
                    coins = Coins(gp = 55),
                ),
            ),
            startingGold = "55 GP",
        ),
    )
}
