package com.ossalali.sessionzero.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Dashboard : NavKey

@Serializable
data class CreateWizard(val characterId: String? = null) : NavKey

@Serializable
data class CharacterSheet(val characterId: String) : NavKey
