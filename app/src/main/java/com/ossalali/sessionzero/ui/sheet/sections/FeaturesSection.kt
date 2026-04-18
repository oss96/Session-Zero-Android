package com.ossalali.sessionzero.ui.sheet.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.ossalali.sessionzero.domain.model.ClassDefinition
import com.ossalali.sessionzero.domain.model.ClassFeature
import com.ossalali.sessionzero.domain.rules.ClassData
import com.ossalali.sessionzero.ui.common.SectionHeader
import com.ossalali.sessionzero.ui.preview.PreviewData
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme

@Composable
fun FeaturesSection(
    classDef: ClassDefinition,
    level: Int,
    subclassName: String? = null,
) {
    val subclassDef = subclassName?.let { name ->
        classDef.subclasses.find { it.name == name }
    }

    val classFeatures = classDef.features
        .filter { it.key <= level }
        .flatMap { it.value }

    val subclassFeatures = subclassDef?.let { sub ->
        val entry = ClassFeature(
            name = sub.name,
            description = sub.description,
            level = classDef.subclassLevel,
        )
        val levelFeatures = sub.features
            .filter { it.key <= level }
            .flatMap { it.value }
        listOf(entry) + levelFeatures
    }.orEmpty()

    val allFeatures = classFeatures + subclassFeatures
    if (allFeatures.isEmpty()) return

    SectionHeader(text = "Features & Traits")
    allFeatures.forEach { feature ->
        Text(
            text = feature.name,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
        )
        Text(text = feature.description, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(height = 4.dp))
    }
}

@PreviewLightDark
@Composable
private fun FeaturesSectionPreview() {
    SessionZeroTheme {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(all = 16.dp),
        ) {
            val classDef = ClassData.ALL_CLASSES.find {
                it.name == PreviewData.sampleCharacter.className
            }!!
            FeaturesSection(
                classDef = classDef,
                level = PreviewData.sampleCharacter.level,
                subclassName = PreviewData.sampleCharacter.subclass,
            )
        }
    }
}
