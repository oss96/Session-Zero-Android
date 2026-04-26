package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.LocalDuskTypography
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme
import androidx.compose.foundation.background as backgroundMod

enum class DuskTagVariant { Default, Accent, Gold }

/**
 * Tiny uppercase mono pill. Three variants: [DuskTagVariant.Default] for
 * neutral tags, [DuskTagVariant.Accent] for primary/highlighted tags,
 * [DuskTagVariant.Gold] for secondary / "grants" tags.
 */
@Composable
fun DuskTag(
    modifier: Modifier = Modifier,
    text: String,
    variant: DuskTagVariant = DuskTagVariant.Default,
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme

    val (bg, fg, border) = when (variant) {
        DuskTagVariant.Default -> Triple(tokens.surface2, tokens.textDim, scheme.outline)
        DuskTagVariant.Accent -> Triple(tokens.accentSoft, scheme.primary, scheme.primary.copy(alpha = 0.26f))
        DuskTagVariant.Gold -> Triple(Color.Transparent, tokens.gold, tokens.gold.copy(alpha = 0.33f))
    }

    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .backgroundMod(color = bg, shape = CircleShape)
            .border(width = 1.dp, color = border, shape = CircleShape)
            .padding(horizontal = 8.dp, vertical = 3.dp),
    ) {
        Text(
            text = text.uppercase(),
            style = LocalDuskTypography.current.monoValue,
            color = fg,
        )
    }
}

@PreviewLightDark
@Composable
private fun DuskTagPreview() {
    SessionZeroTheme {
        Row(modifier = Modifier.padding(all = 12.dp)) {
            DuskTag(text = "All armor", modifier = Modifier.padding(end = 6.dp))
            DuskTag(text = "Hit die d10", variant = DuskTagVariant.Accent, modifier = Modifier.padding(end = 6.dp))
            DuskTag(text = "Savage", variant = DuskTagVariant.Gold)
        }
    }
}
