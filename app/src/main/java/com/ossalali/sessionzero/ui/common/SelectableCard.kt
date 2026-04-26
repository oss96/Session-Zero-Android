package com.ossalali.sessionzero.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

/**
 * Dusk "selectable card" — surface bg + 1dp outline by default; swaps to
 * 2dp primary border + accent-soft background when selected. Rounded 14dp
 * corners. Optionally clickable; if [onClick] is null the card is static.
 *
 * Content lambda receives a trailing `Box` so callers can layer whatever
 * content they like — usually a `Row` or `Column`.
 */
@Composable
fun SelectableCard(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    cornerRadius: Int = 14,
    contentPadding: Int = 12,
    content: @Composable () -> Unit,
) {
    val tokens = LocalDuskTokens.current
    val scheme = MaterialTheme.colorScheme
    val shape = RoundedCornerShape(size = cornerRadius.dp)

    val bg = if (selected) tokens.accentSoft else scheme.surface
    val border = if (selected) {
        BorderStroke(width = 2.dp, color = scheme.primary)
    } else {
        BorderStroke(width = 1.dp, color = scheme.outline)
    }

    val clickableMod = if (onClick != null) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .clip(shape = shape)
            .background(color = bg, shape = shape)
            .border(border = border, shape = shape)
            .then(other = clickableMod)
            .padding(all = contentPadding.dp),
    ) {
        content()
    }
}

@PreviewLightDark
@Composable
private fun SelectableCardPreview() {
    SessionZeroTheme {
        Box(modifier = Modifier.padding(all = 16.dp)) {
            SelectableCard(selected = true) {
                androidx.compose.material3.Text(text = "Selected card")
            }
        }
    }
}
