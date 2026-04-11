package com.ossalali.sessionzero.data.repository

import com.ossalali.sessionzero.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<List<Character>>
    fun getCharacter(id: String): Flow<Character?>
    suspend fun saveCharacter(character: Character)
    suspend fun deleteCharacter(id: String)
}
