package com.ossalali.sessionzero.ui.sheet.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun HeaderSection(character: Character) {
    Column {
        Text(
            text = character.name.ifEmpty { "Unnamed Character" },
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        val subtitle = buildList {
            character.className?.let { add("Level ${character.level} ${it.displayName}") }
            character.subclass?.let { add(it) }
            character.species?.let { add(it.displayName) }
            character.background?.let { add(it.displayName) }
        }.joinToString(separator = " | ")
        if (subtitle.isNotEmpty()) {
            Text(text = subtitle, style = MaterialTheme.typography.bodyLarge)
        }
        character.alignment?.let {
            Text(text = it.displayName, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@PreviewLightDark
@Composable
private fun HeaderSectionPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 16.dp),
        ) {
            HeaderSection(character = PreviewData.sampleCharacter)
        }
    }
}
