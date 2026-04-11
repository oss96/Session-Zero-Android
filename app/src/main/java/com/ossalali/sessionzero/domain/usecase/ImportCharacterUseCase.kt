package com.ossalali.sessionzero.domain.usecase

import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.Alignment
import com.ossalali.sessionzero.domain.model.Appearance
import com.ossalali.sessionzero.domain.model.BackgroundName
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.ClassName
import com.ossalali.sessionzero.domain.model.Coins
import com.ossalali.sessionzero.domain.model.EquipmentItem
import com.ossalali.sessionzero.domain.model.SkillName
import com.ossalali.sessionzero.domain.model.SpeciesName
import com.ossalali.sessionzero.domain.model.Weapon
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.util.UUID
import javax.inject.Inject

class ImportCharacterUseCase @Inject constructor() {

    private val json = Json { ignoreUnknownKeys = true }

    operator fun invoke(jsonString: String): Result<Character> = runCatching {
        val obj = json.parseToJsonElement(jsonString).jsonObject

        fun str(key: String) = obj[key]?.jsonPrimitive?.content ?: ""
        fun intVal(key: String, default: Int = 0) = obj[key]?.jsonPrimitive?.intOrNull ?: default

        // Ability scores: support both flat fields (baseStr) and nested object (baseAbilityScores)
        val baseScoresObj = obj["baseAbilityScores"]?.jsonObject
        fun abilityScore(flatKey: String, abilityKey: String, default: Int = 10): Int {
            return obj[flatKey]?.jsonPrimitive?.intOrNull
                ?: baseScoresObj?.get(abilityKey)?.jsonPrimitive?.intOrNull
                ?: default
        }

        val abilityBonuses = obj["abilityScoreBonuses"]?.jsonObject
            ?.mapNotNull { (k, v) ->
                parseEnum<AbilityName>(k)?.let { it to v.jsonPrimitive.intOrNull } as? Pair<AbilityName, Int>
            }?.toMap() ?: emptyMap()

        val skills = obj["skillProficiencies"]?.jsonArray
            ?.mapNotNull { parseSkill(it.jsonPrimitive.content) }
            ?: emptyList()

        val coins = obj["coins"]?.jsonObject?.let { c ->
            Coins(
                cp = c["cp"]?.jsonPrimitive?.intOrNull ?: 0,
                sp = c["sp"]?.jsonPrimitive?.intOrNull ?: 0,
                gp = c["gp"]?.jsonPrimitive?.intOrNull ?: 0,
                pp = c["pp"]?.jsonPrimitive?.intOrNull ?: 0,
            )
        } ?: Coins()

        val equipment = obj["equipment"]?.jsonArray?.map { e ->
            val o = e.jsonObject
            EquipmentItem(
                name = o["name"]?.jsonPrimitive?.content ?: "",
                quantity = o["quantity"]?.jsonPrimitive?.intOrNull ?: 1,
                description = o["description"]?.jsonPrimitive?.content?.takeIf { it.isNotEmpty() },
            )
        } ?: emptyList()

        val weapons = obj["weapons"]?.jsonArray?.map { w ->
            val o = w.jsonObject
            Weapon(
                name = o["name"]?.jsonPrimitive?.content ?: "",
                attackBonus = o["attackBonus"]?.jsonPrimitive?.content ?: "",
                damage = o["damage"]?.jsonPrimitive?.content ?: "",
            )
        } ?: emptyList()

        val appearance = obj["appearance"]?.jsonObject?.let { a ->
            Appearance(
                age = a["age"]?.jsonPrimitive?.content ?: "",
                height = a["height"]?.jsonPrimitive?.content ?: "",
                weight = a["weight"]?.jsonPrimitive?.content ?: "",
                eyes = a["eyes"]?.jsonPrimitive?.content ?: "",
                skin = a["skin"]?.jsonPrimitive?.content ?: "",
                hair = a["hair"]?.jsonPrimitive?.content ?: "",
                distinguishingMarks = a["distinguishingMarks"]?.jsonPrimitive?.content ?: "",
                description = a["description"]?.jsonPrimitive?.content ?: "",
            )
        } ?: Appearance()

        fun stringList(key: String) = obj[key]?.jsonArray
            ?.map { it.jsonPrimitive.content } ?: emptyList()

        Character(
            id = UUID.randomUUID().toString(),
            name = str("name"),
            pronouns = str("pronouns"),
            level = intVal(key = "level", default = 1).coerceIn(1, 20),
            xp = intVal(key = "xp"),
            alignment = str("alignment").takeIf { it.isNotEmpty() }
                ?.let { parseEnum<Alignment>(it) },
            className = str("className").takeIf { it.isNotEmpty() }
                ?.let { parseClassName(it) },
            subclass = str("subclass").takeIf { it.isNotEmpty() },
            species = str("species").takeIf { it.isNotEmpty() }
                ?.let { parseSpeciesName(it) },
            speciesLineage = str("speciesLineage").takeIf { it.isNotEmpty() },
            background = str("background").takeIf { it.isNotEmpty() }
                ?.let { parseBackgroundName(it) },
            abilityScoreBonuses = abilityBonuses,
            originFeat = str("originFeat").takeIf { it.isNotEmpty() },
            baseStr = abilityScore(flatKey = "baseStr", abilityKey = "STR").coerceIn(1, 30),
            baseDex = abilityScore(flatKey = "baseDex", abilityKey = "DEX").coerceIn(1, 30),
            baseCon = abilityScore(flatKey = "baseCon", abilityKey = "CON").coerceIn(1, 30),
            baseInt = abilityScore(flatKey = "baseInt", abilityKey = "INT").coerceIn(1, 30),
            baseWis = abilityScore(flatKey = "baseWis", abilityKey = "WIS").coerceIn(1, 30),
            baseCha = abilityScore(flatKey = "baseCha", abilityKey = "CHA").coerceIn(1, 30),
            abilityScoreMethod = str("abilityScoreMethod").takeIf { it.isNotEmpty() },
            skillProficiencies = skills,
            equipmentChoice = str("equipmentChoice").takeIf { it.isNotEmpty() },
            coins = coins,
            equipment = equipment,
            weapons = weapons,
            personalityTraits = str("personalityTraits"),
            ideals = str("ideals"),
            bonds = str("bonds"),
            flaws = str("flaws"),
            appearance = appearance,
            backstory = str("backstory"),
            alliesAndOrganizations = str("alliesAndOrganizations"),
            additionalNotes = str("additionalNotes"),
            portraitUrl = str("portraitUrl"),
            hpOverride = obj["hpOverride"]?.jsonPrimitive?.intOrNull,
            acOverride = obj["acOverride"]?.jsonPrimitive?.intOrNull,
            spellSaveDCOverride = obj["spellSaveDCOverride"]?.jsonPrimitive?.intOrNull,
            feats = stringList("feats"),
            languages = stringList("languages"),
            knownCantrips = stringList("knownCantrips"),
            preparedSpells = stringList("preparedSpells"),
        )
    }

    private inline fun <reified T : Enum<T>> parseEnum(value: String): T? {
        return enumValues<T>().firstOrNull {
            it.name.equals(value, ignoreCase = true)
        }
    }

    private fun parseClassName(value: String): ClassName? {
        return parseEnum<ClassName>(value)
            ?: ClassName.entries.firstOrNull { it.displayName.equals(value, ignoreCase = true) }
    }

    private fun parseSpeciesName(value: String): SpeciesName? {
        return parseEnum<SpeciesName>(value)
            ?: SpeciesName.entries.firstOrNull { it.displayName.equals(value, ignoreCase = true) }
    }

    private fun parseBackgroundName(value: String): BackgroundName? {
        return parseEnum<BackgroundName>(value)
            ?: BackgroundName.entries.firstOrNull { it.displayName.equals(value, ignoreCase = true) }
    }

    private fun parseSkill(value: String): SkillName? {
        return parseEnum<SkillName>(value)
            ?: SkillName.entries.firstOrNull { it.displayName.equals(value, ignoreCase = true) }
    }
}
