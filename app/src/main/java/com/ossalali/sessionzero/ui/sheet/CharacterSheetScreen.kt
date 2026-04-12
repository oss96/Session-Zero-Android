package com.ossalali.sessionzero.ui.sheet

import android.app.Activity
import android.content.Context
import android.print.PrintManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.print.CharacterPrintAdapter
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun CharacterSheetScreen(
    viewModel: CharacterSheetViewModel = hiltViewModel(),
    characterId: String,
    onNavigateBack: () -> Unit = {},
) {
    val character by viewModel.character.collectAsState()
    val derivedStats by viewModel.derivedStats.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(characterId) {
        viewModel.loadCharacter(characterId)
    }

    CharacterSheetContent(
        character = character,
        derivedStats = derivedStats,
        isLoading = isLoading,
        onNavigateBack = onNavigateBack,
        onPrint = {
            val char = character ?: return@CharacterSheetContent
            val activity = context as? Activity ?: return@CharacterSheetContent
            val printManager = activity.getSystemService(Context.PRINT_SERVICE) as PrintManager
            printManager.print(
                "${char.name.ifEmpty { "Character" }} - Session Zero",
                CharacterPrintAdapter(
                    activity = activity,
                    character = char,
                    derivedStats = derivedStats,
                ),
                null,
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterSheetContent(
    character: Character? = null,
    derivedStats: DerivedStats = DerivedStats(),
    isLoading: Boolean = false,
    onNavigateBack: () -> Unit = {},
    onPrint: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = character?.name ?: "Character Sheet") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                actions = {
                    if (character != null) {
                        IconButton(onClick = onPrint) {
                            Icon(
                                imageVector = Icons.Default.Print,
                                contentDescription = "Print character sheet",
                            )
                        }
                    }
                },
            )
        },
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            val char = character ?: return@Scaffold
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
                    .verticalScroll(state = rememberScrollState())
                    .padding(all = 16.dp),
            ) {
                SheetBody(character = char, derivedStats = derivedStats)
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CharacterSheetPreview() {
    SessionZeroTheme {
        CharacterSheetContent(
            character = PreviewData.sampleCharacter,
            derivedStats = PreviewData.sampleDerivedStats,
        )
    }
}

@PreviewLightDark
@Composable
private fun CharacterSheetCasterPreview() {
    SessionZeroTheme {
        CharacterSheetContent(
            character = PreviewData.sampleCasterCharacter,
            derivedStats = PreviewData.sampleCasterDerivedStats,
        )
    }
}

@PreviewLightDark
@Composable
private fun CharacterSheetLoadingPreview() {
    SessionZeroTheme {
        CharacterSheetContent(isLoading = true)
    }
}
