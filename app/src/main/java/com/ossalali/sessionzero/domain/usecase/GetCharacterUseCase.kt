package com.ossalali.sessionzero.domain.usecase

import com.ossalali.sessionzero.data.repository.CharacterRepository
import com.ossalali.sessionzero.domain.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {
    operator fun invoke(id: String): Flow<Character?> {
        return repository.getCharacter(id)
    }
}
