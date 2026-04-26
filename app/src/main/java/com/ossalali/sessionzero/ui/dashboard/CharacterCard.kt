package com.ossalali.sessionzero.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Character
import com.ossalali.sessionzero.domain.model.ClassName
import com.ossalali.sessionzero.ui.common.MonoLabel
import com.ossalali.sessionzero.ui.common.SelectableCard
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

/**
 * Dashboard hero card — used for the first/featured character. Gradient
 * accent-soft → surface background with a large class icon watermark in
 * the corner and a 4-stat row.
 */
@Composable
fun FeaturedCard(
    modifier: Modifier = Modifier,
    character: Character,
    onViewSheet: () -> Unit = {},
    onEdit: () -> Unit = {},
    onExport: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(tokens.accentSoft, scheme.surface),
                ),
            )
            .border(
                width = 1.dp,
                color = scheme.primary.copy(alpha = 0.33f),
                shape = RoundedCornerShape(size = 16.dp),
            )
            .padding(all = 14.dp),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 44.dp)
                        .clip(shape = RoundedCornerShape(size = 12.dp))
                        .background(color = scheme.primary),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = classIconFor(className = character.className),
                        contentDescription = null,
                        tint = scheme.onPrimary,
                        modifier = Modifier.size(size = 22.dp),
                    )
                }
                Spacer(modifier = Modifier.width(width = 10.dp))
                Column(modifier = Modifier.weight(weight = 1f)) {
                    MonoLabel(
                        text = "Lv ${character.level} · ${character.className?.displayName ?: "Adventurer"}",
                        color = scheme.primary,
                    )
                    Text(
                        text = character.name.ifEmpty { "Unnamed" },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                    )
                }
                IconButton(onClick = onViewSheet) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "View",
                        tint = tokens.textDim,
                    )
                }
            }
            Spacer(modifier = Modifier.height(height = 6.dp))
            Text(
                text = listOfNotNull(
                    character.species?.displayName,
                    character.background?.displayName,
                    character.alignment?.displayName,
                ).joinToString(separator = " · ").ifEmpty { "—" },
                style = MaterialTheme.typography.bodySmall,
                color = tokens.textDim,
            )
            Spacer(modifier = Modifier.height(height = 10.dp))

            // 4-stat row (placeholder values; actual stats lives in character sheet)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(space = 5.dp),
            ) {
                StatSquare(modifier = Modifier.weight(weight = 1f), label = "Lv", value = "${character.level}")
                StatSquare(
                    modifier = Modifier.weight(weight = 1f),
                    label = "Class",
                    value = character.className?.displayName?.take(n = 4) ?: "—",
                )
                StatSquare(
                    modifier = Modifier.weight(weight = 1f),
                    label = "Skills",
                    value = "${character.skillProficiencies.size}",
                )
                StatSquare(
                    modifier = Modifier.weight(weight = 1f),
                    label = "Items",
                    value = "${character.equipment.size}",
                )
            }

            Spacer(modifier = Modifier.height(height = 10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = tokens.textDim,
                    )
                }
                IconButton(onClick = onExport) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Export",
                        tint = tokens.textDim,
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = tokens.textDim,
                    )
                }
            }
        }
    }
}

/**
 * Compact list-row variant — used for every character after the first.
 */
@Composable
fun MiniCharCard(
    modifier: Modifier = Modifier,
    character: Character,
    onViewSheet: () -> Unit = {},
    onEdit: () -> Unit = {},
    onExport: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme

    SelectableCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onViewSheet,
        cornerRadius = 12,
        contentPadding = 10,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(size = 36.dp)
                    .clip(shape = RoundedCornerShape(size = 9.dp))
                    .background(color = tokens.surface2),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = classIconFor(className = character.className),
                    contentDescription = null,
                    tint = scheme.primary,
                    modifier = Modifier.size(size = 18.dp),
                )
            }
            Spacer(modifier = Modifier.width(width = 10.dp))
            Column(modifier = Modifier.weight(weight = 1f)) {
                Text(
                    text = character.name.ifEmpty { "Unnamed" },
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                )
                Text(
                    text = listOfNotNull(
                        character.className?.displayName?.let { "Lv${character.level} $it" },
                        character.species?.displayName,
                    ).joinToString(separator = " · "),
                    style = MaterialTheme.typography.bodySmall,
                    color = tokens.textDim,
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                MonoLabel(text = "Skills")
                Text(
                    text = "${character.skillProficiencies.size}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                )
            }
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = tokens.textDim,
                    modifier = Modifier.size(size = 18.dp),
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = tokens.textDim,
                    modifier = Modifier.size(size = 18.dp),
                )
            }
        }
    }
}

@Composable
private fun StatSquare(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
) {
    val scheme = MaterialTheme.colorScheme
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .background(color = scheme.background)
            .border(
                width = 1.dp,
                color = scheme.outline,
                shape = RoundedCornerShape(size = 10.dp),
            )
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MonoLabel(text = label)
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
        )
    }
}

private fun classIconFor(className: ClassName?): ImageVector = when (className) {
    ClassName.SORCERER, ClassName.WIZARD, ClassName.WARLOCK, ClassName.BARD -> Icons.Default.AutoAwesome
    else -> Icons.Default.Bolt
}

// Legacy alias used by DashboardContent list rows during migration.
@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    character: Character,
    onViewSheet: () -> Unit = {},
    onEdit: () -> Unit = {},
    onExport: () -> Unit = {},
    onDelete: () -> Unit = {},
) = MiniCharCard(
    modifier = modifier,
    character = character,
    onViewSheet = onViewSheet,
    onEdit = onEdit,
    onExport = onExport,
    onDelete = onDelete,
)

@PreviewLightDark
@Composable
private fun FeaturedCardPreview() {
    SessionZeroTheme(dynamicColor = false) {
        FeaturedCard(
            modifier = Modifier.padding(all = 16.dp),
            character = PreviewData.sampleCharacter,
        )
    }
}

@PreviewLightDark
@Composable
private fun MiniCharCardPreview() {
    SessionZeroTheme(dynamicColor = false) {
        MiniCharCard(
            modifier = Modifier.padding(all = 16.dp),
            character = PreviewData.sampleCasterCharacter,
        )
    }
}
