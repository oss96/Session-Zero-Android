package com.ossalali.sessionzero.ui.wizard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ossalali.sessionzero.ui.common.StepIndicator
import com.ossalali.sessionzero.ui.wizard.steps.AbilityScoresStep
import com.ossalali.sessionzero.ui.wizard.steps.BackgroundStep
import com.ossalali.sessionzero.ui.wizard.steps.ClassStep
import com.ossalali.sessionzero.ui.wizard.steps.DetailsStep
import com.ossalali.sessionzero.ui.wizard.steps.EquipmentStep
import com.ossalali.sessionzero.ui.wizard.steps.ReviewStep
import com.ossalali.sessionzero.ui.wizard.steps.SkillsStep
import com.ossalali.sessionzero.ui.wizard.steps.SpeciesStep
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(pageCount = { WizardViewModel.STEP_COUNT })

    LaunchedEffect(characterId) {
        if (characterId != null) {
            viewModel.loadCharacter(characterId)
        }
    }

    LaunchedEffect(currentStep) {
        pagerState.animateScrollToPage(currentStep)
    }

    LaunchedEffect(saveComplete) {
        if (saveComplete) onNavigateBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (characterId != null) "Edit Character" else "Create Character"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                if (currentStep > 0) {
                    OutlinedButton(
                        onClick = {
                            viewModel.previousStep()
                            scope.launch { pagerState.animateScrollToPage(currentStep - 1) }
                        },
                    ) {
                        Text("Previous")
                    }
                }
                Spacer(Modifier.weight(1f))
                if (currentStep < WizardViewModel.STEP_COUNT - 1) {
                    Button(
                        onClick = {
                            viewModel.nextStep()
                            scope.launch { pagerState.animateScrollToPage(currentStep + 1) }
                        },
                    ) {
                        Text("Next")
                    }
                }
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            StepIndicator(
                steps = WizardViewModel.STEP_LABELS,
                currentStep = currentStep,
            )

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.fillMaxSize(),
            ) { page ->
                when (page) {
                    0 -> ClassStep(character = character, viewModel = viewModel)
                    1 -> SpeciesStep(character = character, viewModel = viewModel)
                    2 -> BackgroundStep(character = character, viewModel = viewModel)
                    3 -> AbilityScoresStep(character = character, viewModel = viewModel)
                    4 -> SkillsStep(character = character, viewModel = viewModel)
                    5 -> EquipmentStep(character = character, viewModel = viewModel)
                    6 -> DetailsStep(character = character, viewModel = viewModel)
                    7 -> ReviewStep(
                        character = character,
                        derivedStats = derivedStats,
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}
