package com.ossalali.sessionzero.domain.usecase

import com.ossalali.sessionzero.data.repository.CharacterRepository
import javax.inject.Inject

class DeleteCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {
    suspend operator fun invoke(id: String) {
        repository.deleteCharacter(id)
    }
}
