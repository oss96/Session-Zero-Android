package com.ossalali.sessionzero.ui.sheet.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ossalali.sessionzero.domain.model.Appearance
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun AppearanceSection(appearance: Appearance) {
    SectionHeader(text = "Appearance")
    if (appearance.age.isNotEmpty()) Text(text = "Age: ${appearance.age}")
    if (appearance.height.isNotEmpty()) Text(text = "Height: ${appearance.height} ${appearance.heightUnit}")
    if (appearance.weight.isNotEmpty()) Text(text = "Weight: ${appearance.weight} ${appearance.weightUnit}")
    if (appearance.eyes.isNotEmpty()) Text(text = "Eyes: ${appearance.eyes}")
    if (appearance.skin.isNotEmpty()) Text(text = "Skin: ${appearance.skin}")
    if (appearance.hair.isNotEmpty()) Text(text = "Hair: ${appearance.hair}")
    if (appearance.distinguishingMarks.isNotEmpty()) Text(text = "Marks: ${appearance.distinguishingMarks}")
}

@PreviewLightDark
@Composable
private fun AppearanceSectionPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 16.dp),
        ) {
            AppearanceSection(appearance = PreviewData.sampleCharacter.appearance)
        }
    }
}
