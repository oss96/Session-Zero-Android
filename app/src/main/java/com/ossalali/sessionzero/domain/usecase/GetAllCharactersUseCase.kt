package com.ossalali.sessionzero.domain.usecase

import com.ossalali.sessionzero.data.repository.CharacterRepository
import com.ossalali.sessionzero.domain.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {
    operator fun invoke(): Flow<List<Character>> {
        return repository.getAllCharacters()
    }
}
