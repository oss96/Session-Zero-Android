package com.ossalali.sessionzero.domain.usecase

import com.ossalali.sessionzero.domain.model.Character
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.putJsonObject
import javax.inject.Inject

class ExportCharacterUseCase @Inject constructor() {

    private val json = Json { prettyPrint = true }

    operator fun invoke(character: Character): String {
        val jsonObj = buildJsonObject {
            put("id", character.id)
            put("name", character.name)
            put("pronouns", character.pronouns)
            put("level", character.level)
            put("xp", character.xp)
            put("alignment", character.alignment?.name ?: "")
            put("className", character.className?.name ?: "")
            put("subclass", character.subclass ?: "")
            put("species", character.species?.name ?: "")
            put("speciesLineage", character.speciesLineage ?: "")
            put("background", character.background?.name ?: "")
            putJsonObject("abilityScoreBonuses") {
                character.abilityScoreBonuses.forEach { (k, v) -> put(k.name, v) }
            }
            put("originFeat", character.originFeat ?: "")
            put("baseStr", character.baseStr)
            put("baseDex", character.baseDex)
            put("baseCon", character.baseCon)
            put("baseInt", character.baseInt)
            put("baseWis", character.baseWis)
            put("baseCha", character.baseCha)
            put("abilityScoreMethod", character.abilityScoreMethod ?: "")
            putJsonArray("skillProficiencies") {
                character.skillProficiencies.forEach { add(JsonPrimitive(it.name)) }
            }
            put("equipmentChoice", character.equipmentChoice ?: "")
            putJsonObject("coins") {
                put("cp", character.coins.cp)
                put("sp", character.coins.sp)
                put("gp", character.coins.gp)
                put("pp", character.coins.pp)
            }
            putJsonArray("equipment") {
                character.equipment.forEach { item ->
                    add(buildJsonObject {
                        put("name", item.name)
                        put("quantity", item.quantity)
                        put("description", item.description ?: "")
                    })
                }
            }
            putJsonArray("weapons") {
                character.weapons.forEach { weapon ->
                    add(buildJsonObject {
                        put("name", weapon.name)
                        put("attackBonus", weapon.attackBonus)
                        put("damage", weapon.damage)
                    })
                }
            }
            put("personalityTraits", character.personalityTraits)
            put("ideals", character.ideals)
            put("bonds", character.bonds)
            put("flaws", character.flaws)
            putJsonObject("appearance") {
                put("age", character.appearance.age)
                put("height", character.appearance.height)
                put("weight", character.appearance.weight)
                put("eyes", character.appearance.eyes)
                put("skin", character.appearance.skin)
                put("hair", character.appearance.hair)
                put("distinguishingMarks", character.appearance.distinguishingMarks)
                put("description", character.appearance.description)
            }
            put("backstory", character.backstory)
            put("alliesAndOrganizations", character.alliesAndOrganizations)
            put("additionalNotes", character.additionalNotes)
            put("portraitUrl", character.portraitUrl)
            putJsonArray("feats") { character.feats.forEach { add(JsonPrimitive(it)) } }
            putJsonArray("languages") { character.languages.forEach { add(JsonPrimitive(it)) } }
            putJsonArray("knownCantrips") { character.knownCantrips.forEach { add(JsonPrimitive(it)) } }
            putJsonArray("preparedSpells") { character.preparedSpells.forEach { add(JsonPrimitive(it)) } }
            put("createdAt", character.createdAt)
            put("updatedAt", character.updatedAt)
        }
        return json.encodeToString(jsonObj)
    }
}
