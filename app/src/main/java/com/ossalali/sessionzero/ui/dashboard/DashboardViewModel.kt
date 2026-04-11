package com.ossalali.sessionzero.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.usecase.DeleteCharacterUseCase
import com.ossalali.sessionzero.domain.usecase.ExportCharacterUseCase
import com.ossalali.sessionzero.domain.usecase.GetAllCharactersUseCase
import com.ossalali.sessionzero.domain.usecase.ImportCharacterUseCase
import com.ossalali.sessionzero.domain.usecase.SaveCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    getAllCharacters: GetAllCharactersUseCase,
    private val deleteCharacter: DeleteCharacterUseCase,
    private val exportCharacter: ExportCharacterUseCase,
    private val importCharacter: ImportCharacterUseCase,
    private val saveCharacter: SaveCharacterUseCase,
) : ViewModel() {

    val characters: StateFlow<List<Character>> = getAllCharacters()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _importError = MutableStateFlow<String?>(null)
    val importError: StateFlow<String?> = _importError.asStateFlow()

    fun onDeleteCharacter(id: String) {
        viewModelScope.launch {
            deleteCharacter(id)
        }
    }

    fun onExportCharacter(character: Character): String {
        return exportCharacter(character)
    }

    fun onImportCharacter(jsonString: String) {
        viewModelScope.launch {
            importCharacter(jsonString)
                .onSuccess { character ->
                    saveCharacter(character)
                    _importError.value = null
                }
                .onFailure { e ->
                    _importError.value = e.message ?: "Failed to import character"
                }
        }
    }

    fun clearImportError() {
        _importError.value = null
    }
}
