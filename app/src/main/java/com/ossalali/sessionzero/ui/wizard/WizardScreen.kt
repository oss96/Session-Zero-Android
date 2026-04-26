package com.ossalali.sessionzero.ui.wizard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.ui.common.ConfirmDialog
import com.ossalali.sessionzero.ui.common.DuskStepper
import com.ossalali.sessionzero.ui.common.MonoLabel
import com.ossalali.sessionzero.ui.common.WizardFooter
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
import com.ossalali.sessionzero.ui.wizard.steps.WeaponStep
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
        title = if (characterId != null) "Edit Character" else "New Character",
        character = character,
        currentStep = currentStep,
        checkUnsavedChanges = { viewModel.hasUnsavedChanges() },
        onNavigateBack = onNavigateBack,
        onStepClick = { viewModel.setStep(step = it) },
        onPrevious = { viewModel.previousStep() },
        onNext = { viewModel.nextStep() },
        isSaving = isSaving,
        onSave = { viewModel.saveCharacter() },
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
                6 -> WeaponStep(
                    character = pagerCharacter,
                    onWeaponsChanged = { viewModel.setWeapons(weapons = it) },
                )
                7 -> DetailsStep(
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
                8 -> ReviewStep(
                    character = pagerCharacter,
                    derivedStats = derivedStats,
                    onEditStep = { viewModel.setStep(step = it) },
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WizardContent(
    title: String = "New Character",
    character: Character = Character.empty(),
    currentStep: Int = 0,
    checkUnsavedChanges: () -> Boolean = { false },
    onNavigateBack: () -> Unit = {},
    onStepClick: (Int) -> Unit = {},
    onPrevious: () -> Unit = {},
    onNext: () -> Unit = {},
    isSaving: Boolean = false,
    onSave: () -> Unit = {},
    stepContent: @Composable (page: Int, character: Character) -> Unit = { _, _ -> },
) {
    var showDiscardDialog by remember { mutableStateOf(value = false) }

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
        pagerState.animateScrollToPage(page = currentStep)
    }

    val isLastStep = currentStep == WizardViewModel.STEP_COUNT - 1

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Spacer(modifier = Modifier.height(height = 2.dp))
                        MonoLabel(text = "Step ${currentStep + 1} of ${WizardViewModel.STEP_COUNT}")
                    }
                },
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
            WizardFooter(
                showPrevious = currentStep > 0,
                isLastStep = isLastStep,
                lastStepLabel = "Begin Adventure",
                isSaving = isSaving,
                saveEnabled = character.name.isNotBlank(),
                onPrevious = {
                    onPrevious()
                    scope.launch { pagerState.animateScrollToPage(page = currentStep - 1) }
                },
                onNext = {
                    onNext()
                    scope.launch { pagerState.animateScrollToPage(page = currentStep + 1) }
                },
                onSave = onSave,
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding),
        ) {
            DuskStepper(
                steps = WizardViewModel.STEP_LABELS,
                currentStep = currentStep,
                onStepClick = { step ->
                    onStepClick(step)
                    scope.launch { pagerState.animateScrollToPage(page = step) }
                },
            )
            Spacer(modifier = Modifier.height(height = 8.dp))

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.fillMaxSize(),
            ) { page ->
                stepContent(page, character)
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
    SessionZeroTheme(dynamicColor = false) {
        WizardContent(
            title = "New Character",
            currentStep = 0,
        )
    }
}

@PreviewLightDark
@Composable
private fun WizardScreenMidStepPreview() {
    SessionZeroTheme(dynamicColor = false) {
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
    SessionZeroTheme(dynamicColor = false) {
        WizardContent(
            title = "New Character",
            character = PreviewData.sampleCharacter,
            currentStep = 8,
        )
    }
}
