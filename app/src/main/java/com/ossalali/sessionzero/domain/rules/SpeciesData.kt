package com.ossalali.sessionzero.domain.rules

import com.ossalali.sessionzero.domain.model.LineageOption
import com.ossalali.sessionzero.domain.model.SpeciesDefinition
import com.ossalali.sessionzero.domain.model.SpeciesName
import com.ossalali.sessionzero.domain.model.SpeciesTrait

object SpeciesData {

    fun forSpecies(name: SpeciesName): SpeciesDefinition = ALL_SPECIES.first { it.name == name }

    val ALL_SPECIES: List<SpeciesDefinition> = listOf(
        // Aasimar
        SpeciesDefinition(
            name = SpeciesName.AASIMAR,
            speed = 30,
            size = "Medium or Small",
            darkvision = 60,
            traits = listOf(
                SpeciesTrait(
                    name = "Celestial Resistance",
                    description = "You have resistance to necrotic damage and radiant damage."
                ),
                SpeciesTrait(
                    name = "Healing Hands",
                    description = "As a Magic action, you touch a creature and roll a number of d4s equal to your proficiency bonus. The creature regains a number of hit points equal to the total rolled. Once you use this trait, you can't use it again until you finish a Long Rest."
                ),
                SpeciesTrait(
                    name = "Light Bearer",
                    description = "You know the Light cantrip. Charisma is your spellcasting ability for it."
                ),
                SpeciesTrait(
                    name = "Celestial Revelation",
                    description = "When you reach character level 3, you can transform as a Bonus Action using one of the options below, gaining the benefits of that option. The transformation lasts for 1 minute or until you end it as a Bonus Action. Once you transform, you can't do so again until you finish a Long Rest."
                ),
            ),
            lineageOptions = listOf(
                LineageOption(
                    name = "Heavenly Wings",
                    description = "Two spectral wings sprout from your back temporarily. Until the transformation ends, you have a Fly Speed equal to your Speed."
                ),
                LineageOption(
                    name = "Inner Radiance",
                    description = "Searing light radiates from you. Until the transformation ends, you shed Bright Light in a 10-foot radius and Dim Light for an additional 10 feet, and at the end of each of your turns, each creature within 10 feet of you takes radiant damage equal to your proficiency bonus."
                ),
                LineageOption(
                    name = "Necrotic Shroud",
                    description = "Your eyes briefly become pools of darkness, and flightless wings sprout from your back temporarily. Creatures other than your allies within 10 feet of you must succeed on a Charisma saving throw or have the Frightened condition until the end of your next turn. Until the transformation ends, once on each of your turns, you can deal extra necrotic damage to one target when you deal damage to it with an attack or a spell, equal to your proficiency bonus."
                ),
            ),
            languages = listOf("Common"),
        ),

        // Dragonborn
        SpeciesDefinition(
            name = SpeciesName.DRAGONBORN,
            speed = 30,
            size = "Medium",
            darkvision = 0,
            traits = listOf(
                SpeciesTrait(
                    name = "Breath Weapon",
                    description = "When you take the Attack action on your turn, you can replace one of your attacks with an exhalation of magical energy in either a 15-foot Cone or a 30-foot Line that is 5 feet wide (your choice each time). Each creature in that area must make a Dexterity saving throw (DC = 8 + your Constitution modifier + your proficiency bonus). On a failed save, a creature takes 1d10 damage of the type determined by your Draconic Ancestry. On a successful save, a creature takes half as much damage. This damage increases by 1d10 when you reach character levels 5 (2d10), 11 (3d10), and 17 (4d10). You can use this Breath Weapon a number of times equal to your proficiency bonus, and you regain all expended uses when you finish a Long Rest."
                ),
                SpeciesTrait(
                    name = "Damage Resistance",
                    description = "You have resistance to the damage type determined by your Draconic Ancestry."
                ),
                SpeciesTrait(
                    name = "Draconic Flight",
                    description = "When you reach character level 5, you can channel draconic magic to give yourself temporary flight. As a Bonus Action, you sprout spectral wings on your back that last for 10 minutes or until you retract them. During that time, you have a Fly Speed equal to your Speed. Once you use this trait, you can't use it again until you finish a Long Rest."
                ),
            ),
            lineageOptions = listOf(
                LineageOption(name = "Black", description = "Acid damage."),
                LineageOption(name = "Blue", description = "Lightning damage."),
                LineageOption(name = "Brass", description = "Fire damage."),
                LineageOption(name = "Bronze", description = "Lightning damage."),
                LineageOption(name = "Copper", description = "Acid damage."),
                LineageOption(name = "Gold", description = "Fire damage."),
                LineageOption(name = "Green", description = "Poison damage."),
                LineageOption(name = "Red", description = "Fire damage."),
                LineageOption(name = "Silver", description = "Cold damage."),
                LineageOption(name = "White", description = "Cold damage."),
            ),
            languages = listOf("Common"),
        ),

        // Dwarf
        SpeciesDefinition(
            name = SpeciesName.DWARF,
            speed = 30,
            size = "Medium",
            darkvision = 120,
            traits = listOf(
                SpeciesTrait(
                    name = "Dwarven Resilience",
                    description = "You have resistance to Poison damage. You also have Advantage on saving throws you make to avoid or end the Poisoned condition."
                ),
                SpeciesTrait(
                    name = "Dwarven Toughness",
                    description = "Your Hit Point maximum increases by 1, and it increases by 1 again whenever you gain a level."
                ),
                SpeciesTrait(
                    name = "Stonecunning",
                    description = "As a Bonus Action, you gain Tremorsense with a range of 60 feet for 10 minutes. You must be on a stone surface or touching a stone surface to use this Tremorsense. The stone can be natural or worked. You can use this Bonus Action a number of times equal to your proficiency bonus, and you regain all expended uses when you finish a Long Rest."
                ),
            ),
            languages = listOf("Common", "Dwarvish"),
        ),

        // Elf
        SpeciesDefinition(
            name = SpeciesName.ELF,
            speed = 30,
            size = "Medium",
            darkvision = 60,
            traits = listOf(
                SpeciesTrait(
                    name = "Fey Ancestry",
                    description = "You have Advantage on saving throws you make to avoid or end the Charmed condition."
                ),
                SpeciesTrait(
                    name = "Keen Senses",
                    description = "You have proficiency in the Perception skill."
                ),
                SpeciesTrait(
                    name = "Trance",
                    description = "You don't need to sleep, and magic can't put you to sleep. You can finish a Long Rest in 4 hours if you spend those hours in a trancelike meditation, during which you retain consciousness."
                ),
            ),
            lineageOptions = listOf(
                LineageOption(
                    name = "Drow",
                    description = "The range of your Darkvision increases to 120 feet. You also know the Dancing Lights cantrip. Starting at character level 3, you can cast Faerie Fire with this trait. Starting at character level 5, you can also cast Darkness with this trait. Once you cast either of these spells with this trait, you can't cast that spell with it again until you finish a Long Rest. Charisma is your spellcasting ability for these spells."
                ),
                LineageOption(
                    name = "High Elf",
                    description = "You know the Prestidigitation cantrip. Whenever you finish a Long Rest, you can replace that cantrip with a different cantrip from the Wizard spell list. Intelligence is your spellcasting ability for this cantrip."
                ),
                LineageOption(
                    name = "Wood Elf",
                    description = "Your Speed increases to 35 feet. You also know the Druidcraft cantrip. Wisdom is your spellcasting ability for it."
                ),
            ),
            languages = listOf("Common", "Elvish"),
        ),

        // Gnome
        SpeciesDefinition(
            name = SpeciesName.GNOME,
            speed = 30,
            size = "Small",
            darkvision = 60,
            traits = listOf(
                SpeciesTrait(
                    name = "Gnomish Cunning",
                    description = "You have Advantage on Intelligence, Wisdom, and Charisma saving throws."
                ),
            ),
            lineageOptions = listOf(
                LineageOption(
                    name = "Forest Gnome",
                    description = "You know the Minor Illusion cantrip. You can also cast Speak with Animals with this trait. You can cast it a number of times equal to your proficiency bonus, and you regain all expended uses when you finish a Long Rest. Intelligence is your spellcasting ability for these spells."
                ),
                LineageOption(
                    name = "Rock Gnome",
                    description = "You know the Mending and Prestidigitation cantrips. In addition, you can spend 10 minutes casting Prestidigitation to create a Tiny clockwork device (AC 5, 1 HP) such as a toy, fire starter, or music box. When you create the device, you determine its function. You can have up to three such devices in existence at a time."
                ),
            ),
            languages = listOf("Common", "Gnomish"),
        ),

        // Goliath
        SpeciesDefinition(
            name = SpeciesName.GOLIATH,
            speed = 35,
            size = "Medium",
            darkvision = 0,
            traits = listOf(
                SpeciesTrait(
                    name = "Large Form",
                    description = "Starting at character level 5, you can change your size to Large as a Bonus Action if you're in a big enough space. This size change lasts for 10 minutes or until you end it as a Bonus Action. Once you use this trait, you can't use it again until you finish a Long Rest."
                ),
                SpeciesTrait(
                    name = "Powerful Build",
                    description = "You count as one size larger when determining your carrying capacity and the weight you can push, drag, or lift."
                ),
            ),
            lineageOptions = listOf(
                LineageOption(
                    name = "Cloud's Giant Ancestry",
                    description = "You can use a Reaction when you take damage to magically become partially insubstantial, halving that damage. You can use this a number of times equal to your proficiency bonus, and you regain all expended uses when you finish a Long Rest."
                ),
                LineageOption(
                    name = "Fire's Giant Ancestry",
                    description = "You can use a Reaction when you take damage to magically surround yourself in flames, halving that damage. Any creature in a space within 10 feet of you takes fire damage equal to your proficiency bonus. You can use this a number of times equal to your proficiency bonus, and you regain all expended uses when you finish a Long Rest."
                ),
                LineageOption(
                    name = "Frost's Giant Ancestry",
                    description = "You can use a Reaction when you take damage to magically surround yourself with icy cold, halving that damage. Any creature in a space within 10 feet of you takes cold damage equal to your proficiency bonus. You can use this a number of times equal to your proficiency bonus, and you regain all expended uses when you finish a Long Rest."
                ),
                LineageOption(
                    name = "Hill's Giant Ancestry",
                    description = "You can use a Reaction when you take damage to magically bolster yourself, halving that damage. You also gain temporary hit points equal to 1d12 + your Constitution modifier. You can use this a number of times equal to your proficiency bonus, and you regain all expended uses when you finish a Long Rest."
                ),
                LineageOption(
                    name = "Stone's Giant Ancestry",
                    description = "You can use a Reaction when you take damage to magically become like stone, halving that damage. You then have a Speed of 0 until the end of your next turn. You can use this a number of times equal to your proficiency bonus, and you regain all expended uses when you finish a Long Rest."
                ),
                LineageOption(
                    name = "Storm's Giant Ancestry",
                    description = "You can use a Reaction when you take damage to magically emit a bolt of lightning at a creature you can see within 60 feet, halving that damage. The target must make a Dexterity saving throw (DC = 8 + your proficiency bonus + your Constitution modifier). On a failed save, it takes 1d8 lightning damage. You can use this a number of times equal to your proficiency bonus, and you regain all expended uses when you finish a Long Rest."
                ),
            ),
            languages = listOf("Common", "Giant"),
        ),

        // Halfling
        SpeciesDefinition(
            name = SpeciesName.HALFLING,
            speed = 30,
            size = "Small",
            darkvision = 0,
            traits = listOf(
                SpeciesTrait(
                    name = "Brave",
                    description = "You have Advantage on saving throws you make to avoid or end the Frightened condition."
                ),
                SpeciesTrait(
                    name = "Halfling Nimbleness",
                    description = "You can move through the space of any creature that is a size larger than you, but you can't stop in the same space."
                ),
                SpeciesTrait(
                    name = "Luck",
                    description = "When you roll a 1 on the d20 of a D20 Test, you can reroll the die, and you must use the new roll."
                ),
                SpeciesTrait(
                    name = "Naturally Stealthy",
                    description = "You can take the Hide action even when you are obscured only by a creature that is at least one size larger than you."
                ),
            ),
            languages = listOf("Common", "Halfling"),
        ),

        // Human
        SpeciesDefinition(
            name = SpeciesName.HUMAN,
            speed = 30,
            size = "Medium or Small",
            darkvision = 0,
            traits = listOf(
                SpeciesTrait(
                    name = "Resourceful",
                    description = "You gain Heroic Inspiration whenever you finish a Long Rest."
                ),
                SpeciesTrait(
                    name = "Skillful",
                    description = "You gain proficiency in one skill of your choice."
                ),
                SpeciesTrait(
                    name = "Versatile",
                    description = "You gain an Origin feat of your choice."
                ),
            ),
            languages = listOf("Common"),
        ),

        // Orc
        SpeciesDefinition(
            name = SpeciesName.ORC,
            speed = 30,
            size = "Medium",
            darkvision = 120,
            traits = listOf(
                SpeciesTrait(
                    name = "Adrenaline Rush",
                    description = "You can take the Dash action as a Bonus Action. When you do so, you gain a number of Temporary Hit Points equal to your proficiency bonus. You can use this trait a number of times equal to your proficiency bonus, and you regain all expended uses when you finish a Short or Long Rest."
                ),
                SpeciesTrait(
                    name = "Relentless Endurance",
                    description = "When you are reduced to 0 Hit Points but not killed outright, you can drop to 1 Hit Point instead. Once you use this trait, you can't do so again until you finish a Long Rest."
                ),
            ),
            languages = listOf("Common", "Orc"),
        ),

        // Tiefling
        SpeciesDefinition(
            name = SpeciesName.TIEFLING,
            speed = 30,
            size = "Medium or Small",
            darkvision = 60,
            traits = listOf(
                SpeciesTrait(
                    name = "Otherworldly Presence",
                    description = "You know the Thaumaturgy cantrip. Charisma is your spellcasting ability for it."
                ),
                SpeciesTrait(
                    name = "Fiendish Legacy",
                    description = "You gain spells at certain character levels based on your chosen legacy. Charisma is your spellcasting ability for these spells."
                ),
            ),
            lineageOptions = listOf(
                LineageOption(
                    name = "Abyssal",
                    description = "You gain the Poison Spray cantrip. At level 3, you can cast Ray of Sickness once per Long Rest. At level 5, you can cast Hold Person once per Long Rest."
                ),
                LineageOption(
                    name = "Chthonic",
                    description = "You gain the Chill Touch cantrip. At level 3, you can cast False Life once per Long Rest. At level 5, you can cast Ray of Enfeeblement once per Long Rest."
                ),
                LineageOption(
                    name = "Infernal",
                    description = "You gain the Fire Bolt cantrip. At level 3, you can cast Hellish Rebuke once per Long Rest. At level 5, you can cast Darkness once per Long Rest."
                ),
            ),
            languages = listOf("Common"),
        ),
    )
}
