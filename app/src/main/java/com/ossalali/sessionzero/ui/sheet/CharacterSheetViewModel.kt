package com.ossalali.sessionzero.ui.sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.usecase.ComputeDerivedStatsUseCase
import com.ossalali.sessionzero.domain.usecase.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterSheetViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val computeDerivedStats: ComputeDerivedStatsUseCase,
) : ViewModel() {

    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character

    private val _derivedStats = MutableStateFlow(DerivedStats())
    val derivedStats: StateFlow<DerivedStats> = _derivedStats

    val isLoading: StateFlow<Boolean> = _character
        .combine(MutableStateFlow(true)) { char, _ -> char == null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun loadCharacter(characterId: String) {
        viewModelScope.launch {
            getCharacterUseCase(characterId).collect { loaded ->
                _character.value = loaded
                if (loaded != null) {
                    _derivedStats.value = computeDerivedStats(loaded)
                }
            }
        }
    }
}
