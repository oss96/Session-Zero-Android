package com.ossalali.sessionzero.data.mapper

import com.ossalali.sessionzero.data.local.entity.CharacterEntity
import com.ossalali.sessionzero.data.local.entity.EquipmentItemEntity
import com.ossalali.sessionzero.data.local.entity.WeaponEntity
import com.ossalali.sessionzero.data.local.relation.CharacterWithDetails
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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CharacterMapper {

    private val json = Json { ignoreUnknownKeys = true }

    fun toDomain(details: CharacterWithDetails): Character {
        val entity = details.character
        return Character(
            id = entity.id,
            name = entity.name,
            pronouns = entity.pronouns,
            level = entity.level,
            xp = entity.xp,
            alignment = entity.alignment.takeIf { it.isNotEmpty() }
                ?.let { runCatching { Alignment.valueOf(it) }.getOrNull() },
            className = entity.className
                ?.let { runCatching { ClassName.valueOf(it) }.getOrNull() },
            subclass = entity.subclass,
            species = entity.species
                ?.let { runCatching { SpeciesName.valueOf(it) }.getOrNull() },
            speciesLineage = entity.speciesLineage,
            background = entity.background
                ?.let { runCatching { BackgroundName.valueOf(it) }.getOrNull() },
            abilityScoreBonuses = parseAbilityBonuses(entity.abilityScoreBonuses),
            originFeat = entity.originFeat,
            baseStr = entity.baseStr,
            baseDex = entity.baseDex,
            baseCon = entity.baseCon,
            baseInt = entity.baseInt,
            baseWis = entity.baseWis,
            baseCha = entity.baseCha,
            abilityScoreMethod = entity.abilityScoreMethod,
            skillProficiencies = parseSkillList(entity.skillProficiencies),
            equipmentChoice = entity.equipmentChoice,
            coins = Coins(
                cp = entity.coinsCp,
                sp = entity.coinsSp,
                gp = entity.coinsGp,
                pp = entity.coinsPp,
            ),
            equipment = details.equipment
                .sortedBy { it.sortOrder }
                .map { EquipmentItem(it.name, it.quantity, it.description) },
            weapons = details.weapons
                .sortedBy { it.sortOrder }
                .map { Weapon(it.name, it.attackBonus, it.damage) },
            personalityTraits = entity.personalityTraits,
            ideals = entity.ideals,
            bonds = entity.bonds,
            flaws = entity.flaws,
            appearance = parseAppearance(entity.appearance),
            backstory = entity.backstory,
            alliesAndOrganizations = entity.alliesAndOrganizations,
            additionalNotes = entity.additionalNotes,
            portraitUrl = entity.portraitUrl,
            feats = parseStringList(entity.feats),
            languages = parseStringList(entity.languages),
            knownCantrips = parseStringList(entity.knownCantrips),
            preparedSpells = parseStringList(entity.preparedSpells),
            hpOverride = entity.hpOverride,
            acOverride = entity.acOverride,
            spellSaveDCOverride = entity.spellSaveDCOverride,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
        )
    }

    fun toEntity(character: Character): CharacterEntity {
        return CharacterEntity(
            id = character.id,
            name = character.name,
            pronouns = character.pronouns,
            level = character.level,
            xp = character.xp,
            alignment = character.alignment?.name ?: "",
            className = character.className?.name,
            subclass = character.subclass,
            species = character.species?.name,
            speciesLineage = character.speciesLineage,
            background = character.background?.name,
            abilityScoreBonuses = serializeAbilityBonuses(character.abilityScoreBonuses),
            originFeat = character.originFeat,
            baseStr = character.baseStr,
            baseDex = character.baseDex,
            baseCon = character.baseCon,
            baseInt = character.baseInt,
            baseWis = character.baseWis,
            baseCha = character.baseCha,
            abilityScoreMethod = character.abilityScoreMethod,
            skillProficiencies = json.encodeToString(character.skillProficiencies.map { it.name }),
            equipmentChoice = character.equipmentChoice,
            coinsCp = character.coins.cp,
            coinsSp = character.coins.sp,
            coinsGp = character.coins.gp,
            coinsPp = character.coins.pp,
            personalityTraits = character.personalityTraits,
            ideals = character.ideals,
            bonds = character.bonds,
            flaws = character.flaws,
            appearance = serializeAppearance(character.appearance),
            backstory = character.backstory,
            alliesAndOrganizations = character.alliesAndOrganizations,
            additionalNotes = character.additionalNotes,
            portraitUrl = character.portraitUrl,
            feats = json.encodeToString(character.feats),
            languages = json.encodeToString(character.languages),
            knownCantrips = json.encodeToString(character.knownCantrips),
            preparedSpells = json.encodeToString(character.preparedSpells),
            hpOverride = character.hpOverride,
            acOverride = character.acOverride,
            spellSaveDCOverride = character.spellSaveDCOverride,
            createdAt = character.createdAt,
            updatedAt = character.updatedAt,
        )
    }

    fun toEquipmentEntities(characterId: String, items: List<EquipmentItem>): List<EquipmentItemEntity> {
        return items.mapIndexed { index, item ->
            EquipmentItemEntity(
                characterId = characterId,
                name = item.name,
                quantity = item.quantity,
                description = item.description,
                sortOrder = index,
            )
        }
    }

    fun toWeaponEntities(characterId: String, weapons: List<Weapon>): List<WeaponEntity> {
        return weapons.mapIndexed { index, weapon ->
            WeaponEntity(
                characterId = characterId,
                name = weapon.name,
                attackBonus = weapon.attackBonus,
                damage = weapon.damage,
                sortOrder = index,
            )
        }
    }

    private fun parseAbilityBonuses(value: String): Map<AbilityName, Int> {
        if (value.isBlank() || value == "{}") return emptyMap()
        val raw: Map<String, Int> = json.decodeFromString(value)
        return raw.mapNotNull { (key, v) ->
            runCatching { AbilityName.valueOf(key) }.getOrNull()?.let { it to v }
        }.toMap()
    }

    private fun serializeAbilityBonuses(bonuses: Map<AbilityName, Int>): String {
        val raw = bonuses.map { (k, v) -> k.name to v }.toMap()
        return json.encodeToString(raw)
    }

    private fun parseSkillList(value: String): List<SkillName> {
        if (value.isBlank() || value == "[]") return emptyList()
        val raw: List<String> = json.decodeFromString(value)
        return raw.mapNotNull { runCatching { SkillName.valueOf(it) }.getOrNull() }
    }

    private fun parseStringList(value: String): List<String> {
        if (value.isBlank() || value == "[]") return emptyList()
        return json.decodeFromString(value)
    }

    private fun parseAppearance(value: String): Appearance {
        if (value.isBlank() || value == "{}") return Appearance()
        return runCatching { json.decodeFromString<AppearanceJson>(value) }.getOrNull()
            ?.toAppearance() ?: Appearance()
    }

    private fun serializeAppearance(appearance: Appearance): String {
        return json.encodeToString(AppearanceJson.from(appearance))
    }

    @kotlinx.serialization.Serializable
    private data class AppearanceJson(
        val age: String = "",
        val height: String = "",
        val weight: String = "",
        val eyes: String = "",
        val skin: String = "",
        val hair: String = "",
        val distinguishingMarks: String = "",
        val description: String = "",
    ) {
        fun toAppearance() = Appearance(age, height, weight, eyes, skin, hair, distinguishingMarks, description)

        companion object {
            fun from(a: Appearance) = AppearanceJson(
                a.age, a.height, a.weight, a.eyes, a.skin, a.hair, a.distinguishingMarks, a.description
            )
        }
    }
}
