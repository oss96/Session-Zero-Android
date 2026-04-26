package com.ossalali.sessionzero.ui.dashboard

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.ui.common.ConfirmDialog
import com.ossalali.sessionzero.ui.common.DuskTag
import com.ossalali.sessionzero.ui.common.DuskTagVariant
import com.ossalali.sessionzero.ui.common.MonoLabel
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
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
        contract = ActivityResultContracts.GetContent(),
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
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val snackbarHostState = remember { SnackbarHostState() }
    var deleteTarget by remember { mutableStateOf<Character?>(value = null) }

    LaunchedEffect(importError) {
        importError?.let {
            snackbarHostState.showSnackbar(message = it)
            onImportErrorShown()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateCharacter,
                shape = RoundedCornerShape(size = 18.dp),
                containerColor = scheme.primary,
                contentColor = scheme.onPrimary,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Create Character")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding),
        ) {
            // Hero header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = tokens.bgAlt)
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 18.dp),
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(weight = 1f)) {
                            MonoLabel(
                                text = "Session Zero",
                                color = scheme.primary,
                            )
                            Text(
                                text = if (characters.isEmpty()) "Welcome" else "Your party",
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        IconButton(onClick = onImportClick) {
                            Icon(
                                imageVector = Icons.Default.FileOpen,
                                contentDescription = "Import",
                                tint = scheme.onBackground,
                            )
                        }
                        IconButton(onClick = { /* no-op overflow menu */ }) {
                            Icon(
                                imageVector = Icons.Default.MoreHoriz,
                                contentDescription = "More",
                                tint = scheme.onBackground,
                            )
                        }
                    }
                    if (characters.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(height = 10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(space = 6.dp)) {
                            DuskTag(
                                text = "${characters.size} character${if (characters.size != 1) "s" else ""}",
                                variant = DuskTagVariant.Accent,
                            )
                            if (characters.isNotEmpty()) {
                                DuskTag(
                                    text = "1 active",
                                    variant = DuskTagVariant.Default,
                                )
                            }
                        }
                    }
                }
            }
            HorizontalDivider(color = scheme.outline)

            if (characters.isEmpty()) {
                EmptyState()
            } else {
                val featured = characters.first()
                val rest = characters.drop(n = 1)
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                ) {
                    item(key = "featured-${featured.id}") {
                        FeaturedCard(
                            character = featured,
                            onViewSheet = { onViewSheet(featured.id) },
                            onEdit = { onEditCharacter(featured.id) },
                            onExport = { onExportCharacter(featured) },
                            onDelete = { deleteTarget = featured },
                        )
                    }
                    if (rest.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(height = 6.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                MonoLabel(text = "Others")
                                Spacer(modifier = Modifier.width(width = 8.dp))
                                Box(
                                    modifier = Modifier
                                        .weight(weight = 1f)
                                        .height(height = 1.dp)
                                        .background(color = scheme.outline),
                                )
                            }
                        }
                    }
                    items(items = rest, key = { it.id }) { character ->
                        MiniCharCard(
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
    SessionZeroTheme(dynamicColor = false) {
        DashboardContent(characters = emptyList())
    }
}

@PreviewLightDark
@Composable
private fun DashboardWithCharactersPreview() {
    SessionZeroTheme(dynamicColor = false) {
        DashboardContent(
            characters = listOf(
                PreviewData.sampleCharacter,
                PreviewData.sampleCasterCharacter,
            ),
        )
    }
}
