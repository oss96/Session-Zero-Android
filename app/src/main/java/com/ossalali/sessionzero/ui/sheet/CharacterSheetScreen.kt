package com.ossalali.sessionzero.ui.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.DerivedStats
import com.ossalali.sessionzero.ui.common.DuskSegmentedControl
import com.ossalali.sessionzero.ui.common.MonoLabel
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

enum class SheetMode { Play, Showcase }

@Composable
fun CharacterSheetScreen(
    viewModel: CharacterSheetViewModel = hiltViewModel(),
    characterId: String,
    onNavigateBack: () -> Unit = {},
) {
    val character by viewModel.character.collectAsState()
    val derivedStats by viewModel.derivedStats.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(characterId) {
        viewModel.loadCharacter(characterId = characterId)
    }

    CharacterSheetContent(
        character = character,
        derivedStats = derivedStats,
        isLoading = isLoading,
        onNavigateBack = onNavigateBack,
    )
}

@Composable
fun CharacterSheetContent(
    character: Character? = null,
    derivedStats: DerivedStats = DerivedStats(),
    isLoading: Boolean = false,
    onNavigateBack: () -> Unit = {},
) {
    var mode by remember { mutableStateOf(value = SheetMode.Play) }

    Scaffold { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }
        val char = character ?: return@Scaffold

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding),
        ) {
            // Hero header (accent-soft for showcase mode, neutral for play mode)
            SheetHero(
                character = char,
                mode = mode,
                onNavigateBack = onNavigateBack,
            )

            // Mode toggle
            Box(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                DuskSegmentedControl(
                    options = listOf("Play", "Showcase"),
                    selectedIndex = mode.ordinal,
                    onSelect = { mode = SheetMode.entries[it] },
                )
            }

            // Body
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
                    .padding(horizontal = 14.dp)
                    .padding(bottom = 14.dp),
            ) {
                when (mode) {
                    SheetMode.Play -> SheetPlayBody(character = char, derivedStats = derivedStats)
                    SheetMode.Showcase -> SheetShowcaseBody(character = char, derivedStats = derivedStats)
                }
            }
        }
    }
}

@Composable
private fun SheetHero(
    character: Character,
    mode: SheetMode,
    onNavigateBack: () -> Unit,
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val heroBg: Brush = when (mode) {
        SheetMode.Showcase -> Brush.linearGradient(
            colors = listOf(tokens.accentSoft, scheme.background),
        )
        SheetMode.Play -> Brush.verticalGradient(
            colors = listOf(tokens.bgAlt, tokens.bgAlt),
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = heroBg)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
                Spacer(modifier = Modifier.weight(weight = 1f))
                IconButton(onClick = { /* no-op share */ }) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
                }
                IconButton(onClick = { /* no-op edit */ }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
            }
            Spacer(modifier = Modifier.height(height = 6.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Box(
                    modifier = Modifier
                        .size(size = 70.dp)
                        .clip(shape = RoundedCornerShape(size = 16.dp))
                        .background(color = scheme.primary),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = scheme.onPrimary,
                        modifier = Modifier.size(size = 34.dp),
                    )
                }
                Spacer(modifier = Modifier.width(width = 12.dp))
                Column(modifier = Modifier.weight(weight = 1f)) {
                    MonoLabel(
                        text = buildString {
                            append("Lv ${character.level}")
                            character.className?.let { append(" · ${it.displayName}") }
                            character.subclass?.let { append(" · $it") }
                        },
                        color = scheme.primary,
                    )
                    Text(
                        text = character.name.ifEmpty { "Unnamed" },
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                    )
                    Text(
                        text = listOfNotNull(
                            character.species?.displayName,
                            character.pronouns.ifEmpty { null },
                            character.alignment?.displayName,
                        ).joinToString(separator = " · "),
                        style = MaterialTheme.typography.bodySmall,
                        color = tokens.textDim,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CharacterSheetPreview() {
    SessionZeroTheme(dynamicColor = false) {
        CharacterSheetContent(
            character = PreviewData.sampleCharacter,
            derivedStats = PreviewData.sampleDerivedStats,
        )
    }
}

@PreviewLightDark
@Composable
private fun CharacterSheetCasterPreview() {
    SessionZeroTheme(dynamicColor = false) {
        CharacterSheetContent(
            character = PreviewData.sampleCasterCharacter,
            derivedStats = PreviewData.sampleCasterDerivedStats,
        )
    }
}

@PreviewLightDark
@Composable
private fun CharacterSheetLoadingPreview() {
    SessionZeroTheme(dynamicColor = false) {
        CharacterSheetContent(isLoading = true)
    }
}
