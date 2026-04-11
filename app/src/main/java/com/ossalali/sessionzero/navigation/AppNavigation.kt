package com.ossalali.sessionzero.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.ossalali.sessionzero.ui.dashboard.DashboardScreen
import com.ossalali.sessionzero.ui.wizard.WizardScreen

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(Dashboard)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<Dashboard> {
                DashboardScreen(
                    onCreateCharacter = { backStack.add(CreateWizard()) },
                    onEditCharacter = { id -> backStack.add(CreateWizard(characterId = id)) },
                    onViewSheet = { id -> backStack.add(CharacterSheet(characterId = id)) },
                )
            }
            entry<CreateWizard> { key ->
                WizardScreen(
                    characterId = key.characterId,
                    onNavigateBack = { backStack.removeLastOrNull() },
                )
            }
            entry<CharacterSheet> { key ->
                // TODO: Replace with CharacterSheetScreen in Phase 8
                Text("Sheet for ${key.characterId}")
            }
        }
    )
}
