package com.ossalali.sessionzero.ui.dashboard

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.ui.common.ConfirmDialog
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onCreateCharacter: () -> Unit = {},
    onEditCharacter: (String) -> Unit = {},
    onViewSheet: (String) -> Unit = {},
) {
    val characters by viewModel.characters.collectAsState()
    val importError by viewModel.importError.collectAsState()
    val context = LocalContext.current

    val importLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            context.contentResolver.openInputStream(it)?.use { stream ->
                val json = stream.bufferedReader().readText()
                viewModel.onImportCharacter(json)
            }
        }
    }

    DashboardContent(
        characters = characters,
        importError = importError,
        onCreateCharacter = onCreateCharacter,
        onEditCharacter = onEditCharacter,
        onViewSheet = onViewSheet,
        onImportClick = { importLauncher.launch("application/json") },
        onExportCharacter = { character ->
            val json = viewModel.onExportCharacter(character)
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, json)
                type = "application/json"
            }
            context.startActivity(Intent.createChooser(sendIntent, "Export Character"))
        },
        onDeleteCharacter = { viewModel.onDeleteCharacter(it) },
        onImportErrorShown = { viewModel.clearImportError() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    characters: List<Character>,
    importError: String? = null,
    onCreateCharacter: () -> Unit = {},
    onEditCharacter: (String) -> Unit = {},
    onViewSheet: (String) -> Unit = {},
    onImportClick: () -> Unit = {},
    onExportCharacter: (Character) -> Unit = {},
    onDeleteCharacter: (String) -> Unit = {},
    onImportErrorShown: () -> Unit = {},
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var deleteTarget by remember { mutableStateOf<Character?>(null) }

    LaunchedEffect(importError) {
        importError?.let {
            snackbarHostState.showSnackbar(it)
            onImportErrorShown()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Session Zero") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                actions = {
                    IconButton(onClick = onImportClick) {
                        Icon(Icons.Default.FileOpen, contentDescription = "Import Character")
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateCharacter) {
                Icon(Icons.Default.Add, contentDescription = "Create Character")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        if (characters.isEmpty()) {
            EmptyState(modifier = Modifier.padding(padding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(characters, key = { it.id }) { character ->
                    CharacterCard(
                        character = character,
                        onViewSheet = { onViewSheet(character.id) },
                        onEdit = { onEditCharacter(character.id) },
                        onExport = { onExportCharacter(character) },
                        onDelete = { deleteTarget = character },
                    )
                }
            }
        }
    }

    deleteTarget?.let { character ->
        ConfirmDialog(
            title = "Delete Character",
            message = "Are you sure you want to delete ${character.name.ifEmpty { "this character" }}?",
            onConfirm = {
                onDeleteCharacter(character.id)
                deleteTarget = null
            },
            onDismiss = { deleteTarget = null },
        )
    }
}

@PreviewLightDark
@Composable
private fun DashboardEmptyPreview() {
    SessionZeroTheme {
        DashboardContent(characters = emptyList())
    }
}

@PreviewLightDark
@Composable
private fun DashboardWithCharactersPreview() {
    SessionZeroTheme {
        DashboardContent(
            characters = listOf(
                PreviewData.sampleCharacter,
                PreviewData.sampleCasterCharacter,
            ),
        )
    }
}
