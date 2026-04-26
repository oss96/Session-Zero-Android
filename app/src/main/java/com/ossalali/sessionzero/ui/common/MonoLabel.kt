package com.ossalali.sessionzero.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.ossalali.sessionzero.ui.theme.LocalDuskTokens
import com.ossalali.sessionzero.ui.theme.LocalDuskTypography
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

/**
 * Small uppercase tracked-caps label in JetBrains Mono — used above form
 * fields and as section "kickers". Text is uppercased for the caller.
 */
@Composable
fun MonoLabel(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = LocalDuskTokens.current.textSub,
    style: TextStyle = LocalDuskTypography.current.monoLabel,
) {
    Text(
        modifier = modifier,
        text = text.uppercase(),
        style = style,
        color = color,
    )
}

@PreviewLightDark
@Composable
private fun MonoLabelPreview() {
    SessionZeroTheme {
        MonoLabel(text = "Calling")
    }
}
