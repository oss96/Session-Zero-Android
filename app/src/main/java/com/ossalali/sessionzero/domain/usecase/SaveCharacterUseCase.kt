package com.ossalali.sessionzero.domain.usecase

import com.ossalali.sessionzero.data.repository.CharacterRepository
import com.ossalali.sessionzero.domain.model.Character
import javax.inject.Inject

class SaveCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {
    suspend operator fun invoke(character: Character) {
        repository.saveCharacter(character)
    }
}
