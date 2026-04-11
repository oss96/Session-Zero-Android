package com.ossalali.sessionzero.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.ossalali.sessionzero.ui.dashboard.DashboardScreen
import com.ossalali.sessionzero.ui.sheet.CharacterSheetScreen
import com.ossalali.sessionzero.ui.wizard.WizardScreen

private const val ANIM_DURATION = 350

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(Dashboard)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
        ),
        transitionSpec = {
            (slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(durationMillis = ANIM_DURATION),
            ) + fadeIn(
                animationSpec = tween(durationMillis = ANIM_DURATION),
            )) togetherWith (slideOutHorizontally(
                targetOffsetX = { -it / 3 },
                animationSpec = tween(durationMillis = ANIM_DURATION),
            ) + fadeOut(
                animationSpec = tween(durationMillis = ANIM_DURATION / 2),
            ))
        },
        popTransitionSpec = {
            (slideInHorizontally(
                initialOffsetX = { -it / 3 },
                animationSpec = tween(durationMillis = ANIM_DURATION),
            ) + fadeIn(
                animationSpec = tween(durationMillis = ANIM_DURATION),
            )) togetherWith (slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(durationMillis = ANIM_DURATION),
            ) + fadeOut(
                animationSpec = tween(durationMillis = ANIM_DURATION / 2),
            ))
        },
        predictivePopTransitionSpec = {
            (slideInHorizontally(
                initialOffsetX = { -it / 3 },
                animationSpec = tween(durationMillis = ANIM_DURATION),
            ) + fadeIn(
                animationSpec = tween(durationMillis = ANIM_DURATION),
            )) togetherWith (slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(durationMillis = ANIM_DURATION),
            ) + fadeOut(
                animationSpec = tween(durationMillis = ANIM_DURATION / 2),
            ))
        },
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
                CharacterSheetScreen(
                    characterId = key.characterId,
                    onNavigateBack = { backStack.removeLastOrNull() },
                )
            }
        },
    )
}
