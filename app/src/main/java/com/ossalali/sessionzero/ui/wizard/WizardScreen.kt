package com.ossalali.sessionzero.ui.wizard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.ui.common.ConfirmDialog
import com.ossalali.sessionzero.ui.common.StepIndicator
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme
import com.ossalali.sessionzero.ui.wizard.steps.AbilityScoresStep
import com.ossalali.sessionzero.ui.wizard.steps.BackgroundStep
import com.ossalali.sessionzero.ui.wizard.steps.ClassStep
import com.ossalali.sessionzero.ui.wizard.steps.DetailsStep
import com.ossalali.sessionzero.ui.wizard.steps.EquipmentStep
import com.ossalali.sessionzero.ui.wizard.steps.ReviewStep
import com.ossalali.sessionzero.ui.wizard.steps.SkillsStep
import com.ossalali.sessionzero.ui.wizard.steps.SpeciesStep
import kotlinx.coroutines.launch

@Composable
fun WizardScreen(
    viewModel: WizardViewModel = hiltViewModel(),
    characterId: String?,
    onNavigateBack: () -> Unit = {},
) {
    val character by viewModel.character.collectAsState()
    val currentStep by viewModel.currentStep.collectAsState()
    val derivedStats by viewModel.derivedStats.collectAsState()
    val saveComplete by viewModel.saveComplete.collectAsState()
    val isSaving by viewModel.isSaving.collectAsState()

    LaunchedEffect(characterId) {
        viewModel.initialize(characterId = characterId)
    }

    LaunchedEffect(saveComplete) {
        if (saveComplete) onNavigateBack()
    }

    WizardContent(
        title = if (characterId != null) "Edit Character" else "Create Character",
        character = character,
        currentStep = currentStep,
        checkUnsavedChanges = { viewModel.hasUnsavedChanges() },
        onNavigateBack = onNavigateBack,
        onStepClick = { viewModel.setStep(step = it) },
        onPrevious = { viewModel.previousStep() },
        onNext = { viewModel.nextStep() },
        stepContent = { page, pagerCharacter ->
            when (page) {
                0 -> ClassStep(
                    character = pagerCharacter,
                    onClassSelected = { viewModel.setClass(className = it) },
                    onLevelChanged = { viewModel.setLevel(level = it) },
                    onSubclassSelected = { viewModel.setSubclass(subclass = it) },
                )
                1 -> SpeciesStep(
                    character = pagerCharacter,
                    onSpeciesSelected = { viewModel.setSpecies(species = it) },
                    onLineageSelected = { viewModel.setSpeciesLineage(lineage = it) },
                )
                2 -> BackgroundStep(
                    character = pagerCharacter,
                    onBackgroundSelected = { viewModel.setBackground(background = it) },
                    onAbilityBonusesChanged = { viewModel.setAbilityScoreBonuses(bonuses = it) },
                )
                3 -> AbilityScoresStep(
                    character = pagerCharacter,
                    onMethodChanged = { viewModel.setAbilityScoreMethod(method = it) },
                    onBaseScoreChanged = { ability, value -> viewModel.setBaseAbilityScore(ability = ability, value = value) },
                    onAllScoresChanged = { s, d, c, i, w, ch -> viewModel.setAllBaseScores(str = s, dex = d, con = c, int = i, wis = w, cha = ch) },
                )
                4 -> SkillsStep(
                    character = pagerCharacter,
                    onToggleSkill = { viewModel.toggleSkillProficiency(skill = it) },
                )
                5 -> EquipmentStep(
                    character = pagerCharacter,
                    onEquipmentChoiceChanged = { viewModel.setEquipmentChoice(choice = it) },
                    onCoinsChanged = { viewModel.setCoins(coins = it) },
                    onEquipmentSet = { viewModel.setEquipment(items = it) },
                    onAddEquipmentItem = { viewModel.addEquipmentItem(item = it) },
                    onRemoveEquipmentItem = { viewModel.removeEquipmentItem(index = it) },
                )
                6 -> DetailsStep(
                    character = pagerCharacter,
                    onNameChanged = { viewModel.setName(name = it) },
                    onPronounsChanged = { viewModel.setPronouns(pronouns = it) },
                    onAlignmentChanged = { viewModel.setAlignment(alignment = it) },
                    onAppearanceChanged = { viewModel.setAppearance(appearance = it) },
                    onPersonalityTraitsChanged = { viewModel.setPersonalityTraits(value = it) },
                    onIdealsChanged = { viewModel.setIdeals(value = it) },
                    onBondsChanged = { viewModel.setBonds(value = it) },
                    onFlawsChanged = { viewModel.setFlaws(value = it) },
                    onBackstoryChanged = { viewModel.setBackstory(value = it) },
                    onAlliesChanged = { viewModel.setAlliesAndOrganizations(value = it) },
                    onNotesChanged = { viewModel.setAdditionalNotes(value = it) },
                )
                7 -> ReviewStep(
                    character = pagerCharacter,
                    derivedStats = derivedStats,
                    isSaving = isSaving,
                    onSave = { viewModel.saveCharacter() },
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WizardContent(
    title: String = "Create Character",
    character: Character = Character.empty(),
    currentStep: Int = 0,
    checkUnsavedChanges: () -> Boolean = { false },
    onNavigateBack: () -> Unit = {},
    onStepClick: (Int) -> Unit = {},
    onPrevious: () -> Unit = {},
    onNext: () -> Unit = {},
    stepContent: @Composable (page: Int, character: Character) -> Unit = { _, _ -> },
) {
    var showDiscardDialog by remember { mutableStateOf(false) }

    val tryNavigateBack: () -> Unit = {
        if (checkUnsavedChanges()) {
            showDiscardDialog = true
        } else {
            onNavigateBack()
        }
    }

    BackHandler {
        if (currentStep > 0) {
            onPrevious()
        } else {
            tryNavigateBack()
        }
    }

    val pagerState = rememberPagerState(
        initialPage = currentStep,
        pageCount = { WizardViewModel.STEP_COUNT },
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(currentStep) {
        pagerState.animateScrollToPage(currentStep)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = tryNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(all = 16.dp),
            ) {
                if (currentStep > 0) {
                    OutlinedButton(
                        onClick = {
                            onPrevious()
                            scope.launch { pagerState.animateScrollToPage(currentStep - 1) }
                        },
                    ) {
                        Text(text = "Previous")
                    }
                }
                Spacer(modifier = Modifier.weight(weight = 1f))
                if (currentStep < WizardViewModel.STEP_COUNT - 1) {
                    Button(
                        onClick = {
                            onNext()
                            scope.launch { pagerState.animateScrollToPage(currentStep + 1) }
                        },
                    ) {
                        Text(text = "Next")
                    }
                }
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding),
        ) {
            StepIndicator(
                steps = WizardViewModel.STEP_LABELS,
                currentStep = currentStep,
                onStepClick = { step ->
                    onStepClick(step)
                    scope.launch { pagerState.animateScrollToPage(step) }
                },
            )

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.fillMaxSize(),
                key = { it },
            ) { page ->
                key(character) {
                    stepContent(page, character)
                }
            }
        }
    }

    if (showDiscardDialog) {
        ConfirmDialog(
            title = "Unsaved Changes",
            message = "You have unsaved changes. Are you sure you want to discard them?",
            confirmText = "Discard",
            dismissText = "Cancel",
            onConfirm = {
                showDiscardDialog = false
                onNavigateBack()
            },
            onDismiss = { showDiscardDialog = false },
        )
    }
}

@PreviewLightDark
@Composable
private fun WizardScreenNewPreview() {
    SessionZeroTheme {
        WizardContent(
            title = "Create Character",
            currentStep = 0,
        )
    }
}

@PreviewLightDark
@Composable
private fun WizardScreenMidStepPreview() {
    SessionZeroTheme {
        WizardContent(
            title = "Edit Character",
            character = PreviewData.sampleCharacter,
            currentStep = 3,
        )
    }
}

@PreviewLightDark
@Composable
private fun WizardScreenLastStepPreview() {
    SessionZeroTheme {
        WizardContent(
            title = "Create Character",
            character = PreviewData.sampleCharacter,
            currentStep = 7,
        )
    }
}
