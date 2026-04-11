package com.ossalali.sessionzero.domain.rules

import com.ossalali.sessionzero.domain.model.BackgroundDefinition
import com.ossalali.sessionzero.domain.model.BackgroundName
import com.ossalali.sessionzero.domain.model.SkillName

object BackgroundData {

    fun forBackground(name: BackgroundName): BackgroundDefinition =
        ALL_BACKGROUNDS.first { it.name == name }

    val ALL_BACKGROUNDS: List<BackgroundDefinition> = listOf(
        BackgroundDefinition(
            name = BackgroundName.ACOLYTE,
            description = "You devoted yourself to service in a temple, learning sacred rites and providing sacrifices to the god or gods you worship.",
            skillProficiencies = listOf(SkillName.INSIGHT, SkillName.RELIGION),
            toolProficiency = null,
            originFeat = "Magic Initiate (Cleric)",
        ),
        BackgroundDefinition(
            name = BackgroundName.ARTISAN,
            description = "You began mopping floors and scrubbing tables in an artisan's workshop while you were still young enough to be impressionable.",
            skillProficiencies = listOf(SkillName.INVESTIGATION, SkillName.PERSUASION),
            toolProficiency = "Artisan's Tools",
            originFeat = "Crafter",
        ),
        BackgroundDefinition(
            name = BackgroundName.CHARLATAN,
            description = "Once combative and prickly, you found a way to use your quick wit and silver tongue to get what you want.",
            skillProficiencies = listOf(SkillName.DECEPTION, SkillName.SLEIGHT_OF_HAND),
            toolProficiency = "Forgery Kit",
            originFeat = "Skilled",
        ),
        BackgroundDefinition(
            name = BackgroundName.CRIMINAL,
            description = "You eked out a living in dark alleys, cutting purses or burgling shops. Perhaps you were part of a small gang of like-minded criminals.",
            skillProficiencies = listOf(SkillName.SLEIGHT_OF_HAND, SkillName.STEALTH),
            toolProficiency = "Thieves' Tools",
            originFeat = "Alert",
        ),
        BackgroundDefinition(
            name = BackgroundName.ENTERTAINER,
            description = "You spent much of your youth following roving performers, minstrels, and entertainers, eventually learning the bytes yourself.",
            skillProficiencies = listOf(SkillName.ACROBATICS, SkillName.PERFORMANCE),
            toolProficiency = "Musical Instrument",
            originFeat = "Musician",
        ),
        BackgroundDefinition(
            name = BackgroundName.FARMER,
            description = "You grew up close to the land. Years of tending crops and animals have given you a robust constitution.",
            skillProficiencies = listOf(SkillName.ANIMAL_HANDLING, SkillName.NATURE),
            toolProficiency = "Carpenter's Tools",
            originFeat = "Tough",
        ),
        BackgroundDefinition(
            name = BackgroundName.GUARD,
            description = "You grew up on the streets of a bustling city and eventually joined the guard, keeping watch over the populace.",
            skillProficiencies = listOf(SkillName.ATHLETICS, SkillName.PERCEPTION),
            toolProficiency = "Gaming Set",
            originFeat = "Alert",
        ),
        BackgroundDefinition(
            name = BackgroundName.GUIDE,
            description = "You came of age in the wilderness, far from civilization. You've mapped trails, hunted game, and guided travelers through unforgiving terrain.",
            skillProficiencies = listOf(SkillName.STEALTH, SkillName.SURVIVAL),
            toolProficiency = "Cartographer's Tools",
            originFeat = "Magic Initiate (Druid)",
        ),
        BackgroundDefinition(
            name = BackgroundName.HERMIT,
            description = "You lived for a long time in seclusion, studying nature and the ways of healing, far from the comforts of a settlement.",
            skillProficiencies = listOf(SkillName.MEDICINE, SkillName.RELIGION),
            toolProficiency = "Herbalism Kit",
            originFeat = "Healer",
        ),
        BackgroundDefinition(
            name = BackgroundName.MERCHANT,
            description = "You ran a successful business, buying and selling goods across ports, cities, and frontier posts.",
            skillProficiencies = listOf(SkillName.ANIMAL_HANDLING, SkillName.PERSUASION),
            toolProficiency = "Navigator's Tools",
            originFeat = "Lucky",
        ),
        BackgroundDefinition(
            name = BackgroundName.NOBLE,
            description = "You were raised in a castle as part of a noble family. Your family gave you a fine education and prepared you for the world of courtly politics.",
            skillProficiencies = listOf(SkillName.HISTORY, SkillName.PERSUASION),
            toolProficiency = "Gaming Set",
            originFeat = "Skilled",
        ),
        BackgroundDefinition(
            name = BackgroundName.SAGE,
            description = "You spent your formative years traveling between libraries, absorbing knowledge on a wide range of subjects.",
            skillProficiencies = listOf(SkillName.ARCANA, SkillName.HISTORY),
            toolProficiency = "Calligrapher's Supplies",
            originFeat = "Magic Initiate (Wizard)",
        ),
        BackgroundDefinition(
            name = BackgroundName.SAILOR,
            description = "You spent years aboard a ship, working alongside seasoned sailors and learning the ways of the sea.",
            skillProficiencies = listOf(SkillName.ACROBATICS, SkillName.PERCEPTION),
            toolProficiency = "Navigator's Tools",
            originFeat = "Tavern Brawler",
        ),
        BackgroundDefinition(
            name = BackgroundName.SCRIBE,
            description = "You spent years copying documents and penning correspondence in a scriptorium, mastering the art of the written word.",
            skillProficiencies = listOf(SkillName.INVESTIGATION, SkillName.PERCEPTION),
            toolProficiency = "Calligrapher's Supplies",
            originFeat = "Skilled",
        ),
        BackgroundDefinition(
            name = BackgroundName.SOLDIER,
            description = "You began training for war at a young age, drilling with weapons and armor until both became second nature.",
            skillProficiencies = listOf(SkillName.ATHLETICS, SkillName.INTIMIDATION),
            toolProficiency = "Gaming Set",
            originFeat = "Savage Attacker",
        ),
        BackgroundDefinition(
            name = BackgroundName.WAYFARER,
            description = "You grew up on the road, traveling with merchants, circus performers, or a nomadic people. Life on the move taught you to fend for yourself.",
            skillProficiencies = listOf(SkillName.INSIGHT, SkillName.STEALTH),
            toolProficiency = "Thieves' Tools",
            originFeat = "Lucky",
        ),
    )
}
