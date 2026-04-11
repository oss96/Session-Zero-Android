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
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
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
        fun intVal(key: String) = obj[key]?.jsonPrimitive?.intOrNull ?: 0
        fun intOrNull(key: String) = obj[key]?.jsonPrimitive?.intOrNull

        val abilityBonuses = obj["abilityScoreBonuses"]?.jsonObject
            ?.mapNotNull { (k, v) ->
                runCatching { AbilityName.valueOf(k) }.getOrNull()
                    ?.let { it to v.jsonPrimitive.int }
            }?.toMap() ?: emptyMap()

        val skills = obj["skillProficiencies"]?.jsonArray
            ?.mapNotNull { runCatching { SkillName.valueOf(it.jsonPrimitive.content) }.getOrNull() }
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
            level = intVal("level").coerceIn(1, 20),
            xp = intVal("xp"),
            alignment = str("alignment").takeIf { it.isNotEmpty() }
                ?.let { runCatching { Alignment.valueOf(it) }.getOrNull() },
            className = str("className").takeIf { it.isNotEmpty() }
                ?.let { runCatching { ClassName.valueOf(it) }.getOrNull() },
            subclass = str("subclass").takeIf { it.isNotEmpty() },
            species = str("species").takeIf { it.isNotEmpty() }
                ?.let { runCatching { SpeciesName.valueOf(it) }.getOrNull() },
            speciesLineage = str("speciesLineage").takeIf { it.isNotEmpty() },
            background = str("background").takeIf { it.isNotEmpty() }
                ?.let { runCatching { BackgroundName.valueOf(it) }.getOrNull() },
            abilityScoreBonuses = abilityBonuses,
            originFeat = str("originFeat").takeIf { it.isNotEmpty() },
            baseStr = intVal("baseStr").coerceIn(1, 30),
            baseDex = intVal("baseDex").coerceIn(1, 30),
            baseCon = intVal("baseCon").coerceIn(1, 30),
            baseInt = intVal("baseInt").coerceIn(1, 30),
            baseWis = intVal("baseWis").coerceIn(1, 30),
            baseCha = intVal("baseCha").coerceIn(1, 30),
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
            feats = stringList("feats"),
            languages = stringList("languages"),
            knownCantrips = stringList("knownCantrips"),
            preparedSpells = stringList("preparedSpells"),
        )
    }
}
