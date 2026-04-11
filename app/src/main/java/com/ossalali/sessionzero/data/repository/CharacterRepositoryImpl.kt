package com.ossalali.sessionzero.data.repository

import com.ossalali.sessionzero.data.local.dao.CharacterDao
import com.ossalali.sessionzero.data.mapper.CharacterMapper
import com.ossalali.sessionzero.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val dao: CharacterDao,
) : CharacterRepository {

    override fun getAllCharacters(): Flow<List<Character>> {
        return dao.getAllCharactersWithDetails().map { list ->
            list.map { CharacterMapper.toDomain(it) }
        }
    }

    override fun getCharacter(id: String): Flow<Character?> {
        return dao.getCharacterWithDetails(id).map { details ->
            details?.let { CharacterMapper.toDomain(it) }
        }
    }

    override suspend fun saveCharacter(character: Character) {
        val now = Instant.now().toString()
        val updated = character.copy(
            createdAt = character.createdAt.ifEmpty { now },
            updatedAt = now,
        )
        val entity = CharacterMapper.toEntity(updated)
        val equipmentEntities = CharacterMapper.toEquipmentEntities(updated.id, updated.equipment)
        val weaponEntities = CharacterMapper.toWeaponEntities(updated.id, updated.weapons)
        dao.saveCharacterFull(entity, equipmentEntities, weaponEntities)
    }

    override suspend fun deleteCharacter(id: String) {
        dao.deleteCharacter(id)
    }
}
