package com.ossalali.sessionzero.ui.wizard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ossalali.sessionzero.domain.model.AbilityName
import com.ossalali.sessionzero.domain.model.Alignment
import com.ossalali.sessionzero.domain.model.Appearance
import com.ossalali.sessionzero.domain.model.BackgroundName
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.ClassName
import com.ossalali.sessionzero.domain.model.Coins
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.domain.model.EquipmentItem
import com.ossalali.sessionzero.domain.model.SkillName
import com.ossalali.sessionzero.domain.model.SpeciesName
import com.ossalali.sessionzero.domain.model.Weapon
import com.ossalali.sessionzero.domain.usecase.ComputeDerivedStatsUseCase
import com.ossalali.sessionzero.domain.usecase.GetCharacterUseCase
import com.ossalali.sessionzero.domain.usecase.SaveCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WizardViewModel @Inject constructor(
    private val saveCharacterUseCase: SaveCharacterUseCase,
    private val getCharacterUseCase: GetCharacterUseCase,
    private val computeDerivedStats: ComputeDerivedStatsUseCase,
) : ViewModel() {

    private val _character = MutableStateFlow(Character.empty())
    val character: StateFlow<Character> = _character.asStateFlow()

    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _saveComplete = MutableStateFlow(false)
    val saveComplete: StateFlow<Boolean> = _saveComplete.asStateFlow()

    val derivedStats: StateFlow<DerivedStats> = _character
        .map { computeDerivedStats(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DerivedStats())

    fun initialize(characterId: String?) {
        _currentStep.value = 0
        _saveComplete.value = false
        _isSaving.value = false
        if (characterId != null) {
            viewModelScope.launch {
                getCharacterUseCase(characterId).collect { loaded ->
                    if (loaded != null) {
                        _character.value = loaded
                    }
                }
            }
        } else {
            _character.value = Character.empty()
        }
    }

    fun setStep(step: Int) {
        _currentStep.value = step.coerceIn(0, STEP_COUNT - 1)
    }

    fun nextStep() = setStep(_currentStep.value + 1)
    fun previousStep() = setStep(_currentStep.value - 1)

    // Class
    fun setClass(className: ClassName) {
        _character.update {
            it.copy(
                className = className,
                skillProficiencies = emptyList(),
                subclass = null,
                knownCantrips = emptyList(),
                preparedSpells = emptyList(),
                equipment = emptyList(),
                weapons = emptyList(),
                equipmentChoice = null,
            )
        }
    }

    fun setLevel(level: Int) {
        _character.update {
            it.copy(
                level = level.coerceIn(1, 20),
                subclass = if (level < 3) null else it.subclass,
            )
        }
    }

    fun setSubclass(subclass: String?) {
        _character.update { it.copy(subclass = subclass) }
    }

    // Species
    fun setSpecies(species: SpeciesName) {
        _character.update {
            it.copy(species = species, speciesLineage = null)
        }
    }

    fun setSpeciesLineage(lineage: String?) {
        _character.update { it.copy(speciesLineage = lineage) }
    }

    // Background
    fun setBackground(background: BackgroundName) {
        _character.update {
            it.copy(
                background = background,
                abilityScoreBonuses = emptyMap(),
            )
        }
    }

    fun setAbilityScoreBonuses(bonuses: Map<AbilityName, Int>) {
        _character.update { it.copy(abilityScoreBonuses = bonuses) }
    }

    fun setOriginFeat(feat: String?) {
        _character.update { it.copy(originFeat = feat) }
    }

    // Ability Scores
    fun setAbilityScoreMethod(method: String) {
        _character.update { it.copy(abilityScoreMethod = method) }
    }

    fun setBaseAbilityScore(ability: AbilityName, value: Int) {
        _character.update {
            when (ability) {
                AbilityName.STR -> it.copy(baseStr = value)
                AbilityName.DEX -> it.copy(baseDex = value)
                AbilityName.CON -> it.copy(baseCon = value)
                AbilityName.INT -> it.copy(baseInt = value)
                AbilityName.WIS -> it.copy(baseWis = value)
                AbilityName.CHA -> it.copy(baseCha = value)
            }
        }
    }

    fun setAllBaseScores(str: Int, dex: Int, con: Int, int: Int, wis: Int, cha: Int) {
        _character.update {
            it.copy(baseStr = str, baseDex = dex, baseCon = con, baseInt = int, baseWis = wis, baseCha = cha)
        }
    }

    // Skills
    fun toggleSkillProficiency(skill: SkillName) {
        _character.update {
            val current = it.skillProficiencies.toMutableList()
            if (skill in current) current.remove(skill) else current.add(skill)
            it.copy(skillProficiencies = current)
        }
    }

    // Equipment
    fun setEquipmentChoice(choice: String) {
        _character.update { it.copy(equipmentChoice = choice) }
    }

    fun setCoins(coins: Coins) {
        _character.update { it.copy(coins = coins) }
    }

    fun setEquipment(items: List<EquipmentItem>) {
        _character.update { it.copy(equipment = items) }
    }

    fun addEquipmentItem(item: EquipmentItem) {
        _character.update { it.copy(equipment = it.equipment + item) }
    }

    fun removeEquipmentItem(index: Int) {
        _character.update {
            it.copy(equipment = it.equipment.toMutableList().apply { removeAt(index) })
        }
    }

    // Weapons
    fun setWeapons(weapons: List<Weapon>) {
        _character.update { it.copy(weapons = weapons) }
    }

    fun addWeapon(weapon: Weapon) {
        _character.update { it.copy(weapons = it.weapons + weapon) }
    }

    fun removeWeapon(index: Int) {
        _character.update {
            it.copy(weapons = it.weapons.toMutableList().apply { removeAt(index) })
        }
    }

    // Details
    fun setName(name: String) {
        _character.update { it.copy(name = name) }
    }

    fun setPronouns(pronouns: String) {
        _character.update { it.copy(pronouns = pronouns) }
    }

    fun setAlignment(alignment: Alignment?) {
        _character.update { it.copy(alignment = alignment) }
    }

    fun setAppearance(appearance: Appearance) {
        _character.update { it.copy(appearance = appearance) }
    }

    fun setPersonalityTraits(value: String) {
        _character.update { it.copy(personalityTraits = value) }
    }

    fun setIdeals(value: String) {
        _character.update { it.copy(ideals = value) }
    }

    fun setBonds(value: String) {
        _character.update { it.copy(bonds = value) }
    }

    fun setFlaws(value: String) {
        _character.update { it.copy(flaws = value) }
    }

    fun setBackstory(value: String) {
        _character.update { it.copy(backstory = value) }
    }

    fun setAlliesAndOrganizations(value: String) {
        _character.update { it.copy(alliesAndOrganizations = value) }
    }

    fun setAdditionalNotes(value: String) {
        _character.update { it.copy(additionalNotes = value) }
    }

    fun setPortraitUrl(url: String) {
        _character.update { it.copy(portraitUrl = url) }
    }

    fun setFeats(feats: List<String>) {
        _character.update { it.copy(feats = feats) }
    }

    fun setLanguages(languages: List<String>) {
        _character.update { it.copy(languages = languages) }
    }

    fun setKnownCantrips(cantrips: List<String>) {
        _character.update { it.copy(knownCantrips = cantrips) }
    }

    fun setPreparedSpells(spells: List<String>) {
        _character.update { it.copy(preparedSpells = spells) }
    }

    // Save
    fun saveCharacter() {
        viewModelScope.launch {
            _isSaving.value = true
            saveCharacterUseCase(_character.value)
            _isSaving.value = false
            _saveComplete.value = true
        }
    }

    companion object {
        const val STEP_COUNT = 8
        val STEP_LABELS = listOf(
            "Class", "Species", "Background", "Abilities",
            "Skills", "Equipment", "Details", "Review"
        )
    }
}
