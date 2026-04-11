package com.ossalali.sessionzero.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

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
                // TODO: Replace with DashboardScreen
                Text("Dashboard - Coming Soon")
            }
            entry<CreateWizard> { key ->
                // TODO: Replace with WizardScreen
                Text("Wizard for ${key.characterId ?: "new character"}")
            }
            entry<CharacterSheet> { key ->
                // TODO: Replace with CharacterSheetScreen
                Text("Sheet for ${key.characterId}")
            }
        }
    )
}
