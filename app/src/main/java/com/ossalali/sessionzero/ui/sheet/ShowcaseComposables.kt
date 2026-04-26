package com.ossalali.sessionzero.ui.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.ui.common.DuskTag
import com.ossalali.sessionzero.ui.common.DuskTagVariant
import com.ossalali.sessionzero.ui.common.MonoLabel
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens

/**
 * Italic "tale so far" card shown at the top of showcase mode.
 */
@Composable
fun ShowcaseTaleCard(text: String) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(color = scheme.surface)
            .border(
                width = 1.dp,
                color = scheme.outline,
                shape = RoundedCornerShape(size = 12.dp),
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
    ) {
        MonoLabel(text = "The tale so far", color = scheme.primary)
        Spacer(modifier = Modifier.height(height = 4.dp))
        Text(
            text = "\u201C$text\u201D",
            style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
            color = scheme.onSurface,
        )
    }
}

/**
 * Personality rows — left accent stripe + icon + trait. Rendered per
 * trait category (Traits/Ideals/Bonds/Flaws).
 */
@Composable
fun ShowcasePersonalityRows(character: Character) {
    val items = listOf(
        ShowcasePersonalityItem(label = "Traits", text = character.personalityTraits, icon = Icons.Default.Person),
        ShowcasePersonalityItem(label = "Ideals", text = character.ideals, icon = Icons.Default.Star),
        ShowcasePersonalityItem(label = "Bonds", text = character.bonds, icon = Icons.Default.Favorite),
        ShowcasePersonalityItem(label = "Flaws", text = character.flaws, icon = Icons.Default.Bolt),
    ).filter { it.text.isNotEmpty() }

    Column(verticalArrangement = Arrangement.spacedBy(space = 6.dp)) {
        items.forEach { item -> ShowcasePersonalityRow(item = item) }
    }
}

private data class ShowcasePersonalityItem(
    val label: String,
    val text: String,
    val icon: ImageVector,
)

@Composable
private fun ShowcasePersonalityRow(item: ShowcasePersonalityItem) {
    val scheme = MaterialTheme.colorScheme
    Row(modifier = Modifier.fillMaxWidth()) {
        // Left accent stripe
        Box(
            modifier = Modifier
                .width(width = 3.dp)
                .height(height = 56.dp)
                .background(color = scheme.primary),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = scheme.surface)
                .padding(horizontal = 12.dp, vertical = 10.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = scheme.primary,
                    modifier = Modifier.size(size = 14.dp),
                )
                Spacer(modifier = Modifier.width(width = 6.dp))
                MonoLabel(text = item.label, color = scheme.primary)
            }
            Spacer(modifier = Modifier.height(height = 3.dp))
            Text(
                text = item.text,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

/**
 * Equipment list rendered as rows with accent bullet dots inside a single
 * surface card.
 */
@Composable
fun ShowcaseEquipment(character: Character) {
    val scheme = MaterialTheme.colorScheme
    val tokens = LocalDuskTokens.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(color = scheme.surface)
            .border(
                width = 1.dp,
                color = scheme.outline,
                shape = RoundedCornerShape(size = 12.dp),
            ),
    ) {
        character.equipment.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(size = 6.dp)
                        .clip(shape = CircleShape)
                        .background(color = scheme.primary.copy(alpha = 0.6f)),
                )
                Spacer(modifier = Modifier.width(width = 10.dp))
                Text(
                    text = "${item.name}${if (item.quantity > 1) " ×${item.quantity}" else ""}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(weight = 1f),
                )
            }
            if (index < character.equipment.lastIndex) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 1.dp)
                        .background(color = scheme.outline),
                )
            }
        }
    }
}

@Composable
fun ShowcaseLanguages(languages: List<String>) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 6.dp),
    ) {
        languages.forEach { lang ->
            DuskTag(
                text = lang,
                variant = DuskTagVariant.Accent,
                modifier = Modifier.padding(bottom = 6.dp),
            )
        }
    }
}
